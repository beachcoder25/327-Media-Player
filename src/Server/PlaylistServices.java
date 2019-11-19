
package Server;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Stores references to current logged in user and the list of
 * that user's playlists.
 * @author Taylor Meyer
 */
public class PlaylistServices {
    
    // Current account
    //private Account current_account;
    // ArrayList of Playlists
    //ArrayList<Playlist> playlists;
    
    
    /**
     * Default constructor currently does nothing
     * @param A current account
     */
    public PlaylistServices() {
        // Do nothing
    }
    
    /**
     * Creates an empty playlist with its name passed as a parameter
     * @param s name of playlist to be created
     */
    public void createPlaylist(String playlist_name, String account_id) {
        
        System.out.println("inside cP");
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        A.getPlaylists().add(new Playlist(playlist_name));
        
        this.save(list);
    }
    
    /**
     * Delete a playlist based on the playlist's name.
     * @param s 
     */
    public void deletePlaylist(String playlist_name, String account_id) {
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        for (Playlist p : A.getPlaylists()) {
            if (p.getName().equals(playlist_name)) {
                A.getPlaylists().remove(p);
                break;
            }
        }
        this.save(list);
    }
    
    /**
     * Using given name s, adds song id i to that playlist.
     * @param s name of the playlist. Calls that Playlist objects
     * add function.
     * @param i id of the song
     */
    public void addSongToPlaylist(int id, String playlist_name, String account_id) {
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        for (Playlist p : A.getPlaylists()) {
            if (p.getName().toLowerCase().equals(playlist_name.toLowerCase())) {
                p.addSong(id);
                break;
            }
        }
        this.save(list);
    }
    
    /**
     * Removes a song from a playlist based on the playlist's name
     * and the specific song's id. Calls that Playlist objects remove
     * functon.
     * @param s the name of the playlist
     * @param i the id of the song to be removed
     */
    public void removeSongFromPlaylist(int id, String playlist_name, String account_id) {
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        for (Playlist p : A.getPlaylists()) {
            if (p.getName().toLowerCase().equals(playlist_name.toLowerCase())) {
                p.removeSong(id);
                break;
            }
        }
        this.save(list);
    }
    
    public String getAllPlaylists(String account_id) {
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        ArrayList<Playlist> playlists = A.getPlaylists();
        String json = new Gson().toJson(playlists);
        return json;
    }
    
    /**
     * Returns playlist based on the index in the list of
     * playlists. Programmer might need this.
     * @param i the index
     * @return Playlist
     */
    public Playlist getPlaylist(int i, String account_id) {
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        return A.getPlaylists().get(i);
    }
    
    /**
     * Returns playlist based on the playlist's name.
     * UI will need this.
     * @param s the name
     * @return Playlist
     */
    public String getPlaylist(String playlist_name, String account_id) {
        
        ArrayList<Account> list = this.getAccountList(account_id);
        Account A = this.getAccount(account_id, list);
        
        ArrayList<MusicMeta> meta = new ArrayList();
        Playlist P = null;
        
        for (Playlist p : A.getPlaylists()) {
            if (p.getName().equals(playlist_name)) {
                P = p;
                break;
            }
        }
        
        MusicServices ms = new MusicServices();
        
//        for(int id : P.getList()) {
//            for(MusicMeta M : ms.getMetaData()) {
//                if (M.getRelease().getId() == id) {
//                    meta.add(M);
//                    break;
//                }
//            }
//        }
        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(meta);
    }
    
    private ArrayList<Account> getAccountList(String id) {
        
        TypeToken<List<Account>> token = new TypeToken<List<Account>>() {};
        ArrayList<Account> list = new ArrayList();
        
        try {
            // File read object.
            Reader read = new FileReader("accounts.json");
            // GSON
            list = new Gson().fromJson(read, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    private Account getAccount(String id, ArrayList<Account> list) {
        
        for (Account A : list) {
            if (A.getID().equals(id))
                return A;
        }
        return null;
    }
    
    private void save(ArrayList<Account> list) {
        // Create JSON to save to file using GSON.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Create JSON with GSON.
        String jsonLine = gson.toJson(list);
        try{
            // Create writer, write, close.
            FileWriter write = new FileWriter("accounts.json", false);
            write.write(jsonLine);
            write.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}