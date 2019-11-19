
package filesplit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Taylor Meyer
 */
public class Splitter {
    
    private TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
    private ArrayList<MusicMeta> masterlist = new ArrayList();
    
    public Splitter() {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try {
            // File read object.
            Reader read = new FileReader("music.json");
            // GSON
            masterlist = new Gson().fromJson(read, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Size of master: " + masterlist.size());
        System.out.println("1/3 of master: " + masterlist.size()/3);
        
        String fp = "music";
        
        int size = masterlist.size();
        int start = 0;
        int stop = size/3;
        
        for(int i = 0; i < 3; i++) {
            
            ArrayList<MusicMeta> temp = new ArrayList();
            
            for (int j = 0; j < stop; j++) {
                
                temp.add(masterlist.get(j));
                
            }
            System.out.println("Size of temp: " + temp.size());
            start = stop ;
            stop += stop;
            while (stop > masterlist.size()-1) stop--;
            
            String jsonLine = gson.toJson(temp);
            
            try{
                
            FileWriter writer = new FileWriter(fp + Integer.toString(i+1) + ".json"); 
            
            writer.write(jsonLine);
            writer.close();
            
            }catch(Exception E) {
                E.printStackTrace();
            }
        }
        
    }
}
