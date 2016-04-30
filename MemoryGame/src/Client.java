import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.io.InputStream;

/**
   Client provides main method to allow the implementation of GUI for a player. <br>
   The actual implementation codes are mostly in MemoryFrame class. <br>
   Precondition: The communication protocols sent from server must be accurate and correct. <br>
   
   @author Naoya Hayashi
   @date March 27th, 2013
   @version 1.1
*/
public class Client implements Constants{
   /**
         The main method to implement the program. <br>
   
         @param args the protocols sent from server which may contain several commands for the GUI.
      */
   public static void main(String[] args) throws IOException{
      do{
         final int WIDTH = 670;
         final int HEIGHT = 560;
         
         final String HOST = "-host";
         final String IMAGE = "-img";
         final String HELP = "-help";
         String host = "";
         String imagePath = "";
         boolean isWrongInput = false;
         // check command-line arguments and assign variables according to the input
         for(int i=0; i<args.length; i++){
            try{
               if(HOST.equals(args[i])){
                  i++;
                  try{
                     host = args[i];
                  }
                  catch(IndexOutOfBoundsException exception){
                     
                  }
               }
               else if(IMAGE.equals(args[i])){
                  i++;
                  try{
                     imagePath = args[i];
                  }
                  catch(IndexOutOfBoundsException exception){
                     
                  }
               }
               else if(HELP.equals(args[i])){
                  // show the list of possible command-line arguments & what they do
                  // probably joption pane
               }
            }
            catch(IndexOutOfBoundsException exception){
               isWrongInput = true;
            }
         }
         
         // If command-line arguments are not appropriate, the program terminates
         if(isWrongInput){
            System.out.println("Command-line Error: " 
               + "The host name or image path is not assigned.");
            System.exit(0);
         }
         
         Socket s = new Socket(host, PORT);
         InputStream instream = s.getInputStream();
         Scanner in = new Scanner(instream);
         // server assigns the player's name when a player connects to the server
         String playerName = in.nextLine();
         // arbitrary choice of the player's name
         // In my plan, the player's name must be assigned from the server.
         
         ClientData data = new ClientData(playerName);
         MemoryGUI frame = new MemoryGUI(s, data);
         
         frame.setSize(WIDTH, HEIGHT);
         frame.setLocationRelativeTo(null);
         frame.setTitle("Memory Game: " + playerName);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
         
         // game continues until a winner is determined
         while(true){
            if(in.hasNextLine()){
               String response = in.nextLine();
               data.setCommand(response);
               frame.implementCommand();
               // comparing first 5 characters fo response to "WINNER"
               // if it is true, it means the winner is determined, so it also means the end of the game.
               if("WINNER".equalsIgnoreCase(response.substring(0, 6))){
                  break;
               }
            }
         }
         // when one of the players click restart button, the game will start again
         while(true){
            if(in.hasNext()){
               String response = in.nextLine();
               if("RESTART".equalsIgnoreCase(response)){
                  break;
               }
            }
         }
         s.close();
      }while(true);
   }   
}
