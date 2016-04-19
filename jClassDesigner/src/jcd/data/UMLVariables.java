
package jcd.data;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLVariables extends Text {
    private String name;
    private String type; //change this to the combobox
    private boolean statictype;
    private boolean accesstype; //false means private
    //use an int flag to tell if variable is private, public, or protected
    
    
    public UMLVariables(){
        name = " ";
        type = "int";
        statictype = false;
        accesstype = false;
    }
    
    public UMLVariables(String n, String t, boolean st, boolean at){
        name = n;
        type = t;
        statictype = st;
        accesstype = at;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the isAbstract
     */
    public boolean isStatictype() {
        return statictype;
    }

    /**
     * @param isStatic the isAbstract to set
     */
    public void setStatictype(boolean statictype) {
        this.statictype = statictype;
    }

    /**
     * @return the access
     */
    public boolean isAccesstype() {
        return accesstype;
    }

    /**
     * @param access the access to set
     */
    public void setAccesstype(boolean accesstype) {
        this.accesstype = accesstype;
    }
    
    @Override
    public String toString(){
        String s = new String();
        if(statictype == true)
            s = s + "+ ";
        else
            s = s + "- ";
        
        if(accesstype == true)
            s = s + "$ ";
        
        s = s + name + ": " + type;
        return s;
    }
}
