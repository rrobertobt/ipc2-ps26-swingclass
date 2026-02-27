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
    
    public boolean insert(int userId, String title) {
        String sql = "INSERT INTO todos (user_id, title, done) VALUES (?, ?, FALSE)";
        
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
                ){
            
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            
            return stmt.executeUpdate() == 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean toggleDone(int todoId){        
        String sql = "UPDATE todos SET done = NOT done WHERE id = ?";
        
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
                ){
               
            stmt.setInt(1, todoId);
            
            return stmt.executeUpdate() == 1;
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int todoId){        
        String sql = "DELETE FROM todos WHERE id = ?";
        
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
                ){
               
            stmt.setInt(1, todoId);
            
            return stmt.executeUpdate() == 1;
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


