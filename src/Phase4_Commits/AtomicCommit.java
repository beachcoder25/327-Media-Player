/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Phase4_Commits;

/**
 *
 * @author Jonah
 */
public interface AtomicCommit {
    
    public boolean canCommit(Transaction transaction);
    public void doCommit(Transaction transaction);
    public void doAbort(Transaction transaction);
    public void haveCommitted(Transaction transaction, Object participant); // Change second parameter
//    public boolean getDecision(Transaction transaction);
    public boolean getDecision();
    
}
