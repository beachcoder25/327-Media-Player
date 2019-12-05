
package Phase4_Commits;

import Phase4_Commits.Transaction.Operation;
import Phase4_Commits.Transaction.Vote;

/**
 *
 * @author Jonah
 */
public class Participant {
    
    Transaction transaction;
    boolean canSave = true;
    
    public Participant(Transaction transaction){
        
        this.transaction = transaction;
        this.transaction.vote = Vote.YES;
    }
    
    public void writeAction(){
        this.transaction.operation = Operation.WRITE;
    }
    
    public void deleteAction(){
        this.transaction.operation = Operation.DELETE;
    }
}
