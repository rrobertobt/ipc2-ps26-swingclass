/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.robertob.claseipc2.models;

/**
 *
 * @author robertob
 */
public class User {
    private int id;
    private String username;
    
    public User(int id, String username){
        this.id = id;
        this.username = username;
                
    }
    
    public int getId() {return id;}
    public String getUsername() {return username;}
}
