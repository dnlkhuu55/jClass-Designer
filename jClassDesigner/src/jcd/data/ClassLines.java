/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.scene.shape.Line;

/**
 *
 * @author dnlkhuu77
 */
public class ClassLines extends Line{
    //A line class that will set the intial node and final node.
    private String start_node;
    private String end_node;
    private double mid_x;
    private double mid_y;
    
    public ClassLines(){
        
    }
    
    public ClassLines(String start, String end){
        start_node = start;
        end_node = end;
        this.setStartX(this.getStartX());
        this.setStartY(this.getStartY());
        this.setEndX(this.getEndX());
        this.setEndY(this.getEndY());
        //set the x and y are already in the api
    }
    
    
    /**
     * @return the start_node
     */
    public String getStart_node() {
        return start_node;
    }

    /**
     * @param start_node the start_node to set
     */
    public void setStart_node(String start_node) {
        this.start_node = start_node;
    }

    /**
     * @return the end_node
     */
    public String getEnd_node() {
        return end_node;
    }

    /**
     * @param end_node the end_node to set
     */
    public void setEnd_node(String end_node) {
        this.end_node = end_node;
    }

    /**
     * @return the mid_x
     */
    public double getMid_x() {
        return mid_x;
    }

    /**
     * @param mid_x the mid_x to set
     */
    public void setMid_x() {
        //this.mid_x = mid_x;
        mid_x = this.getStartX() + this.getEndX();
        mid_x = mid_x / 2;
        
    }

    /**
     * @return the mid_y
     */
    public double getMid_y() {
        return mid_y;
    }

    /**
     * @param mid_y the mid_y to set
     */
    public void setMid_y() {
        mid_y = this.getStartY() + this.getEndY();
        mid_y = mid_y / 2;
    }
}
