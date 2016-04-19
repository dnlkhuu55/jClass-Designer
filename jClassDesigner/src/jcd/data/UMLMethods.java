
package jcd.data;

import java.util.ArrayList;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLMethods extends Text{
    private String name;
    private String returntype;
    private boolean statictype;
    private boolean abstractype;
    private boolean accesstype;
    private ArrayList<String> args = new ArrayList<>();
    
    public UMLMethods(){
        name = "DEFAULT";
        returntype = "void";
        statictype = false;
        abstractype = false;
        accesstype = false;
    }
    
    public UMLMethods(String n, String r, boolean s, boolean a, boolean at){
        name = n;
        returntype = r;
        statictype = s;
        abstractype = a;
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
     * @return the returnType
     */
    public String getReturntype() {
        return returntype;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturntype(String returnType) {
        this.returntype = returnType;
    }

    /**
     * @return the statictype
     */
    public boolean isStatictype() {
        return statictype;
    }

    /**
     * @param statictype the statictype to set
     */
    public void setStatictype(boolean statictype) {
        this.statictype = statictype;
    }

    /**
     * @return the abstractype
     */
    public boolean isAbstractype() {
        return abstractype;
    }

    /**
     * @param abstractype the abstractype to set
     */
    public void setAbstractype(boolean abstractype) {
        this.abstractype = abstractype;
    }

    /**
     * @return the accesstype
     */
    public boolean isAccesstype() {
        return accesstype;
    }

    /**
     * @param accesstype the accesstype to set
     */
    public void setAccesstype(boolean accesstype) {
        this.accesstype = accesstype;
    }

    /**
     * @return the args
     */
    public ArrayList<String> getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }
    public String toString(){
        String s = new String();
        if(accesstype == true)
            s = s + "+";
        else
            s = s + "-";
        if(abstractype == true)
            s = s + "{abstract}";
        
        s = s + name + "(";
        int counter = 0;
        for(String arguments: args){
            s = s + "arg" + counter + ": " + arguments + ", ";
            counter++;
        }
        s = s + "): " + returntype;
        
        
        return s;
    }
    
}
