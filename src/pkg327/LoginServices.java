
package pkg327;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public boolean validateAccount(String username, String password){
        
        for(Account A : account_list){
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
}