/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.model.players;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import main.util.DatabaseConnection;

/**
 *
 * @author Abdul
 */
public class AchievementHandler {

    private HashMap<Integer, Integer> progress = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> completed = new HashMap<Integer, Integer>();
    Player player;

    public void loadProgressAchievements(Player player) {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select achid,progress from achievements where state = 0 and charid = ?");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                progress.put(rs.getInt("achid"), rs.getInt("progress"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadCompletedAchievements(Player player) {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select achid,progress from achievements where state = 1 and charid = ?");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                completed.put(rs.getInt("achid"), rs.getInt("progress"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllAchievements(Player player) {
        loadCompletedAchievements(player);
        loadProgressAchievements(player);
    }

    public void saveProgressAchievements(Player player) {

    }
}
