
package pkg327;

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
public class Catalog {
    
    private String remoteMethod;
    private String objectName;
    private List<Param> Params; // Iterate as a hashmapsh
    private String callSemantic;
    private String returnType;
    private TypeToken<List<Param>> token = new TypeToken<List<Param>>() {};
    
    
        public Catalog(String remoteMethod, String objectName, List<Param>  params, String callSemantic, String returnType){
        this.remoteMethod = remoteMethod;
        this.objectName = objectName;
        this.Params = params;
        this.callSemantic = callSemantic;
        this.returnType = returnType;
    }
    
    
    public static void main(String[] args){
        
        
        System.out.println("TEST");
        Gson g = new Gson();
        
        
      
        try (Reader read = new FileReader("catalog.json")) { // Desktop
           
            
            
            
            Catalog[] c = new Gson().fromJson(read, Catalog[].class);
//            System.out.println(c.getRemoteMethod());

            for(Catalog cat : c){
                System.out.println(cat.getRemoteMethod());
            
            }
            
            int i = 0;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public String getRemoteMethod() {
        return remoteMethod;
    }

    public String getobjectName() {
        return objectName;
    }

    public List<Param> getParams() {
        return Params;
    }

    public String getCallSemantic() {
        return callSemantic;
    }

    public String getReturnType() {
        return returnType;
    }
        


//    public String[] displayParams(){
//        
//        String s = "";
//                
//        for(Params p :Params){
//            s = "user: " + p.getUser() + " playlistname: " + p.getPlaylistName() + " songId: " + p.getSongId();
//        }
//        
//        String[] retArray = s.split(" ");
//        return retArray;
//    }
//            

    public String getObjectName() {
        return objectName;
    }

    public TypeToken<List<Param>> getToken() {
        return token;
    }
    
    
}
