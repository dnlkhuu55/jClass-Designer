
package jcd.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLVariables{
    
    final StringProperty name;
    final StringProperty type;
    final BooleanProperty statictype;
    final StringProperty accesstype;
    
    public UMLVariables(){
        this.name = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("int");
        this.statictype = new SimpleBooleanProperty(false);
        this.accesstype = new SimpleStringProperty("public");
    }
    
    public UMLVariables(String n, String t, boolean st, String at){
        this.name = new SimpleStringProperty(n);
        this.type = new SimpleStringProperty(t);
        this.statictype = new SimpleBooleanProperty(st);
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
    
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type.get();
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type.set(type);
    }
    
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * @return the isAbstract
     */
    public boolean isStatictype() {
        return statictype.get();
    }

    /**
     * @param isStatic the isAbstract to set
     */
    public void setStatictype(boolean statictype) {
        this.statictype.set(statictype);
    }
    
    public BooleanProperty statictypeProperty() {
        return statictype;
    }

    /**
     * @return the access
     */
    public String getAccesstype() {
        return accesstype.get();
    }

    /**
     * @param access the access to set
     */
    public void setAccesstype(String accesstype) {
        this.accesstype.set(accesstype);
    }
    
    public StringProperty accesstypeProperty() {
        return accesstype;
    }
    
    @Override
    public String toString(){
        String s = new String();
        if(getAccesstype().equals("public"))
            s = s + "+ ";
        else if(getAccesstype().equals("private"))
            s = s + "- ";
        else
            s = s + "# ";
        
        if(isStatictype() == true)
            s = s + "$ ";
        
        s = s + getName() + ": " + getType();
        return s;
    }
}
