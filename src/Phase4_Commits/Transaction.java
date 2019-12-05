package Phase4_Commits;

import Phase3_Metadata.Metafile;

/**
 *
 * @author Jonah
 */
public class Transaction {
    
    // From Lecture Notes: When you read a file create a file called transaction
    public enum Operation{ WRITE, DELETE };
    public enum Vote{ YES, NO };
    
    Operation operation;
    Vote vote;
    String fileName;
    int pageIndex;
    int fileIDNum;
    String prefix = "./Pages_JSON/";

    public Transaction(String fileName, int fileIDNum) {
        
        
        this.fileName = prefix + fileName;
        this.fileIDNum = fileIDNum;
        
    }
    

    public static void main(String[] args){
        Transaction t0;
    }
}
