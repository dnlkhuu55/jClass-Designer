/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dnlkhuu77
 */
public class UMLMArgs {
    private StringProperty argstype;
    
    public UMLMArgs(){
        argstype = new SimpleStringProperty("");
    }
    public UMLMArgs(String s){
        argstype = new SimpleStringProperty(s);
    }
    
    /**
     * @return the name
     */
    public String getArgstype() {
        return argstype.get();
    }

    /**
     * @param name the name to set
     */
    public void setArgstype(String args) {
        this.argstype.set(args);
    }
    
    public StringProperty argstypeProperty(){
        return argstype;
    }
    
}
