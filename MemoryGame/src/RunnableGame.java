import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
   RunnableGame class runs a game. <br>
   @author Naoya Hayashi
   @date April 7th, 2013
   @version 1.0
*/
public class RunnableGame implements Constants, Runnable{
   private Socket[] sockets;
   private Scanner[] in;
   private PrintWriter[] out;
   private CardDeck deck;
   private Score[] scores;
   private JTextArea text;
   private int gameNumber;
   
   /**
         Constructor with 3 parameters. <br>
         
         @param aSockets the arra of the sockets for each player
         @param textArea text field to keep the server log
         @param gameNum game # to identify the game
      */
   public RunnableGame(Socket[] aSockets, JTextArea textArea, int gameNum){
      sockets = aSockets;
      text = textArea;
      gameNumber = gameNum;
      deck = new CardDeck();
      in = new Scanner[NUM_OF_PLAYERS_TO_START];
      out = new PrintWriter[NUM_OF_PLAYERS_TO_START];
      scores = new Score[NUM_OF_PLAYERS_TO_START];
      for(int i=0; i<NUM_OF_PLAYERS_TO_START; i++){
         scores[i] = new Score();
      }
   }
   
   /**
         Runs the game. <br>
         Since RunnableGame class implements Runnable interface, this method is necessary.
      */
   public void run(){
      try{
         try{
            connect();
            doService();
         }
         finally{
            closeAllSockets();
            for(int i=0; i<sockets.length; i++){
               int playerNumber = i + 1;
               text.append("Player" + playerNumber + " disconncted.\n");
            }
            text.append("Game #" + gameNumber + "Ended.\n");
         }
      }
      catch(IOException exception){
         // empty
      }
   }
   
   /**
         Excecutes the actual implementations to run the game. <br>
         Communicates with each player and  does certain commands defined in the protocol.
      */
   public void doService() throws IOException{
      int numOfCardOpen = 0;
      // index1 means 1st opened card of index
      int index1 = -1;
      // index2 means 2nd opened card of index
      int index2 = -1;
      boolean turnEnded = false;
      boolean gameEnded = false;
      
      // send a message to all clients that the Player1's turn starts
      int playerTurn = 0;
      assignTurn(playerTurn);
      
      while(true){
         for(int i=0; true; i++){
            turnEnded = false;
            while((!turnEnded) && (in[i].hasNextLine())){
               String message = in[i].nextLine();
               if("QUIT".equals(message)){
                  // sets 0 to the player's number of pairs obtained
                  // so he/she should lose the game
                  scores[i].setNumOfPairs(0);
                  sendWINNERCommandToALL();
                  return;
               }
               else if("START".equals(message)){
                  
               }
               else{ // else means "OPEN i"
                  numOfCardOpen++;
                  String tokens[] = message.split(" ", 0);
                  
                  if(numOfCardOpen == 2){
                     turnEnded = true;
                     numOfCardOpen = 0; // reset the variable for the next player
                     index2 = Integer.parseInt(tokens[1]);
                     sendOPENCommandToAll(index2);
                     
                     if(deck.match(index1, index2)){
                        scores[i].gotPair();
                        sendLEAVEOPENCommandToALL(index1, index2);
                        
                        //if the player got the winner card, make the variable true for the player
                        if((deck.getCardValue(index1) == 0) 
                           && (deck.getCardValue(index2) == 0)){
                           scores[i].gotWinnerCard();
                        }
                        
                        // check if all cards are opened or not, 
                        // if so, Winner Announce & gameEnded = true
                        if(isGameOver()){
                           sendWINNERCommandToALL();
                           return;
                        }
                     }
                     else{ // 2 opened cards did not match
                        sendCLOSECommandToALL(index1, index2);
                     }
                     scores[i].turnIncrement();
                     playerTurn++;
                     if(playerTurn == NUM_OF_PLAYERS_TO_START){
                        playerTurn = 0;
                     }
                     assignTurn(playerTurn);
                     
                  }
                  else{ // else means numOfCardOpen == 1
                     index1 = Integer.parseInt(tokens[1]);
                     sendOPENCommandToAll(index1);
                  }
               }
            }
            if(i >= (sockets.length - 1)){ // reset i
               i = -1;
            }
         }
      }
      
   }
   
   /**
         Closes all the sockets. <br>
      */
   private void closeAllSockets() throws IOException{
      for(int i=0; i<sockets.length; i++){
         sockets[i].close();
      }
   }
   
