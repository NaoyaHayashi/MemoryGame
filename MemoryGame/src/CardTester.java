
public class CardTester implements Constants{
   public static void main(String[] args){
      CardDeck deck = new CardDeck(NUM_OF_CARDS);
      
      for(int i=0; i<NUM_OF_CARDS; i++){
         System.out.print(deck.getCard(i).getValue());
         System.out.print(" ");
      }
      System.out.println("");
   }
}
