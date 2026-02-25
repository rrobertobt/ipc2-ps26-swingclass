/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.robertob.claseipc2.data;

import edu.robertob.claseipc2.ConexionDB;
import java.util.List;
import edu.robertob.claseipc2.models.Todo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author robertob
 */
public class TodoDB {
    public List<Todo> findByUserId(int userId) {
        String sql = "SELECT id, user_id, title, done FROM todos WHERE user_id = ?";
        
        List<Todo> todosList = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
             ){
            stmt.setInt(1, userId);
            
        try (ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Todo todo = new Todo(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getBoolean("done")
                );
                todosList.add(todo);
            }
        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todosList;
    }
}
