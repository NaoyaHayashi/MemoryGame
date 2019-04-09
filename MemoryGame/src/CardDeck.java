/**
   CardDeck class has numbers of Cards. <br>
   Hence, it ends up with holding data for all the cards used in this game. <br>
   This class also incorporates with Server but not Client. <br>
   @author Naoya Hayashi
   @date March 27th, 2013
   @version 1.0
*/
public class CardDeck implements Constants{
   private Card[] cards;
   
   /**
         Default Constructor without parameters.
      */
   public CardDeck(){
      int i = 0;
      cards = new Card[NUM_OF_CARDS];
      
      // assign card values to the first 6 cards
      for(; i<(cards.length / 2); i++){
         cards[i] = new Card(i);
      }
      // asign card values to the last 6 cards
      // so that there exist 6 pair of cards
      for(; i<cards.length; i++){
         cards[i] = new Card(i-(cards.length / 2));
      }
      shuffle();
   }
   
   /**
         Constructor with one parameter. <br>
   
         @param n number of cards used in the game.
      */
   public CardDeck(int n){
      // if n is odd, make it even by reducing its value by 1
      if((n % 2) == 1){
         n = n - 1;
      }
      
      // if n is lower than 2, make it default value
      if(n < 2){
         n = NUM_OF_CARDS;
      }
      
      cards = new Card[NUM_OF_CARDS];
      int i =0;
      
      for(; i<(cards.length / 2); i++){
         cards[i] = new Card(i);
      }
      for(; i<cards.length; i++){
         cards[i] = new Card(i-(cards.length / 2));
      }
      
      shuffle();
   }
   
   //-----------------------------------------------------------------------------------------------------------------
   // Shuffles the card deck.
   // The shuffling algorithm is to swap two randomly choosen cards 1000 times.
   //-----------------------------------------------------------------------------------------------------------------
   private void shuffle(){
      // 1000 is my arbitrary choice to shuffle the card deck
      final int NUM_OF_SWAP = 1000;
      
      for(int i=0; i<NUM_OF_SWAP; i++){
         int randomInt1 = (int)(Math.random() * 10000);
         int randomIndex1 = randomInt1 % (cards.length);
         int randomInt2 = (int)(Math.random() * 10000);
         int randomIndex2 = randomInt2 % (cards.length);
         
         // swap the ramdomly choosen 2 indices
         Card tmp = cards[randomIndex1];
         cards[randomIndex1] = cards[randomIndex2];
         cards[randomIndex2] = tmp;
      }
   }
   
   
   /**
         Returns a card value. <br>
         Returns -1 if the parameter index causes OutOfBounds exception.
   
         @param index index of a card specified by this index
         @return returns -1 if the parameter is erroneous; otherwise, returns 
         the card value specified by the parameter
      */
   public int getCardValue(int index){ //throws IndexOutOfBoundsException{
      if(index >= cards.length){
         return -1;
         //throw new IndexOutOfBoundsException("Error: Incorrect Client Message (Illegal Index)!!");
      }
      return cards[index].getValue();
   }
   
   
   /**
         Checks whether or not two specified cards match. <br>
         The card value is the criteria to determine the match of a pair. <br>
         
         @param index1 the index of the 1st opened card
         @param index2 the index of the 2nd opened card
         @return true if two cards match; false otherwise
      */
   public boolean match(int index1, int index2){ //throws IndexOutOfBoundsException{
      if(index1 >= cards.length || index2 >= cards.length){
         return false;
         //throw new IndexOutOfBoundsException("Error: Incorrect Client Message (Illegal Index)!!");
      }
      //~ catch(IndexOutOfBoundsException exception){
         //~ return false;
      //~ }
      
      if(getCardValue(index1) == getCardValue(index2)){
         return true;
      }
      else{
         return false;
      }
   }
}


