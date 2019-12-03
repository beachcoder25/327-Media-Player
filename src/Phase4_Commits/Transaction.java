package Phase4_Commits;

/**
 *
 * @author Jonah
 */
public class Transaction {
    
    // From Lecture Notes: When you read a file create a file called transaction
    public enum Operation{ WRITE, DELETE };
    public enum Vote{ YES, NO };
    int TransactionId = 0;
    Vote vote;
    String fileName;
    int pageIndex;
    String prefix = "./Pages_JSON/";

    public Transaction(String fileName, int pageIndex) {
        
        this.TransactionId = TransactionId++;
        this.fileName = prefix + fileName;
        this.pageIndex = pageIndex;
        
    }
    
    public static void main(String[] args){
        Transaction t0;
    }
}
