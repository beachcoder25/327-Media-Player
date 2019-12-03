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
public class Cohort implements CohortInterface {

    @Override
    public void haveCommitted(Transaction transaction) {
        // before commit, save files to temp folder (.../guid/temp)
            // build out pathfile like in chord?
        
        // let coordinator know that you have committed
        
        // sleep for allotted time
        // if no response, then call get decision
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDecision(Transaction transaction) {
        // check in with server to see if all participants voted yes and have committed
        // coordinator sends back yes or no if can still commit
            // coordinator checks for all responses
            // if still missing responses, coord sends abort to all participants
        
        // to rollback, grab files from temp
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
