package af.components;

import java.io.IOException;

/**
 * This interface provides the structure for file components in
 * our applications. Note that by doing so we make it possible
 * for customly provided descendent classes to have their methods
 * called from this framework.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface AppFileComponent {
    public void saveData(AppDataComponent data, String filePath) throws IOException;
    public void loadData(AppDataComponent data, String filePath) throws IOException;
    public void exportData(AppDataComponent data, String filePath) throws IOException;
    public void importData(AppDataComponent data, String filePath) throws IOException;
    /*
    public void selectData(AppDataComponent data);
    public void resizeData(AppDataComponent data);
    public void addClasses(AppDataComponent data);
    public void addInterferes(AppDataComponent data);
    public void remove(AppDataComponent data);
    public void undo(AppDataComponent data);
    public void redo(AppDataComponent data);
    
    public void zoomin(AppDataComponent data);
    public void zoomout(AppDataComponent data);
    
    public void gridding(AppDataComponent data);
    public void snapping(AppDataComponent data);
    */
}
