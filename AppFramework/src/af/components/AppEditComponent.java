/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package af.components;

/**
 *
 * @author dnlkhuu77
 */
public interface AppEditComponent {
    public void saveData(AppDataComponent data, String filePath);
    public void loadData(AppDataComponent data, String filePath);
    public void exportData(AppDataComponent data, String filePath);
    public void importData(AppDataComponent data, String filePath);
}
