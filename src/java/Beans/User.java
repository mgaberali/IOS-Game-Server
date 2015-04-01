/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author Amal
 */
public class User {
    private String name;
    private String email;
    private String password;
    private String imageName;
    private int score;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getImageName() {
        return imageName;
    }

    public int getScore() {
        return score;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    
    
}
