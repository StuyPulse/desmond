/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.subsystems;

/**
 *
 * @author home
 */
public class CV {
    
    private static CV instance;
    
    public static CV getInstance(){
        if (instance == null) {
            instance = new CV();
        }
        return instance;
    }
    
    //TODO: implement
    public boolean isGoalHot() {
        return false;
    }
    
}
