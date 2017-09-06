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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import main.util.DatabaseConnection;

/**
 *
 * @author Abdul
 */
public class AchievementHandler {

    private HashMap<Integer, Integer> progress = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> completed = new HashMap<Integer, Integer>();
    Player player;

    public AchievementHandler(Player a) {
        this.player = a;
    }

    public void loadProgressAchievements() {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select achid,progress from achievements where state = 0 and charid = ?");
            ps.setString(1, player.playerName);
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

    public void loadCompletedAchievements() {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select achid,progress from achievements where state = 1 and charid = ?");
            ps.setString(1, player.playerName);
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

    public void loadAllAchievements() {
        loadCompletedAchievements();
        loadProgressAchievements();
    }

    public void saveProgressAchievements() {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        Set<Map.Entry<Integer, Integer>> entrySet = progress.entrySet();
        try {
            ps = con.prepareStatement("delete * from achievements where charid = ? and state = 0");
            ps.setString(1, player.playerName);
            ps.executeQuery();
            for (Entry entry : entrySet) {
                ps = con.prepareStatement("insert into achievements (charid,achid,progress,state) values (?,?,?,?)");
                ps.setString(1, player.playerName);
                ps.setInt(2, (int) entry.getKey());
                ps.setInt(3, (int) entry.getValue());
                ps.setInt(4, 0);
                ps.executeQuery();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCompletedAchievements() {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        Set<Map.Entry<Integer, Integer>> entrySet = completed.entrySet();
        try {
            ps = con.prepareStatement("delete * from achievements where charid = ? and state = 1");
            ps.setString(1, player.playerName);
            ps.executeQuery();
            for (Entry entry : entrySet) {
                ps = con.prepareStatement("insert into achievements (charid,achid,progress,state) values (?,?,?,?)");
                ps.setString(1, player.playerName);
                ps.setInt(2, (int) entry.getKey());
                ps.setInt(3, (int) entry.getValue());
                ps.setInt(4, 1);
                ps.executeQuery();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void completeAchievement(int achid) {
        java.sql.Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("delete * from achievements where charid = ? and achid = " + achid);
            ps.setString(1, player.playerName);
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
