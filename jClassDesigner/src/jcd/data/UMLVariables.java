
package jcd.data;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
    private String accesstype; //false means private
    //use an int flag to tell if variable is private, public, or protected
    private TextField naming;
    private TextField typing;
    private CheckBox staticing;
    private ComboBox accessing;
    private HBox table1;
    
    
    public UMLVariables(){
        name = " ";
        type = "int";
        statictype = false;
        accesstype = "public";
    }
    
    public UMLVariables(String n, String t, boolean st, String at){
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
    public String getAccesstype() {
        return accesstype;
    }

    /**
     * @param access the access to set
     */
    public void setAccesstype(String accesstype) {
        this.accesstype = accesstype;
    }
    
    public HBox settingStuff(){
        table1 = new HBox();
        naming = new TextField();
        naming.setPrefWidth(100);
        typing = new TextField();
        typing.setPrefWidth(100);
        staticing = new CheckBox();
        staticing.setPrefWidth(100);
        
        accessing = new ComboBox();
        accessing.getItems().addAll("public", "private", "protected");
        accessing.setPrefWidth(100);
        
        table1.getChildren().addAll(naming, typing, staticing, accessing);
        
        return table1;
    }
    
    @Override
    public String toString(){
        String s = new String();
        if(accesstype.equals("public"))
            s = s + "+ ";
        else if(accesstype.equals("private"))
            s = s + "- ";
        else
            s = s + "# ";
        
        if(statictype == true)
            s = s + "$ ";
        
        s = s + name + ": " + type;
        return s;
    }
}
