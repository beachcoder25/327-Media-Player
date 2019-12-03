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
public class Coordinator implements CoordinatorInterface {

    @Override
    public boolean canCommit(Transaction transaction) {
        try {
            // create group of peers (cohorts) to send signal to
            
            // send transaction to get vote?
            
            // sleep to wait for vote
                // if sloloted time passes and not all participants have responded
                // send out doAbort and break
            
            // otherwise send out doCommit
        } catch (Exception e) {
            
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doCommit(Transaction transaction) {
        // tell coord to commit
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doAbort(Transaction transaction) {
        // tell coord to abort transaction
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // I believe that the Coordinator can be mapped to the server instance
    // the participants (cohorts) should be mapped to the CMI Peer instances?
    // or with these seperate classes being called by those instances.
    // Not totally sure
    
}
