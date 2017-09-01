package model.players.highscores;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.players.Client;
import main.util.DatabaseConnection;

public class Highscores {

    public static void saveData(Client c) {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("DELETE FROM `skills` WHERE playerName = '" + c.playerName + "';");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.print("ERROR" + e);
        }
        try {
            ps = con.prepareStatement("DELETE FROM `skillsoverall` WHERE playerName = '" + c.playerName + "';");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps = con.prepareStatement("INSERT INTO `skills` (`playerName`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangexp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`) "
                    + "VALUES ('" + c.playerName + "'," + c.playerLevel[0] + "," + c.playerXP[0] + "," + c.playerLevel[1] + "," + c.playerXP[1] + "," + c.playerLevel[2] + "," + c.playerXP[2] + "," + c.playerLevel[3] + "," + c.playerXP[3] + "," + c.playerLevel[4] + "," + c.playerXP[4] + "," + c.playerLevel[5] + "," + c.playerXP[5] + "," + c.playerLevel[6] + "," + c.playerXP[6] + "," + c.playerLevel[7] + "," + c.playerXP[7] + "," + c.playerLevel[8] + "," + c.playerXP[8] + "," + c.playerLevel[9] + "," + c.playerXP[9] + "," + c.playerLevel[10] + "," + c.playerXP[10] + "," + c.playerLevel[11] + "," + c.playerXP[11] + "," + c.playerLevel[12] + "," + c.playerXP[12] + "," + c.playerLevel[13] + "," + c.playerXP[13] + "," + c.playerLevel[14] + "," + c.playerXP[14] + "," + c.playerLevel[15] + "," + c.playerXP[15] + "," + c.playerLevel[16] + "," + c.playerXP[16] + "," + c.playerLevel[17] + "," + c.playerXP[17] + "," + c.playerLevel[18] + "," + c.playerXP[18] + "," + c.playerLevel[19] + "," + c.playerXP[19] + "," + c.playerLevel[20] + "," + c.playerXP[20] + ");");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps = con.prepareStatement("INSERT INTO `skillsoverall` (`playerName`,`lvl`,`xp`) "
                    + "VALUES ('" + c.playerName + "'," + (c.getLevelForXP(c.playerXP[0]) + c.getLevelForXP(c.playerXP[1]) + c.getLevelForXP(c.playerXP[2]) + c.getLevelForXP(c.playerXP[3]) + c.getLevelForXP(c.playerXP[4]) + c.getLevelForXP(c.playerXP[5]) + c.getLevelForXP(c.playerXP[6]) + c.getLevelForXP(c.playerXP[7]) + c.getLevelForXP(c.playerXP[8]) + c.getLevelForXP(c.playerXP[9]) + c.getLevelForXP(c.playerXP[10]) + c.getLevelForXP(c.playerXP[11]) + c.getLevelForXP(c.playerXP[12]) + c.getLevelForXP(c.playerXP[13]) + c.getLevelForXP(c.playerXP[14]) + c.getLevelForXP(c.playerXP[15]) + c.getLevelForXP(c.playerXP[16]) + c.getLevelForXP(c.playerXP[17]) + c.getLevelForXP(c.playerXP[18]) + c.getLevelForXP(c.playerXP[19]) + c.getLevelForXP(c.playerXP[20])) + "," + ((c.playerXP[0]) + (c.playerXP[1]) + (c.playerXP[2]) + (c.playerXP[3]) + (c.playerXP[4]) + (c.playerXP[5]) + (c.playerXP[6]) + (c.playerXP[7]) + (c.playerXP[8]) + (c.playerXP[9]) + (c.playerXP[10]) + (c.playerXP[11]) + (c.playerXP[12]) + (c.playerXP[13]) + (c.playerXP[14]) + (c.playerXP[15]) + (c.playerXP[16]) + (c.playerXP[17]) + (c.playerXP[18]) + (c.playerXP[19]) + (c.playerXP[20])) + ");");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List getScore(String type) {
        int i = 1;
        List<String> list = new ArrayList<String>();
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select playerName, " + type + " from skills ORDER BY " + type + " desc LIMIT 10");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add("" + i + ") " + rs.getString("playerName") + " | " + type + ": @blu@ " + rs.getInt(type) + "");
                i++;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(list);
        return list;
    }
}
