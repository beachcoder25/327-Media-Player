
package Server;

import Phase3_Metadata.Metafile;
import Phase3_Metadata.Page;
import Phase3_Other.ChordMessageInterface;
import Phase3_Other.DFS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads accounts from the JSON file, validates the user, and registers
 * new accounts.
 * @author Taylor Meyer
 */
public class LoginServices {
    
    // Type to load the list of Accounts from JSON file.
    private TypeToken<List<Account>> token = new TypeToken<List<Account>>() {};
    // The ArrayList of Accounts.
    private ArrayList<Account> account_list;
    
    // User that successfull logged in.
    private Account current_account;
    
    DFS dfs;
    
    /**
     * Default constructor.
     * Loads the accounts from the JSON file.
     */
    public LoginServices() {
        this.deserializeAccounts();
    }
    
    /**
     * Loads the accounts from the JSON file.
     */
    private void deserializeAccounts(){
        try {
            // File read object.
            Reader read = new FileReader("accounts.json");
            // GSON
            account_list = new Gson().fromJson(read, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks login credentials against ArrayList of Accounts
     * to validate the user.
     * @param username user input
     * @param password user input
     * @return 
     */
    public boolean validateAccount(String username, String password) {
        
        
        //DFS dfs = new DFS(2000);
        
        Metafile metaFile = dfs.searchFile("accounts");
        int n = metaFile.getNumberOfPages();
        // Create n threads
        ArrayList<SearchPeerThreadA> spt = new ArrayList();
        ArrayList<Thread> threads = new ArrayList();

        for (int i = 0; i < n; i++) {
            Page p = metaFile.getPage(i);

            ChordMessageInterface peer = null;
            try {
                peer = dfs.chord.locateSuccessor(p.guid);
            } catch (RemoteException ex) {
                Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
            }

            //ChordMessageInterface peer, Long guid, String keyword
            SearchPeerThreadA pt = new SearchPeerThreadA(peer, p.guid, username, password);
            spt.add(pt);
            Thread T = new Thread(pt);
            threads.add(T);
            T.start();

        }

        ArrayList<Account> results = new ArrayList();

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (SearchPeerThreadA r : spt) {
            //System.out.println("2 Thread results size: " + r.results.size());
            if (r.results == null) {
            } else {
                for (int i = 0; i < r.results.size(); i++) {
                    results.add(r.results.get(i));
                }
            }

        }
        //append thread.getResult();
        //System.out.println("Size: " + results.size());
        for (Account A : results) {
            //System.out.println(M.getSong().getTitle() + " - " + M.getArtist().getName());
        }

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        for(Account A : results){
            if(A.getUsername().equals(username)){
                if(A.getPassword().equals(password)){
                    this.current_account = A;
                    return true; // Validated.
                }
            }
        }
        return false; // Didn't validate.
    }
    
    /**
     * Register new account.
     * @param username user input
     * @param password user input
     */
    public boolean registerAccount(String username, String password)
    {
        Random rand = new Random();
        
        // Check if account already exists
        if (this.accountAlreadyExists(username))
            return false;
        // Create new account object with empty ArrayList of playlists.
        Account A = new Account(username, password, Integer.toString(rand.nextInt(9999)), new ArrayList<Playlist>());
        // Add it to the ArrayList of Accounts.
        this.account_list.add(A);
        // Save to file
        this.save();
        return true;
    }
    
    /**
     * Save to file data is changed,
     * (add/del playlist, add/remove song, register account)
     */
    public void save(){
        // Create JSON to save to file using GSON.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Create JSON with GSON.
        String jsonLine = gson.toJson(account_list);
        try{
            // Create writer, write, close.
            FileWriter write = new FileWriter("accounts.json", false);
            write.write(jsonLine);
            write.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void setCurrentAccount(String s) {
        for(Account A : this.account_list) {
            if(A.getUsername().equals(s)) {
                this.current_account = A;
                System.out.println(A.getUsername());
            }
        }
    }
    
    public Account getCurrentAccount() {
        return this.current_account;
    }
    
    private boolean accountAlreadyExists(String username) {
        for(Account A : this.account_list) {
            if(A.getUsername().equals(username))
                return true;
        }
        return false;
    }
    
    public ArrayList<Account> getAccounts() {
        
        MusicServices ms = new MusicServices();
        //ms.getAllEntries(keyword, keywordType);
        return this.account_list;
    }
    
    public String getValidatedID(String username) {
        String what = "1";
        try {
            // File read object.
            Reader read = new FileReader("accounts.json");
            // GSON
            account_list = new Gson().fromJson(read, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (Account A : account_list) {
            if (A.getUsername().equals(username))
                return A.getID();
        }
        return null;
    }
    /*
    public ArrayList<Account> getAllEntriesAccounts(String keyword, String keywordType) throws Exception {
        //DFS dfs = new DFS(2000);
        Metafile metaFile = dfs.searchFile("accounts");
        int n = metaFile.getNumberOfPages();
        // Create n threads
        ArrayList<SearchPeerThreadA> spt = new ArrayList();
        ArrayList<Thread> threads = new ArrayList();

        for (int i = 0; i < n; i++) {
            Page p = metaFile.getPage(i);

            ChordMessageInterface peer = dfs.chord.locateSuccessor(p.guid);

            //ChordMessageInterface peer, Long guid, String keyword
            SearchPeerThreadA pt = new SearchPeerThreadA(peer, p.guid, keyword, keywordType);
            spt.add(pt);
            Thread T = new Thread(pt);
            threads.add(T);
            T.start();

        }

        ArrayList<Account> results = new ArrayList();

        for (Thread t : threads) {
            t.join();
        }

        for (SearchPeerThreadA r : spt) {
            //System.out.println("2 Thread results size: " + r.results.size());
            if (r.results == null) {
            } else {
                for (int i = 0; i < r.results.size(); i++) {
                    results.add(r.results.get(i));
                }
            }

        }
        //append thread.getResult();
        //System.out.println("Size: " + results.size());
        for (Account A : results) {
            //System.out.println(M.getSong().getTitle() + " - " + M.getArtist().getName());
        }

        int j = 0;
        return results;
    }
    */
    public void setDFS(DFS dfs) {
        this.dfs = dfs;
    }
    
    public static void main(String[] args) throws Exception {

        
        LoginServices ls = new LoginServices();
        
       System.out.println( ls.validateAccount("TaylorM", "asdf")
       );
    }
}