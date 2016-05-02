
package jcd.data;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLMethods{
    
    private ArrayList<UMLMArgs> args = new ArrayList<>();
    
    final StringProperty name;
    final StringProperty returntype;
    final BooleanProperty statictype;
    final BooleanProperty abstractype;
    final StringProperty accesstype;
    
    public UMLMethods(){
        this.name = new SimpleStringProperty("DEFAULT");
        this.returntype = new SimpleStringProperty("void");
        this.statictype = new SimpleBooleanProperty(false);
        this.abstractype = new SimpleBooleanProperty(false);
        this.accesstype = new SimpleStringProperty("public");
    }
    
    public UMLMethods(String n, String r, boolean s, boolean a, String at){
        this.name = new SimpleStringProperty(n);
        this.returntype = new SimpleStringProperty(r);
        this.statictype = new SimpleBooleanProperty(s);
        this.abstractype = new SimpleBooleanProperty(a);
        this.accesstype = new SimpleStringProperty(at);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty(){
        return name;
    }

    /**
     * @return the returnType
     */
    public String getReturntype() {
        return returntype.get();
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturntype(String returntype) {
        this.returntype.set(returntype);
    }
    
    public StringProperty returntypeProperty(){
        return returntype;
    }

    /**
     * @return the statictype
     */
    public boolean isStatictype() {
        return statictype.get();
    }

    /**
     * @param statictype the statictype to set
     */
    public void setStatictype(boolean statictype) {
        this.statictype.set(statictype);
    }
    
    public BooleanProperty statictypeProperty(){
        return statictype;
    }

    /**
     * @return the abstractype
     */
    public boolean isAbstractype() {
        return abstractype.get();
    }

    /**
     * @param abstractype the abstractype to set
     */
    public void setAbstractype(boolean abstractype) {
        this.abstractype.set(abstractype);
    }
    
    public BooleanProperty abstractypeProperty(){
        return abstractype;
    }

    /**
     * @return the accesstype
     */
    public String getAccesstype() {
        return accesstype.get();
    }

    /**
     * @param accesstype the accesstype to set
     */
    public void setAccesstype(String accesstype) {
        this.accesstype.set(accesstype);
    }

    /**
     * @return the args
     */
    public ArrayList<UMLMArgs> getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(ArrayList<UMLMArgs> args) {
        this.args = args;
    }
    
    public void addUMLMArgs(UMLMArgs s){
        getArgs().add(s);
    }
    public void removeUMLArgs(UMLMArgs s){
        getArgs().remove(s);
    }
    
    //
    
    public String toString(){
        String s = new String();
        if(getAccesstype().equals("public"))
            s = s + "+";
        else if (getAccesstype().equals("private"))
            s = s + "-";
        else
            s = s + "#";
        
        if(isStatictype() == true)
            s = s + "$";
        
        if(isAbstractype() == true)
            s = s + "{abstract}";
        
        s = s + getName() + "(";
        
        int counter = 0;
        
        for(UMLMArgs m: args){
            s = s + "arg" + counter + ": " + m.getArgstype() + ", ";
            counter++;
        }

        s = s + "): " + getReturntype();
        
        return s;
    }
    
}
