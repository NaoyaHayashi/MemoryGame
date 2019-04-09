/**
   Score class keeps track of a player's score for the memory game. <br>
   This class incorporates with the server. <br>
   @author Naoya Hayashi
   @date April 8th, 2013
   @version 1.0
*/
public class Score{
   private int obtainedNumOfPairs;
   private int turn; // how many turns the palyer has
   // true if the player has a winner card, false otherwise
   private boolean winnerCard;

   /**
         Default constructor. <br>
      */
   public Score(){
      obtainedNumOfPairs = 0;
      // keeps track of a player's elapsed turn
      // this variable was not used after all
      turn = 0;
      // winner card is the card in the 1st index
      winnerCard = false;
   }

   /**
         Increments a player's elapsed turn if he/she finished his/her turn.
      */
   public void turnIncrement(){
      turn++;
   }


   /**
         Sets a flag that a particular player got the winner card.
      */
   public void gotWinnerCard(){
      winnerCard = true;
   }


   /**
         Increments the number of pairs a player gets.
      */
   public void gotPair(){
      obtainedNumOfPairs++;
   }


   /**
         Returns the number of pairs obtained by a player. <br>

         @return number of pairs obtained by a specific player
      */
   public int getNumOfPairs(){
      return obtainedNumOfPairs;
   }


   /**
         Checks if the player has the winner card or not. <br>

         @return true if the player has the winner card; false otherwise
      */
   public boolean hasWinnerCard(){
      return winnerCard;
   }


   /**
         Sets the player's number of pairs got. <br>
         This method is used only when the user quits the game. <br>
         Then, the server assigns the player to 0 obtainedPairs.

         @param obtainedPairs number of obtained pairs which is likely 0
      */
   public void setNumOfPairs(int obtainedPairs){
      obtainedNumOfPairs = obtainedPairs;
   }

   public void dummyMethod(){
     System.out.println("This Method is dummy.");
   }
}
