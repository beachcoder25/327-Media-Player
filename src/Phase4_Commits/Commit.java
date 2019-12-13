package Phase4_Commits;

import Phase3_Metadata.Metafile;
import java.util.ArrayList;
import java.util.List;
import Phase3_Other.Chord;
import Phase4_Commits.Transaction.Vote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Jonah
 */
public class Commit implements AtomicCommit {

    // Will be used for collecting votes
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    private ArrayList<Participant> participantList = new ArrayList<Participant>();
    private ArrayList<String> voteResults = new ArrayList<String>();
    private Participant chosenParticipant = new Participant();

    private TypeToken<List<Metafile>> token = new TypeToken<List<Metafile>>() {
    };

    public static void main(String[] args) {

        Transaction t0 = new Transaction("musicJSON", 0, "50");
        Transaction t1 = new Transaction("musicJSON", 1, "77265");
        Transaction t2 = new Transaction("musicJSON", 2, "80000");
        Commit c = new Commit();

        System.out.println(c.canCommit(t0));
        System.out.println(c.canCommit(t1));
        System.out.println(c.canCommit(t2));
    }

    @Override
    public boolean canCommit(Transaction transaction) {

        Gson gson = new Gson(); // gson reader to find writeTimeStamp

        ArrayList<Metafile> mf_list = new ArrayList();
        try {
            // Reader
            Reader read = new FileReader("metadata.json");
            // Use GSON
            mf_list = new Gson().fromJson(read, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        long writeTS = 0;

        //System.out.println(transaction.getFileName());
        for (Metafile m : mf_list) {
            //System.out.println("here");
            if (m.getName().equals(transaction.getFileName())) {
                //System.out.println("here2");
                Long.parseLong(m.getWriteTS());
                writeTS = Long.parseLong(m.getWriteTS());
                break;
            }
        }

        //System.out.println(Long.parseLong(transaction.getReadTime()));
        //System.out.println(writeTS);

        // Read occured after last write which tells us that
        // this is the most recent change being committed.
        if (Long.parseLong(transaction.getReadTime()) > writeTS) {
            return true;
        }

        // else return false
        return false;
    }

    @Override
    public void doCommit(Transaction transaction) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Push was a success! Changes from "+ this.chosenParticipant.transaction.tempFileLocation + " will persist" );
    }

    @Override
    public void doAbort(Transaction transaction) {
        System.out.println("Please pull before commiting changes, your code is outdated");
    }

    @Override
    public void haveCommitted(Transaction transaction, Object participant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDecision() {

        boolean returnValue = false;
        int noCount = 0;
        for (Participant p : participantList) {

            if (p.transaction.getStringVote().equals("YES")) {
                if (this.canCommit(p.transaction)) {
                    this.chosenParticipant = p;
                    doCommit(p.transaction);
                    returnValue = true;
                }
            } 
            else if (p.transaction.getStringVote().equals("NO")) {
                doAbort(p.transaction);  
            }
            
            
        }
        
        

        return returnValue;
    }

    public void addParticipant(Participant p) {
        this.participantList.add(p);
    }

    public int getListSize() {
        return this.participantList.size();
    }

}
