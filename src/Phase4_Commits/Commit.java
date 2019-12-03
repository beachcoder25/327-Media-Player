package Phase4_Commits;

import java.util.ArrayList;

/**
 *
 * @author Jonah
 */
public class Commit implements AtomicCommit{

    // Will be used for collecting votes
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    
    public static void main(String[] args){
        Transaction t0 = new Transaction("music1", 0);
        Transaction t1 = new Transaction("music2", 1);
        Transaction t2 = new Transaction("music3", 2);
        
        Commit c0 = new Commit();
        
        c0.transactionList.add(t0);
        c0.transactionList.add(t1);
        c0.transactionList.add(t2);
        
        System.out.println(c0.transactionList.size());
        System.out.println(t0.fileName);
    }
    
    
    @Override
    public boolean canCommit(Transaction transaction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doCommit(Transaction transaction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doAbort(Transaction transaction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void haveCommitted(Transaction transaction, Object participant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDecision(Transaction transaction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
