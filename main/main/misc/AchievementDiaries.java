package main.misc;

import main.model.players.Client;
import main.util.Misc;


/**
 *
 * @author 6ix
 */
  
public class AchievementDiaries {

   public static int rewardId;
   public static int rewardAmount;
   private static final boolean complete = true;
   private static String completeMessage = "<img=6>Congratualtions, you've completed all your tasks!</img>";

    public static void run(Client player) {
    Diaries.AchievementDiariesEasy(player);
    Diaries.AchievementDiariesMedium(player);
    Diaries.AchievementDiariesHard(player);
    Diaries.AchievementDiariesElite(player);
}
}
