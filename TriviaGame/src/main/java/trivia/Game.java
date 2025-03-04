package trivia;

import java.util.ArrayList;

// REFACTOR ME
public class Game implements IGame {
   ArrayList players = new ArrayList();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   Pop popQuestions = new Pop();
   Sciences scienceQuestions = new Sciences();
   Sport sportsQuestions = new Sport();
   Rock rockQuestions = new Rock();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addQuestion(i);
         scienceQuestions.addQuestion(i);
         sportsQuestions.addQuestion(i);
         rockQuestions.addQuestion(i);
      }
   }

   // A utiliser
   public boolean isPlayable() {
      return (getNbPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[getNbPlayers()] = 1;
      purses[getNbPlayers()] = 0;
      inPenaltyBox[getNbPlayers()] = false; //nom bizarre
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   //changer nom pour avec un getter
   public int getNbPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   // dans la classe abstraite
   private void askQuestion() {
      if (currentCategory() == "Pop")
         System.out.println(popQuestions.removeFirstQuestion());
      if (currentCategory() == "Science")
         System.out.println(scienceQuestions.removeFirstQuestion());
      if (currentCategory() == "Sports")
         System.out.println(sportsQuestions.removeFirstQuestion());
      if (currentCategory() == "Rock")
         System.out.println(rockQuestions.removeFirstQuestion());
   }


   //a changer
   private String currentCategory() {
      switch (places[currentPlayer] - 1) {
         case 0:
         case 4:
         case 8:
            return popQuestions.getName();
         case 1:
         case 5:
         case 9:
            return scienceQuestions.getName();
         case 2:
         case 6:
         case 10:
            return sportsQuestions.getName();
         default:
            return rockQuestions.getName();
      }
   }

   //a changer
   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
         {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }
      } else {

         System.out.println("Answer was correct!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
