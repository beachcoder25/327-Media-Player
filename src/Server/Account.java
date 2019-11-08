
package Server;

import java.util.ArrayList;

/**
 * Stores the username, password, and their ArrayList of playlists.
 * @author Taylor Meyer
 */
public class Account {
    
    // username
    private String username;
    // password
    private String password;
    // ID
    private String id;
    // the ArrayList of Playlist objects.
    private ArrayList<Playlist> playlists;
    
    /**
     * Constructor.
     * @param username
     * @param password
     * @param playlists 
     */
    public Account(String username, String password, String id, ArrayList<Playlist> playlists)
    {
        this.username = username;
        this.password = password;
        this.id = id;
        this.playlists = playlists;
    }
    
    /**
     * Returns the username.
     * @return 
     */
    public String getUsername(){
        return this.username;
    }
    
    /**
     * Returns the password.
     * @return 
     */
    public String getPassword(){
        return this.password;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public String getID() {
        return this.id;
    }
    
    /**
     * Returns the ArrayList of Playlist objects.
     * @return 
     */
    public ArrayList<Playlist> getPlaylists() { 
        return this.playlists;
    }
}