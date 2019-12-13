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
    
    static int IDcount = 0;
    int transactionID = 0;
    String fileName;
    int pageIndex;
    int fileIDNum;
    String prefix = "./Pages_JSON/";
    String tempFileLocation = "";

    public void setTempFileLocation(String tempFileLocation) {
        this.tempFileLocation = tempFileLocation;
    }

    public String getTempFileLocation() {
        return tempFileLocation;
    }
    String readTime;

    public Transaction(String fileName, int fileIDNum, String readTime) {
        
        
        this.fileName = fileName;
        this.fileIDNum = fileIDNum;
        this.readTime = readTime;
        this.transactionID = IDcount++;
        
    }

    public int getFileIDNum() {
        return fileIDNum;
    }

    public void setFileIDNum(int fileIDNum) {
        this.fileIDNum = fileIDNum;
    }

    public Operation getOperation() {
        return operation;
    }

    public Vote getVote() {
        return vote;
    }

    public String getFileName() {
        return fileName;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public static void main(String[] args){
        Transaction t0;
    }
}
