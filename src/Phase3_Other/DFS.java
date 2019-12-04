package Phase3_Other;

import Phase3_Metadata.Metadata;
import Phase3_Metadata.Metafile;
import Server.MusicMeta;
import java.rmi.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.math.BigInteger;
import java.security.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/* JSON Format
{"file":
  [
     {"name":"MyFile",
      "size":128000000,
      "pages":
      [
         {
            "guid":11,
            "size":64000000
         },
         {
            "guid":13,
            "size":64000000
         }
      ]
      }
   ]
} 
*/


public class DFS
{
    int port;
    public Chord  chord;
    
    
    private long md5(String objectName)
    {
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(objectName.getBytes());
            BigInteger bigInt = new BigInteger(1,m.digest());
            return Math.abs(bigInt.longValue());
        }
        catch(NoSuchAlgorithmException e)
        {
                e.printStackTrace();
                
        }
        return 0;
    }
    
    
    
    public DFS(int port) throws Exception
    {
        
        
        this.port = port;
        long guid = md5("" + port);
        chord = new Chord(port, guid);
        Files.createDirectories(Paths.get(guid+"/repository"));
        Files.createDirectories(Paths.get(guid+"/tmp"));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                chord.leave();
            }
        });
        
    }
    
  
/**
 * Join the chord
  *
 */
    public void join(String Ip, int port) throws Exception
    {
        chord.joinRing(Ip, port);
        chord.print();
    }
    
    
   /**
 * leave the chord
  *
 */ 
    public void leave() throws Exception
    {        
       chord.leave();
    }
  
   /**
 * print the status of the peer in the chord
  *
 */
    public void print() throws Exception
    {
        chord.print();
    }
    
    public static void main(String[] args){
        
        try {
            DFS dfs = new DFS(9999);
            dfs.readMetaData();
            
        } catch (Exception ex) {
            Logger.getLogger(DFS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/**
 * readMetaData read the metadata from the chord
  *
 */
    public Metadata readMetaData() throws Exception
    {
        // FilesJson should be a list of metafiles
        Metadata filesJson = null;
        //FilesJson filesJson = null;
        try {
            Gson gson = new Gson();
            long guid = md5("Metadata");

            System.out.println("GUID " + guid);
            ChordMessageInterface peer = chord.locateSuccessor(guid);
            RemoteInputFileStream metadataraw = peer.get(guid);
            metadataraw.connect();
            Scanner scan = new Scanner(metadataraw);
            scan.useDelimiter("\\A");
            String strMetaData = scan.next();
            System.out.println(strMetaData);
            filesJson= gson.fromJson(strMetaData, Metadata.class);
        } catch (Exception ex)
        {
            filesJson = new Metadata(); // All files are null
        }
        return filesJson;
    }
    
/**
 * writeMetaData write the metadata back to the chord
  *
 */
    public void writeMetaData(Metadata filesJson) throws Exception
    {
        long guid = md5("Metadata"); // Metadata sector 
        
        Gson gson = new Gson();
        //gson.
        //gson.fromJson(filesJson, Metadata.class).toJson()
        //ystem.out.println(gson.fromJson(filesJson, Metadata.class).toJson());
        
        ChordMessageInterface peer = chord.locateSuccessor(guid); // Dont worry about this too much 
        
       // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String s = gson.toJson(filesJson); // Takes JSON object as a string, use GSON prettyBuilder
        //stem.out.println(gson.toJson(filesJson));
        peer.put(guid, filesJson.serializeMetadata());
        
        //String s = gson.toJson(filesJson); // Takes JSON object as a string, use GSON prettyBuilder
//        try{
//            // Create writer, write, close.
//            FileWriter write = new FileWriter("metadata.json", false);
//            write.write(s);
//            write.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
    }
   
/**
 * Change Name
  *
 */
    public void move(String oldName, String newName) throws Exception
    {
        // TODO:  Change the name in Metadata
        // Write Metadata
        
        Metadata mData = readMetaData();
        mData.move(oldName, newName);
        writeMetaData(mData);
    }

  
/**
 * List the files in the system
  *
 * @param filename Name of the file
 */
    public String lists() throws Exception
    {
        //FilesJson fileJson = readMetaData();
        Metadata mData = readMetaData();
       
        return mData.lists();
    }

/**
 * create an empty file 
  *
 * @param filename Name of the file
 */
    public void create(String fileName, long fileSize) throws Exception
    {
         // TODO: Create the file fileName by adding a new entry to the Metadata
        // Write Metadata

        Metadata mData = readMetaData();
        
        // add file to meta data
        mData.create(fileName, fileSize);
        
        // At the end write again if you make a change
        writeMetaData(mData);
        
        // NEXT STEPS:
        // Most idifficult will be integrating with musicStreaming
        // Consider when you move
        
        
    }
    
/**
 * delete file 
  *
 * @param filename Name of the file
 */
    public void delete(String fileName) throws Exception
    {
        // read metadata
        Metadata mData = readMetaData();
        
        mData.delete(fileName);
        
        // TODO: add in deletion of pages and pointers attached to the metafile
        
        writeMetaData(mData);
    }
    
/**
 * Read block pageNumber of fileName 
  *
 * @param filename Name of the file
 * @param pageNumber number of block. 
 */
    public RemoteInputFileStream read(String fileName, int pageNumber) throws Exception
    {
        return null;
    }
    
 /**
 * Add a page to the file                
  *
 * @param filename Name of the file
 * @param data RemoteInputStream. 
 */
    public void append(String filename, RemoteInputFileStream data) throws Exception
    {
        // data passed in is the new page
        // read metadata
        Metadata mData = readMetaData();
        int[] pageGUIDs = mData.appendCopies(filename, data);
        
        
        for(int pageGUID : pageGUIDs){
            chord.locateSuccessor(pageGUID);
            chord.put(pageGUID, data);
            writeMetaData(mData);
        }
        
        
        
    }
    
    public Metafile searchFile(String s) {
        Gson gson = new Gson();
        
        TypeToken<List<Metafile>> token = new TypeToken<List<Metafile>>() {};
        
        ArrayList<Metafile> mf = new ArrayList();
        
        
        try {
            // File read object.
            Reader read = new FileReader("metadata.json");
            // GSON
            mf = new Gson().fromJson(read, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        for (Metafile M : mf) {
            if (M.getName().equals(s)) {
                return M;
            }
        }
        return null;
    }
    
    
    // Create file 
     
    
}