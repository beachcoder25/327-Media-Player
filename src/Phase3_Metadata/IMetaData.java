package Phase3_Metadata;

/**
 *
 * @author Jonah
 */
public interface IMetaData {
    
    public void create(String fileName, String size);
    public String lists();
    public void delete(String fileName);
    public int append(String filename);
    public void move(String oldName, String newName);
}
