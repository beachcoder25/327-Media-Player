
package Phase4_Commits;

import Phase3_Metadata.Metafile;
import Phase3_Metadata.Page;
import Phase4_Commits.Transaction.Operation;
import Phase4_Commits.Transaction.Vote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonah
 */
public class Participant {
    
    static int copyID = 0;
    Transaction transaction;
    boolean canSave = true;
    private TypeToken<List<Metafile>> token = new TypeToken<List<Metafile>>() {
    };
    
    
    public Participant(){
        
        
    }
    
        static public void main(String args[]) throws Exception {
        
        Transaction t0 = new Transaction("musicJSON", 0, "50");
        Transaction t1 = new Transaction("musicJSON", 1, "77265");
        Transaction t2 = new Transaction("musicJSON", 2, "80000");
       
        Participant p = new Participant(t0);
        Participant p1 = new Participant(t1);
        Participant p2 = new Participant(t2);
        
        
        /*     test for pull */
        
        
        Page page = new Page();
        long temp = p.pull("musicJSON", page);
        System.out.println("Here is read ts: " + temp + "\nHere is the transID: " + p.transaction.transactionID );
        System.out.println("Filepath: " + p.transaction.tempFileLocation + "\n");
        
        
        long temp1 = p1.pull("musicJSON", page);
        System.out.println("Here is read ts: " + temp1);
        System.out.println("Filepath: " + p1.transaction.tempFileLocation + "\nHere is the transID: " + p1.transaction.transactionID + "\n");
        
        
        long temp2 = p2.pull("musicJSON", page);
        System.out.println("Here is read ts: " + temp2);
        System.out.println("Filepath: " + p2.transaction.tempFileLocation + "\nHere is the transID: " + p2.transaction.transactionID );
        
        
        
        
        
        
        /* TEST FOR PUSH */
        
        // get what we currently have to test
//        TypeToken<List<Metafile>> token = new TypeToken<List<Metafile>>() {
//        };
//        
//        ArrayList<Metafile> metafileList = new ArrayList();
//        
//        try {
//            // Reader
//            Reader read = new FileReader("metadataTemp.json");
//            
//            // Use GSON
//            metafileList = new Gson().fromJson(read, token.getType());
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        // set a metafile to change
//        Metafile M = metafileList.get(0);
//        
//        // get readts and increment it
//        long readTS = Long.parseLong(M.getReadTS());
//        readTS++;
//        
//        // set write ts to incremented readts, original val didnt change
//        M.setWriteTS(Long.toString(readTS));
//        
//        // Instantiate
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        // Create JSON with GSON.
//        String jsonLine = gson.toJson(M);
//        try{
//            // Create writer, write, close.
//            FileWriter write = new FileWriter("taylorTempMetafile.json", false);
//            write.write(jsonLine);
//            write.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        
//        p.push("musicJSON");
    }
    
    public Participant(Transaction transaction){
        
        this.transaction = transaction;
        this.transaction.vote = Vote.YES;
    }
    
    public void makeChangesToFile(){
        
        // Make changes to file
        
        // Update Vote to yes because changes were made
        this.transaction.vote = Vote.YES;
    }

    
    // From lab 12/03/2019
    
    public void tempFileCopy(ArrayList<Metafile> mf_list, Reader read){
    
        //System.out.println("List length: " + mf_list.size());
        // Instantiate
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Create JSON with GSON.
        String jsonLine = gson.toJson(mf_list);
        //System.out.println("JSON DATA: \n" + jsonLine);
        
        
        
        FileWriter fileWriter;
        String tempCopyPath = "TEMP_FILEZ/" + copyID++ + "TempMetadata.json";
        
        File file = new File(tempCopyPath);
        this.transaction.setTempFileLocation(tempCopyPath);  
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write(jsonLine);
            fileWriter.close();    
        } catch (IOException ex) {
            Logger.getLogger(professorCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public long pull(String filename, Page page){
        
        // Store ReadTimeStamp in filename.transaction
        // return ReadTimeStamp
        
        
        ArrayList<Metafile> mf_list = new ArrayList();
       
        
        try {
            // Reader
            Reader read = new FileReader("metadata.json");

            // Use GSON
            mf_list = new Gson().fromJson(read, token.getType());
            
            // Make temp copy
            this.tempFileCopy(mf_list, read);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("MetaFile name: " + filename);
        int j = 0;
        String readTS = "";
        for (Metafile m : mf_list) {
            if (m.getName().equals(filename))
            {
                readTS = m.getReadTS();
                break;
            }
        }
       
        
        int i = 0;
        
        return Long.parseLong(readTS);
    }
    
    public boolean push(String filename){
        
        // Filepath to the temp file we are pushing
        
        /* THIS NEEDS TO BE UPDATED TO WHAT THE TEMP WILL BE CALLED */
        String temporaryFileFilepath = "taylorTempMetafile.json";
        
        // Read in the teamp file
        Metafile newMetafile;
        
        try {
            // Reader
            Reader read = new FileReader(temporaryFileFilepath);
            
            // Use GSON
            newMetafile = new Gson().fromJson(read, Metafile.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        // Read what is currently there
        ArrayList<Metafile> metafileList = new ArrayList();
        
        try {
            // Reader
            Reader read = new FileReader("metadata.json");
            
            // Use GSON
            metafileList = new Gson().fromJson(read, token.getType());
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        // Set array element with updated Metafile
        for(Metafile M : metafileList) {
            if(M.getName().equals(filename)) {
                System.out.println("M TS: " + M.getWriteTS());
                System.out.println("New TS: " + newMetafile.getWriteTS());
                M = newMetafile;
                System.out.println("Found metafile");
                
                System.out.println("After changes TS: " + M.getWriteTS() + "\n\n");
                
                break;
            }
        }
        
        /* Write to file */
        
        // Instantiate
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Create JSON with GSON.
        String jsonLine = gson.toJson(metafileList);
        //System.out.println("JSON DATA: \n" + jsonLine);
        try{
            // Create writer, write, close.
            FileWriter fileWriter = new FileWriter("metadataTemp.json", false);
            System.out.println("Old TS: " + newMetafile.getWriteTS());   
            metafileList.get(0).setWriteTS(newMetafile.getWriteTS());
            System.out.println("New TS: " + metafileList.get(0).getWriteTS());
            
            fileWriter.write(jsonLine);
            fileWriter.close();
            
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        
        // Write success, return true
        return true;
    }
    
    
    public void writeAction(){
        this.transaction.operation = Operation.WRITE;
    }
    
    public void deleteAction(){
        this.transaction.operation = Operation.DELETE;
    }
}
