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
public interface CohortInterface {
    public void haveCommitted(Transaction transaction);
    public boolean getDecision(Transaction transaction);
}
