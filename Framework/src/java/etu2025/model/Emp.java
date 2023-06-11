/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2025.model;

import etu2025.framework.annotation.url;

/**
 *
 * @author tiavi
 */
public class Emp {
    String first_name;
    String last_name;
    
    @url("/find-all.action")
    public String findAll() {
        System.out.println("Tiavina Malalaniaina");
        return "Tiavina Malalaniaina";
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    
}
