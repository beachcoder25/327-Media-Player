package Phase3_Metadata;

import Server.Catalog;
import Server.Param;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 *
 * @author Jonah
 */
public class Tester {

    private TypeToken<List<Metafile>> token = new TypeToken<List<Metafile>>() {
    };
    private List<Metafile> metafileList;
    private String pageString;

    public Tester() {
        this.deserializeRemoteReferences();
    }

    private void deserializeRemoteReferences() {

        try (Reader read = new FileReader("metadata.json")) {

            metafileList = new Gson().fromJson(read, token.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMetafilePages(String name) {

        for (Metafile m : metafileList) {
            System.out.println(m.getName());
            if (m.getName().equals(name)) {
                List<Page> pageList = m.getPages();
                pageString = "";

                for (int i = 0; i < pageList.size(); i++) {
                    
                    pageString += "Page" + (i + 1) + " GUID: " + pageList.get(i).getGuid() + "\n";
                }
            }
        }
        return pageString;
    }

    public static void main(String[] args){
     
        Tester tester = new Tester();
        
        String name = "MusicJson";
        System.out.println(tester.getMetafilePages(name));
        
    }
}
