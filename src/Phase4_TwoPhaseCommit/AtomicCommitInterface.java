/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Phase4_TwoPhaseCommit;

import Phase3_Other.ChordMessageInterface;

/**
 *
 * @author nicka
 */
public interface AtomicCommitInterface {
    
    public boolean canCommit(Transaction transaction);
    public void doCommit(Transaction transaction);
    public void doAbort(Transaction transaction);
    public void haveCommitted(Transaction transaction, ChordMessageInterface participant);
    public boolean getDecision (Transaction transaction);
    
}