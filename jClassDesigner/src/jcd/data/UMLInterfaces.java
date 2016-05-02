/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLInterfaces extends VBox{
    private Text interName;
    private String interNametoString;
    private ArrayList<UMLVariables> variableNames = new ArrayList<>(); //must be capitalized and static
    private ArrayList<UMLMethods> methodNames = new ArrayList<>(); //must all be abstract
    //variables, methods must be modified.
    
    private Text currentVariableName;
    private Text currentMethodName;
    private String packageName;
    
    private double translateXer;
    private double translateYer;
    
    private double widthy;
    private double heighty;
    
    private VBox interfaces;
    private VBox variables;
    private VBox method;
    
    public UMLInterfaces(String a){
        interNametoString = a;
        interName = new Text(a);
        currentVariableName = new Text("");
        currentMethodName = new Text("");
        packageName = "";
        translateXer = 20;
        translateYer = 30;
        
        interfaces = new VBox();
        variables = new VBox();
        method = new VBox();
        
        interfaces.getChildren().add(interName);
        variables.getChildren().add(currentVariableName);
        method.getChildren().add(currentMethodName);
        interfaces.setMargin(interName, new Insets(5));
        variables.setMargin(currentVariableName, new Insets(5));
        method.setMargin(currentMethodName, new Insets(5));
        
        interfaces.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        variables.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        method.setStyle("-fx-background-color: #ffffff");
        
        this.getChildren().add(interfaces);
        this.getChildren().add(variables);
        this.getChildren().add(method);
    }
    
    public UMLInterfaces(String a, String variabley, String methody, String packagey,
            double x_er, double y_er){
        interNametoString = a;
        interName = new Text(a);
        currentVariableName = new Text(variabley);
        currentMethodName = new Text(methody);
        packageName = packagey;
        translateXer = x_er;
        translateYer = y_er;
        
        interfaces = new VBox();
        variables = new VBox();
        method = new VBox();
        
        interfaces.getChildren().add(interName);
        variables.getChildren().add(currentVariableName);
        method.getChildren().add(currentMethodName);
        interfaces.setMargin(interName, new Insets(5));
        variables.setMargin(currentVariableName, new Insets(5));
        method.setMargin(currentMethodName, new Insets(5));
        
        interfaces.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        variables.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        method.setStyle("-fx-background-color: #ffffff");
        
        this.getChildren().add(interfaces);
        this.getChildren().add(variables);
        this.getChildren().add(method);
    }

    /**
     * @return the interName
     */
    public Text getInterName() {
        return interName;
    }

    /**
     * @param interName the interName to set
     */
    public void setInterName(String name) {
        interName.setText(name);
        interfaces.getChildren().clear();
        interfaces.getChildren().add(interName);
    }

    /**
     * @return the interNametoString
     */
    public String getInterNametoString() {
        return interNametoString;
    }

    /**
     * @param interNametoString the interNametoString to set
     */
    public void setInterNametoString(String interNametoString) {
        this.interNametoString = interNametoString;
    }

    /**
     * @return the variableNames
     */
    public ArrayList<UMLVariables> getVariableNames() {
        return variableNames;
    }

    /**
     * @param variableNames the variableNames to set
     */
    public void setVariableNames(ArrayList<UMLVariables> variableNames) {
        this.variableNames = variableNames;
    }

    /**
     * @return the methodNames
     */
    public ArrayList<UMLMethods> getMethodNames() {
        return methodNames;
    }

    /**
     * @param methodNames the methodNames to set
     */
    public void setMethodNames(ArrayList<UMLMethods> methodNames) {
        this.methodNames = methodNames;
    }

    /**
     * @return the currentVariableName
     */
    public Text getCurrentVariableName() {
        return currentVariableName;
    }

    /**
     * @param currentVariableName the currentVariableName to set
     */
    public void setCurrentVariableName(Text currentVariableName) {
        this.currentVariableName = currentVariableName;
        variables.getChildren().add(currentVariableName);
    }
    
    public void removeCurrentVariableName(Text t){
        variables.getChildren().remove(t);
    }

    /**
     * @return the currentMethodName
     */
    public Text getCurrentMethodName() {
        return currentMethodName;
    }

    /**
     * @param currentMethodName the currentMethodName to set
     */
    public void setCurrentMethodName(Text currentMethodName) {
        this.currentMethodName = currentMethodName;
        method.getChildren().add(currentMethodName);
    }
    
    public void removeCurrentMethodName(Text t){
        method.getChildren().remove(t);
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the translateXer
     */
    public double getTranslateXer() {
        return translateXer;
    }

    /**
     * @param translateXer the translateXer to set
     */
    public void setTranslateXer(double translateXer) {
        this.translateXer = translateXer;
    }

    /**
     * @return the translateYer
     */
    public double getTranslateYer() {
        return translateYer;
    }

    /**
     * @param translateYer the translateYer to set
     */
    public void setTranslateYer(double translateYer) {
        this.translateYer = translateYer;
    }

    /**
     * @return the widthy
     */
    public double getWidthy() {
        return widthy;
    }

    /**
     * @param widthy the widthy to set
     */
    public void setWidthy(double widthy) {
        this.widthy = widthy;
    }

    /**
     * @return the heighty
     */
    public double getHeighty() {
        return heighty;
    }

    /**
     * @param heighty the heighty to set
     */
    public void setHeighty(double heighty) {
        this.heighty = heighty;
    }

    /**
     * @return the interfaces
     */
    public VBox getInterfaces() {
        return interfaces;
    }

    /**
     * @param interfaces the interfaces to set
     */
    public void setInterfaces(VBox interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * @return the variables
     */
    public VBox getVariables() {
        return variables;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(VBox variables) {
        this.variables = variables;
    }

    /**
     * @return the method
     */
    public VBox getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(VBox method) {
        this.method = method;
    }
    
    
}