   /**
         Sets up all the sockets to send and receive messages. <br>
      */
   private void connect() throws IOException{
      for(int i=0; i<sockets.length; i++){
         in[i] = new Scanner(sockets[i].getInputStream());
         out[i] = new PrintWriter(sockets[i].getOutputStream());
      }
   }
   
   
   /**
         Assigns a turn to a specific player. <br>
         The command message is send to all players.
         
         @param turn the index of a specific player
      */
   private void assignTurn(int turn){
      turn++;
      String str = "PLAYER Player" + turn;
      for(int i=0; i<sockets.length; i++){
         out[i].println(str);
         out[i].flush();
      }
   }
   
   
   /**
         Sends OPEN command to all the players. <br>
         
         @param index index of the card to open
      */
   private void sendOPENCommandToAll(int index){
      int iconValue = deck.getCardValue(index) + 1;
      String str = "OPEN " + index + " " + iconValue;
      for(int i=0; i<sockets.length; i++){
         out[i].println(str);
         out[i].flush();
      }
   }
   
   /**
         Sends WINNER command to all the players. <br>
         When this command is used, it indicates the end of the game.
      */
   private void sendWINNERCommandToALL(){
      // winnerIndex ranges from 0 to (maxNumOfPlayers-1)
      int winnerIndex = findWinner();
      // player number is bigger than index number by 1, so need this increment
      int playerNumber = winnerIndex + 1;
      String str = "WINNER Player" + playerNumber + " " 
         + scores[winnerIndex].getNumOfPairs();
      for(int i=0; i<sockets.length; i++){
         out[i].println(str);
         out[i].flush();
      }
   }
   
   
   /**
         Sends Start command to all the players. <br>
         However, I couldn't implement this command, so it does nothing, and not used for the program.
      */
   private void sendSTARTCommandToALL(){
      
      //~ for(int i=0; i<sockets.length; i++){
         //~ out[i].println(str);
         //~ out[i].flush();
      //~ }
   }
   
   
   /**
         Sends LEAVEOPEN command to all the players.
         
         @param index1 the index of the 1st card to leave open
         @param index2 the index of the 2nd card to leave open
      */
   private void sendLEAVEOPENCommandToALL(int index1, int index2){
      int iconValue1 = deck.getCardValue(index1) + 1;
      int iconValue2 = deck.getCardValue(index2) + 1;
      String str = "LEAVEOPEN " + index1 + " " + iconValue1 
         + " " + index2 + " " + iconValue2;
      for(int i=0; i<sockets.length; i++){
         out[i].println(str);
         out[i].flush();
      }
   }
   
   
   /**
         Sends CLOSE command to all the players. <br>
         
         @param index1 the index of the 1st card to close
         @param index2 the index of the 2nd card to close
      */
   private void sendCLOSECommandToALL(int index1, int index2){
      String str = "CLOSE " + index1 + " " + index2;
      for(int i=0; i<sockets.length; i++){
         out[i].println(str);
         out[i].flush();
      }
   }
   
   
   /**
         Determines who won the game at the end of the game. <br>
         The winner is determined by the following standard. <br>
         1: A player with most number of pairs wins the game. <br>
         2: If two players have the same number of pairs, the one having
            winner card will win.
      */
   private int findWinner(){
      int topPlayer = 0;
      int playerWithWinnerCard = -1;
      
      // find the winner card holder's index
      // if it's not found, playerWithWinnerCard remains to be -1
      for(int i=0; i<sockets.length; i++){
         if(scores[i].hasWinnerCard()){
            playerWithWinnerCard = i;
         }
      }
      
      for(int j=1; j<sockets.length; j++){
         if(scores[topPlayer].getNumOfPairs() <= scores
            [j].getNumOfPairs()){
            topPlayer = j;
         }
      }
      
      if(scores[topPlayer].getNumOfPairs() == scores
         [playerWithWinnerCard].getNumOfPairs()){
         topPlayer = playerWithWinnerCard;
      }
      return topPlayer;
   }
   
   
   /**
          Checks if the game is over or not. <br>
         The decision is made based on the sum of number of pairs found by 
         all the players. <br>
         If the sum is equal to the half of all cards (which is 6 in this program),
         it indicates that all pairs are found so that the game is over. <br>
         
         @return true if the game ends; otherwise false   
      */
   private boolean isGameOver(){
      int numOfPairsFound = 0;
      // count the number of pairs found by ALL players
      for(int i=0; i<sockets.length; i++){
         numOfPairsFound = numOfPairsFound + scores[i].getNumOfPairs();
      }
      // if the number of pairs found is equal to (NUM_OF_CARDS / 2), it means game over
      if(numOfPairsFound == (NUM_OF_CARDS / 2)){
         return true;
      }
      return false;
   }
}