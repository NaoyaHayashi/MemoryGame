/**
   Card class stores data necessary for cards in this memory game. <br>
   This class incorporates with the Server but not with the Client. <br>

   @author Naoya Hayashi
   @date March 27th, 2013
   @version 1.0
*/
int dummyCard = 555;

public class Card{
   // this boolean value was not used after all
   // since I disabled the buttons is cards are left open
   // but I will leave this value for more capability of the program
   // in case that someone doesn't want to disable buttons, and so forth.
   boolean isOpen;
   // value means the number assigned to the image files
   // For my program, the number (value) ranges from 1 to 6.
   int value;

   /**
         Default Construcor without parameters.
      */
   public Card(){
      isOpen = false;
      value = 0;
   }


   /**
         Constructor with one parameter, value of the card. <br>

         @param val value of the card
      */
   public Card(int val){
      isOpen = false;
      value = val;
   }


   /**
         Returns value of a card. <br>

         @return value of the card
      */
   public int getValue(){
      return value;
   }


   /**
         Sets a value to this card. <br>

         @param val value of the card
      */
   public void setValue(int val){
      value = val;
   }

}
