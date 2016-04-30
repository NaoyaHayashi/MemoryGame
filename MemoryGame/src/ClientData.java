import java.util.*;

/**
   ClientData class is used to store some useful data for clients (GUIs). <br>
   It stores 3 kinds of different data: protocols from server, a player's name assigned from server,
   and  list of index which refers to the location of pairs revealed by the players.  <br>
   The list of index may be unnecessary data but the first two data are essential. <br>
   Precondition: The protocols sent from the server are assumed to be correct and accurate since 
      the server is in charge of checking the accuracy of protocols before sending via socket. <br>
      
   @author Naoya Hayashi
   @date March 27th, 2013
   @version 1.1
*/
public class ClientData{
   // instance variables 
   private String[] command;
   private String playerName;
   private boolean isYourTurn;
   // with this array list, it's possible to keep track of pairs revealed by players,
   // but it might not be used. 
   // The integer indicates the index of pairs found, so it can locate the found pairs of cards.
   private ArrayList <Integer> openCards;
   
   /**
         Constructor with two parameters. <br>
   
         @param agrs protocols sent from server
         @param pName a player's name 
      */
   public ClientData(String pName){
      command = null;
      isYourTurn = false;
      playerName = pName;
      openCards = new ArrayList<Integer>();
      if(playerName.equalsIgnoreCase("Player1")){
         isYourTurn = true;
      }
   }
   
   /**
         Returns a player's name.
         
         @return a player's name
      */
   public String getPlayerName(){
      return playerName;
   }
   
   /**
         Returns a specified input from the server. <br>
         
         @param index index to specify a particular String in a protocol   
         @return a particular String which reprents either command or integer to access a specific data
      */
   public String getInput(int index){
      return command[index];
   }
   
   /**
         Returns the card value of leave-opened cards. <br>
         This may be useful when decideing a winner 
         when two players get same number of pairs at the end of the game. <br>
   
         @param index index to locate a particular card for the value to return
         @return the value of a card left open
      */
   public int getOpenCardVal(int index){
      return openCards.get(index);
   }
   
   /**
         Returns the length of the protocol. <br>
         In other words, it returns the number of String sent from server. <br>
         
         @return the length of protocol
      */
   public int getCommandLength(){
      return command.length;
   }
   
   /**
         Returns true if the current turn is this player's turn; returns false otherwise. <br>
   
         @return true if the current turn is this player's turn; returns false otherwise
      */
   public boolean getPlayerTurn(){
      return isYourTurn;
   }
   
   
   /**
         Sets the player's turn. <br>
         
         @param turn true if it's the player's turn; otherwise false
      */
   public void setPlayerTurn(boolean turn){
      isYourTurn = turn;
   }
   
   
   /**
         Sets the command for this player. <br>
         
         @return String object sent from the server as a command
      */
   public void setCommand(String cmd){
      command = cmd.split(" ", 0);
   }
}