/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import af.components.AppDataComponent;
import java.util.Stack;

/**
 *
 * @author dnlkhuu77
 */
public class URManager {
    Stack undoStack = new Stack();
    Stack redoStack = new Stack();
    
    public URManager(){
    }
    
    public void undoing(AppDataComponent data){
        DataManager hello = (DataManager) data;
        undoStack.push(hello);
    }
    public void loadUndo(AppDataComponent data){
        DataManager test = (DataManager) data;
        DataManager s = (DataManager) undoStack.pop();
        test.setClassList(s.getClassList());
        test.setLineList(s.getLineList());
        test.setLeftPane(s.getLeftPane());
        redoStack.push(s);
    }
}
