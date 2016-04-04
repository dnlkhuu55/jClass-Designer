package jcd.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import af.components.AppDataComponent;
import af.AppTemplate;
import javafx.scene.layout.GridPane;
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
    ArrayList<GridPane> classList = new ArrayList<GridPane>();
    
    public ArrayList<GridPane> getClassList(){
        return classList;
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
}
