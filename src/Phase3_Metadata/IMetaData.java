package Phase3_Metadata;

import Phase3_Other.RemoteInputFileStream;

/**
 *
 * @author Jonah
 */
public interface IMetaData {
    
    public void create(String fileName, long size);
    public String lists();
    public void delete(String fileName);
    public int append(String filename, RemoteInputFileStream data);
    public void move(String oldName, String newName);
}
