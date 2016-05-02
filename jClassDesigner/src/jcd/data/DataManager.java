package jcd.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import af.components.AppDataComponent;
import af.AppTemplate;
import java.util.Stack;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import jcd.gui.Workspace;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DataManager implements AppDataComponent {
    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    ArrayList<VBox> classList = new ArrayList<>();
    ArrayList<ClassLines> lineList = new ArrayList<>();
    Pane leftPane = new Pane(); //save the leftPane (zoom in and out)
    Stack undoStack = new Stack();
    Stack redoStack = new Stack();
    
    public ArrayList<VBox> getClassList(){
        return classList;
    }
    public void setClassList(ArrayList<VBox> s){
        this.classList = s;
    }
    public ArrayList<ClassLines> getLineList(){
        return lineList;
    }
    public void setLineList(ArrayList<ClassLines> l){
        this.lineList = l;
    }
    
    public void setLeftPane(Pane l){
        leftPane = l;
    }
    public Pane getLeftPane(){
        return leftPane;
    }
    public void undoing(){
        undoStack.push(this);
    }
    public void loadUndo(){
        DataManager s = (DataManager) undoStack.pop();
        this.setClassList(s.getClassList());
        this.setLineList(s.getLineList());
        this.setLeftPane(s.getLeftPane());
        reload();
        redoStack.push(s);
    }

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public DataManager(AppTemplate initApp) throws Exception {
	// KEEP THE APP FOR LATER
	app = initApp;
    }

    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void reset() {
        Workspace expo = (Workspace) app.getWorkspaceComponent();
        expo.resetWorkspace();
    }
    
    public void photo(){
        Workspace expo = (Workspace) app.getWorkspaceComponent();
        expo.photoGo();
    }

    public void reload() {
        Workspace expo = (Workspace) app.getWorkspaceComponent();
        expo.reloadWorkspace();
        expo.activateWorkspace(app.getGUI().getAppPane());
    }
}
