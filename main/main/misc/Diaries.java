package main.misc;

import main.model.players.Client;

/**
 *
 * @author 6ix
 */
public class Diaries extends AchievementDiaries {
       public static void AchievementDiariesEasy(Client player) {
           
                 /**
                   * Logs cut
                   */
                 player.getPA().sendFrame126("        Completed Tasks "+player.taskcomplete+"/13",29295);
               if (player.logsCut == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.logsCut+"/100 logs cut</img>",29305);     
               } else if (player.logsCut <= 99){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.logsCut+"/100 logs cut</img>",29305);
               }  else if (player.logsCut == 100) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.logsCut+"/100 logs cut</img>",29305);  
               } 
                  /**
                   * lobsters fished
                   */
                if (player.lobstersFished == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.lobstersFished+"/100 Fish caught</img>",29306);     
               } else if (player.lobstersFished <= 99){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.lobstersFished+"/100 Fish caught</img>",29306);
               }  else if (player.lobstersFished == 100) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.lobstersFished+"/100 Fish caught</img>",29306);  
               } 
                /**
                 * rock crabs killed
                 */
                if (player.crabKills == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.crabKills+"/50 Rockcrabs Killed</img>",29307);     
               } else if (player.crabKills <= 49){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.crabKills+"/50 Rockcrabs Killed</img>",29307);
               }  else if (player.crabKills == 50) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.crabKills+"/50 Rockcrabs Killed</img>",29307);   
               } 
                
                /**
                * Bones on altar
                */
                if (player.bonesonalt == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.bonesonalt+"/100 Bones on altar</img>",29308);     
               } else if (player.bonesonalt <= 99){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.bonesonalt+"/100 Bones on altar</img>",29308);
               }  else if (player.bonesonalt == 100) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.bonesonalt+"/100 Bones on altar</img>",29308);  
               }
                /**
                * Slayer tasks
                */
                if (player.slayertasks == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.slayertasks+"/10 Slayer tasks</img>",29309);     
               } else if (player.slayertasks <= 9){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.slayertasks+"/10 Slayer tasks</img>",29309);
               }  else if (player.slayertasks == 10) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.slayertasks+"/10 Slayer tasks</img>",29309);   
               } 
               }
       
    
    public static void AchievementDiariesMedium(Client player) {
                         /**
                   * dragons
                   */
               if (player.dragKills == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.dragKills+"/100 Dragons killed</img>",29311);     
               } else if (player.dragKills <= 99){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.dragKills+"/100 Dragons killed</img>",29311);
               }  else if (player.dragKills == 100) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.dragKills+"/100 Dragons killed</img>",29311); 
               } 
                  /**
                   * pest games won
                   */
                if (player.gamesWon == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.gamesWon+"/10 PC games won</img>",29312);     
               } else if (player.gamesWon <= 8){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.gamesWon+"/10 PC games won</img>",29312);
               }  else if (player.gamesWon == 10) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.gamesWon+"/10 PC games won)</img>",29312); 
               }
               /**
                   * Slayer level
                   */
               int slayerLevel = 70;
               if (player.getLevelForXP(player.playerXP[18]) == 1) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.getLevelForXP(player.playerXP[18])+"/70 Slayer level</img>",29313);     
               } else if (player.getLevelForXP(player.playerXP[18]) <= 69){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.getLevelForXP(player.playerXP[18]) +"/70 Slayer level</img>",29313);
               }  else if (player.getLevelForXP(player.playerXP[18]) >= 70) {
                   player.getPA().sendFrame126("@gre@<img=6>"+slayerLevel +"/70 Slayer Level</img>",29313); 
               } 
    }
       public static void AchievementDiariesHard(Client player) {
                  /**
                   * Firecape
                   */
                if (player.firecapeObtained == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.firecapeObtained+"/1 Firecape</img>",29315);     
               }   else if (player.firecapeObtained == 1) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.firecapeObtained+"/1 Firecape</img>",29315);  
               } 
                /**
                 * Mage arena
                 */
                if (player.magearenaC == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.magearenaC+"/1 Mage arena</img>",29316);     
               }  else if (player.magearenaC == 1) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.magearenaC+"/1 Mage arena</img>",29316);    
               } 
                /**
                   * Abbyssal demons killed
                   */
               if (player.abbieskilled == 0) {
                   player.getPA().sendFrame126("@red@<img=6>"+player.abbieskilled+"/100 Abbyssal demons</img>",29317);     
               } else if (player.abbieskilled <= 99){
                   player.getPA().sendFrame126("@yel@<img=6>"+player.abbieskilled+"/100 Abbyssal demons</img>",29317);
               }  else if (player.logsCut == 100) {
                   player.getPA().sendFrame126("@gre@<img=6>"+player.abbieskilled+"/100 Abbyssal demons)</img>",29317); 
               } 
    }

    public static void AchievementDiariesElite(Client player) {
                         /**
                   * Firecape
                   */
               if (player.firecapeObtained == 0) {
                   player.getPA().sendFrame126("@red@<img=6> -",29319);     
               }  else if (player.firecapeObtained == 1) {
                   player.getPA().sendFrame126("@gre@<img=6> -",29319);  
               } 
                  /**
                   * has pet
                   */
                if (player.hasPet == 0) {
                   player.getPA().sendFrame126("@red@<img=6> -",29320);     
               }  else if (player.hasPet == 1) {
                   player.getPA().sendFrame126("@gre@<img=6> -",29320);     
               } 
                 /**
                   * suggestion
                   */
                if (player.hasPet == 0) {
                    player.getPA().sendFrame126("",29321);     
               }  else if (player.hasPet == 1) {
                   player.getPA().sendFrame126("",29321);              
               }
    }

    
}
