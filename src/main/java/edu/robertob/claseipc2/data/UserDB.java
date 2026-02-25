/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.robertob.claseipc2.data;

import edu.robertob.claseipc2.ConexionDB;
import edu.robertob.claseipc2.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author robertob
 */
public class UserDB {
    public User login(String username, String password) {
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
             ){
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    User foundUser = new User(
                            rs.getInt("id"),
                            rs.getString("username")
                    );
                    return foundUser;
                }
            }
                        
        } catch (Exception e) {
            System.out.println("Error al hacer login"+e);
        }
        return null;
    }
}
