/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Phase4_TwoPhaseCommit;

/**
 *
 * @author nicka
 */
public interface CoordinatorInterface {
    
    public boolean canCommit(Transaction transaction);
    public void doCommit(Transaction transaction);
    public void doAbort(Transaction transaction);
    
}
