import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;

/**
   MemoryGUI class gives GUI for each player to play memory game. <br>
   This class inherits JFrame class. <br>
   This class also implements ActionListener interface, so it implements the methods ActionPerformed(). <br>
   Precondition: Since I assume that all protocols sent from the server are correct, the client (GUI) doesn't
                      handle the erroneous inputs for protocols. This should be handled in the server before sending protocols.
   
   @author Naoya Hayashi
   @date March 27th, 2013
   @version 1.1
*/
public class MemoryGUI extends JFrame implements ActionListener{
   private static final long serialVersionUID = 1L;
   private final int NUM_OF_BUTTONS = 12;
   // These Strings refer to the name of image files used in this program
   private final String[] ICONS = {"src/img/back.jpg", "src/img/1.jpg", "src/img/2.jpg"
      , "src/img/3.jpg", "src/img/4.jpg", "src/img/5.jpg", "src/img/6.jpg"};
   
   // instance variables for GUI
   private JButton[] buttons;
   private JButton startButton;
   private JButton quitButton;
   private JTextArea text;
   
   // index of the card, so possible values are 0-11
   // its default value is -1, if the value is not -1, it means one card is already open
   private int openedCard; 
   private ClientData data;
   private Socket s;
   private Scanner in;
   private PrintWriter out;
   
      
   /**
         Constructor with one parameter, ClientData. <br>
         This constructor essentially builds the GUI. <br>
      
         @param otherData ClientData object to store some data
      */
   public MemoryGUI(Socket socket, ClientData clientData){
      super();
      openedCard = -1;
      s = socket;
      data = clientData;
      
      //buildGUI();
      setLayout(new BorderLayout());
      JPanel panel1 = new JPanel();
      panel1.setLayout(new GridLayout(3, 4, 10, 10));
      
      buttons = new JButton[NUM_OF_BUTTONS];
      
      // initialize buttons
      for(int i=0; i<buttons.length; i++){
         buttons[i] = new JButton(new ImageIcon(ICONS[0]));
      }
      
      // add buttons to the panel
      for(int i=0; i<buttons.length; i++){
         panel1.add(buttons[i]);
      }
      
      add(panel1, BorderLayout.CENTER);
      
      JPanel panel2 = new JPanel();
      panel2.setLayout(new GridLayout(1, 3));
      startButton = new JButton("Start the Game");
      //startButton.setContentAreaFilled(false);//---------
      //startButton.setOpaque(true);//---------
      startButton.setBackground(Color.blue);
      quitButton = new JButton("Quit the Game");
      //quitButton.setContentAreaFilled(false);//---------
      //quitButton.setOpaque(true);//---------
      quitButton.setBackground(Color.red);
      text = new JTextArea(1, 1);
      text.setBackground(Color.gray);
      
      //System.out.println(startButton.isOpaque());//---------
      //System.out.println(quitButton.isOpaque());//---------
      //System.out.println(text.isOpaque());//---------
      
      panel2.add(startButton);
      panel2.add(text);
      panel2.add(quitButton);
      
      add(panel2, BorderLayout.SOUTH);
      
      // register buttons to listener
      for(int i=0; i<buttons.length; i++){
         buttons[i].addActionListener(this);
      }
      // register start and quit button to listener
      startButton.addActionListener(this);
      quitButton.addActionListener(this);
   }
   
