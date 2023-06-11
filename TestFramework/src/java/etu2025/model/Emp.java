/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2025.model;

import etu2025.framework.ModelView;
import etu2025.framework.annotation.url;

/**
 *
 * @author tiavi
 */
public class Emp {
    String firstname;
    String lastname;
    String[] loisir;
    
    @url("/find-all.action")
    public ModelView findAll() {
        ModelView mv = new ModelView("list.jsp");
        mv.addItem("first_name", "John");
        mv.addItem("last_name", "Doe");
        return mv;
    }
    
    @url("/save.action")
    public ModelView save() {
        ModelView mv = new ModelView("list.jsp");
        mv.addItem("first_name", getFirstname());
        mv.addItem("last_name", getLastname());
        mv.addItem("loisir", getLoisir());
        return mv;
    }
    
    @url("/parent.action")
    public ModelView parent(String dadname, String momname) {
        ModelView mv = new ModelView("list.jsp");
        mv.addItem("first_name", dadname);
        mv.addItem("last_name", momname);
        mv.addItem("loisir", getLoisir());
        return mv;
    }
    
    
    
    @url("/input.action")
    public ModelView input() {
        ModelView mv = new ModelView("input.jsp");
        return mv;
    }
    
    

    public String[] getLoisir() {
        return loisir;
    }

    public void setLoisir(String[] loisir) {
        this.loisir = loisir;
    }
    
    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    
}
