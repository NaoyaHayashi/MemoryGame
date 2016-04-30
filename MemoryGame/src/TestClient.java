import java.net.*;
import java.io.*;
import java.util.*;

// This class tests the OPEN, LEAVEOPEN and CLOSE commands and checks such messages are correclty sent to each other
public class TestClient implements Constants{
   public static void main(String[] args) throws IOException{
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.out.println("Waiting for clients to connect...");
      
      while(true){
         int numOfCardOpened = 0;
         // keep track of which card the player opened
         // needed to close or leaveopen after two cards are revealed
         int index1 = -1;
         int index2 = -1;
         boolean match = true;
         Socket s = serverSocket.accept();
         System.out.println("Client connected.");
         InputStream is = s.getInputStream();
         OutputStream os = s.getOutputStream();
         Scanner in = new Scanner(is);
         PrintWriter out = new PrintWriter(os);
         out.println("Player1");
         out.flush();
         
         while(true){
            ///////// EXPECTING THE CORRECT MESSAGE IS SENT////////////////
            // Format: OPEN 1 4
            while(in.hasNextLine()){
               System.out.println("Message received from a client: OPEN Command");
               final int CARD_VAL = 3; // arbitrary value (this value has to be got from CardDeck class)
               String str = in.nextLine();
               String[] tokens = str.split(" ", 0);
               String command = tokens[0];
               int index = Integer.parseInt(tokens[1]);
               if(numOfCardOpened == 0){
                  index1 = index;
               }
               else{ // else means numOfCardOpened == 1
                  index2 = index;
               }
               
               if(command.equalsIgnoreCase("OPEN")){
                  numOfCardOpened++;
                  String msg = command + " " + index + " " + CARD_VAL;
                  out.println(msg);
                  out.flush();
                  
                  if(numOfCardOpened == 2){
                     numOfCardOpened = 0;
                     if(!match){
                        System.out.println("Message received from a client: LEAVEOPEN Command");
                        String anotherMsg = "LEAVEOPEN" + " " + index1 + " " + CARD_VAL + " " + index2 + " " + CARD_VAL;
                        out.println(anotherMsg);
                        out.flush();
                     }
                     else{
                        System.out.println("Message received from a client: CLOSE Command");
                        String anotherMsg = "CLOSE" + " " + index1 + " " + index2;
                        out.println(anotherMsg);
                        out.flush();
                     }
                  }
               }
            }
         }
      }
   }
}