   /**
         Executes events registered on listener object. <br>
         Clicking a button sends a request to server depending on the kind of button. <br>
         Since the server is not yet created, this method essentially does nothing. <br>
         @param e an event triggered by a button
      */
   public void actionPerformed(ActionEvent e){
      try{
         in = new Scanner(s.getInputStream());
         out = new PrintWriter(s.getOutputStream());
      }
      catch(IOException exception){
         text.setText("Connection Error!!");
         return;
      }
      
      if(data.getPlayerTurn()){
         if(e.getSource() == quitButton){
            // send socket to the server to tell the user wants to quit
            // System.out.println("Quit button was hit");
            out.println("QUIT");
            out.flush();
         }
         else if(e.getSource() == startButton){
            // send socket to the server to tell the user requested to play the game //
            // System.out.println("Start button was hit");
            out.println("START");
            out.flush();
         }
         else if(e.getSource() == buttons[0]){
            //System.out.println("button 0 was hit.");
            // send a request to the server to open the card
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 0){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 0");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 0");
               out.flush();
               openedCard = 0;
            }
         }
         else if(e.getSource() == buttons[1]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 1){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 1");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 1");
               out.flush();
               openedCard = 1;
            }
         }
         else if(e.getSource() == buttons[2]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 2){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 2");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 2");
               out.flush();
               openedCard = 2;
            }
         }
         else if(e.getSource() == buttons[3]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 3){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 3");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 3");
               out.flush();
               openedCard = 3;
            }
         }
         else if(e.getSource() == buttons[4]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 4){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 4");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 4");
               out.flush();
               openedCard = 4;
            }
         }
         else if(e.getSource() == buttons[5]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 5){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 5");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 5");
               out.flush();
               openedCard = 5;
            }
         }
         else if(e.getSource() == buttons[6]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 6){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 6");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 6");
               out.flush();
               openedCard = 6;
            }
         }
         else if(e.getSource() == buttons[7]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 7){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 7");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 7");
               out.flush();
               openedCard = 7;
            }
         }
         else if(e.getSource() == buttons[8]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 8){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 8");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 8");
               out.flush();
               openedCard = 8;
            }
         }
         else if(e.getSource() == buttons[9]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 9){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 9");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 9");
               out.flush();
               openedCard = 9;
            }
         }
         else if(e.getSource() == buttons[10]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 10){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 10");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 10");
               out.flush();
               openedCard = 10;
            }
         }
         else if(e.getSource() == buttons[11]){
            // case: the player opens the second card
            if(openedCard >=0 && openedCard <=11){
               if(openedCard == 11){
                  text.setText("Please do not click the opened card!!");
               }
               else{
                  out.println("OPEN 11");
                  out.flush();
                  openedCard = -1; // the second card is open so reset the variable
                  data.setPlayerTurn(false); // the player open two cards, so her turn is over
                  text.setText("-- Another Player's Turn --");
               }
            }
            // case: the player opens the first card
            else{
               out.println("OPEN 11");
               out.flush();
               openedCard = 11;
            }
         }
         else{
            // empty
         }
      }
      else{ // case: the current turn is NOT this client's turn
         // clicking buttons does nothing if the current turn is not this client's turn
      }
   }
   
   //-------------------------------------------------------------------------------------------------------------------------------------------
   // Implements the requests given from the server via communication protocol. 
   // Please refer to My Game Protocol.docx to see the possible protocols used in this program.
   //-------------------------------------------------------------------------------------------------------------------------------------------
   public void implementCommand(){
      // if there is no command protocol, do nothing
      // this will not happen if server always sends a correct messange
      if(data.getCommandLength() == 0){
         System.out.println("Error: the server doesn't send data correctly.");
         return;
      }
      
      int i = 0;
      // executes the request of commands sent from server until completes all requests
      while(i < data.getCommandLength()){
         // Assigns player's turn to a specific player.
         // If it's not the player's turn, the player's button-clicking doesn't send any message to the server
         if(data.getInput(i).equalsIgnoreCase("PLAYER")){
            i++;
            if(data.getInput(i).equalsIgnoreCase(data.getPlayerName())){
               data.setPlayerTurn(true);
               text.setText("-- Your Turn --");
            }
            else{
               data.setPlayerTurn(false);
               text.setText("-- Another Player's Turn --");
            }
            i++;
         }
         else if(data.getInput(i).equalsIgnoreCase("OPEN")){
            i++;
            int nthCard = Integer.parseInt(data.getInput(i));
            i++;
            int value = Integer.parseInt(data.getInput(i));
            i++;
            buttons[nthCard].setIcon(new ImageIcon(ICONS[value]));
            
            // after opening two cards, //
            // if two cards match, it leaves the cards open //
            // if two cards do not match, it closes the cards //
            // server determines if the selected pair of cards matches or not //
         }
         else if(data.getInput(i).equalsIgnoreCase("CLOSE")){
            try{
               Thread.sleep(5000);
            }
            catch(InterruptedException exception){
               // empty
            }
            
            i++;
            int nthCard = Integer.parseInt(data.getInput(i));
            i++;
            int mthCard = Integer.parseInt(data.getInput(i));
            i++;
            // ICONS[0] refers to "back.png", which is langara's image
            buttons[nthCard].setIcon(new ImageIcon(ICONS[0]));
            buttons[mthCard].setIcon(new ImageIcon(ICONS[0]));
         }
         else if(data.getInput(i).equalsIgnoreCase("LEAVEOPEN")){
            try{
               Thread.sleep(5000);
            }
            catch(InterruptedException exception){
               // empty
            }
            
            i++;
            int nthCard = Integer.parseInt(data.getInput(i));
            i++;
            int value1 = Integer.parseInt(data.getInput(i));
            i++;
            int mthCard = Integer.parseInt(data.getInput(i));
            i++;
            int value2 = Integer.parseInt(data.getInput(i));
            i++;
            // open the pair of cards
            // Since it's a pair value1 and value 2 should be the same value
            buttons[nthCard].setIcon(new ImageIcon(ICONS[value1]));
            buttons[mthCard].setIcon(new ImageIcon(ICONS[value2]));
            
            // these codes should be done after a few seconds
            // the time management (control) should be done by Thread            
            buttons[nthCard].setEnabled(false);
            buttons[mthCard].setEnabled(false);
         }
         else if(data.getInput(i).equalsIgnoreCase("WINNER")){
            i++;
            // announce who won the game
            if(data.getInput(i).equalsIgnoreCase(data.getPlayerName())){
               text.setText("Congratulation!! You Win!!");
               i++;
               text.append("\n" + "The winner obtained " + data.getInput(i) + " pairs.");
            }
            else{
               text.setText("Result: Winner is " + data.getInput(i) + "!!");
               i++;
               text.append("\n" + "The winner obtained " + data.getInput(i) + " pairs.");
            }
            i++;
         }
         // may not be used //
         else if(data.getInput(i).equalsIgnoreCase("WAIT")){
            i++;
            // empty since this command is not required for this assignment
            System.out.println("Sleep this client");
            i++;
         }
         // If the command doesn't match any of those above, it ignores the String.
         // But, as long as the server's meesage is correct, programming flow never gets inside this else.
         else{
            i++;
         }
      }
   }
   
}