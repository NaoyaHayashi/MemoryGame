import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.*;
import javax.swing.*;

/**
   Server class provides the instance for the players to play the memory game. <br>
   This class builds a text console to keep track of the log of the server. <br>
   @author Naoya Hayashi
   @date April 7th, 2013
   @version 1.0
*/
public class Server implements Constants{
   public static void main(String[] args) throws IOException{
      final int WIDTH = 600;
      final int HEIGHT = 700;
      JFrame frame = new JFrame();
      JTextArea text = new JTextArea();
      
      ServerSocket serverSocket = new ServerSocket(PORT);
      CardDeck deck = new CardDeck(NUM_OF_CARDS);
      //~ ArrayList<Socket> sockets = new ArrayList<Socket>();
      //~ ArrayList<RunnableGame> games = new ArrayList<RunnableGame>();
      Socket[] sockets = new Socket[NUM_OF_PLAYERS_TO_START];
      // may or may not be used
      ArrayList<Thread> threads = new ArrayList<Thread>();
      
      
      text.append("Waiting for clients to connect . . . \n");
      int numOfPlayer = 0;
      final String PLAYER = "Player";
      String playerName;
      int gameCounter = 1;
      
      frame.add(text);
      frame.setSize(WIDTH, HEIGHT);
      frame.setTitle("Server Log");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      
      while(true){
         Socket s = serverSocket.accept();
         sockets[numOfPlayer] = s;
         OutputStream os = sockets[numOfPlayer].getOutputStream();
         PrintWriter out = new PrintWriter(os);
         
         numOfPlayer++;
         playerName = PLAYER + numOfPlayer;
         text.append("Game #"+ gameCounter + ": " + playerName + " connected.\n");
         // assign player's name to a player
         out.println(playerName);
         out.flush();
         
         ///// possibly, might have a big problem OUT OF SCOPE ////////
         if(numOfPlayer >= NUM_OF_PLAYERS_TO_START){
            // reset numOfPlayer
            numOfPlayer = 0;
            text.append("Game #" + gameCounter + " started.\n");
            RunnableGame aGame = new RunnableGame(sockets, text, gameCounter);
            Thread t = new Thread(aGame);
            gameCounter++;
            t.start();
         }
      }
   }
}