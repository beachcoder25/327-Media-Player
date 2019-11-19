
package Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author nicka
 */
public class MusicServices {
    
    // Token for GSON to load in a list from JSON
    private TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
    // Unsorted list
    ArrayList<MusicMeta> meta_data_unsorted;
    // Sorted list
    ArrayList<MusicMeta> meta_data_sorted;
    // SearchDriver
    private SearchDriver searchDriver;
    // Metadata result from SearchDriver
    ArrayList<MusicMeta> meta_data_result;

    /**
     * Default constructor. Loads in the meta data for all the songs
     * from the JSON file using GSON.
     */
    public MusicServices() {
        // De-serialize the data
        //deserializeData();
        // Sort the one list that needs to be sorted
        //Collections.sort(this.meta_data_sorted);
    }
    
    /**
     * This function deserializes the data from music.json
     * <p>
     * To deserialize, we use Gson to convert the .json file to lists of objects
     * that we are then able to use to process commands from the client.
     * <\p>
     */
    private void deserializeData() {
        
        try {
            // Reader for first list.
            Reader read = new FileReader("music.json");
            // Reader for second list.
            Reader read2 = new FileReader("music.json");
            // Use GSON
            meta_data_unsorted = new Gson().fromJson(read, token.getType());
            meta_data_sorted = new Gson().fromJson(read2, token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Get the list of MetaData
    public List<MusicMeta> getMetaData(){
        return this.meta_data_unsorted;
    }
    
    /**
     * Uses Random to pick out a random song from the list of music
     * @return MusicMeta random song
     */
    public MusicMeta getRandomSong(){
        return this.meta_data_unsorted.get(
                new Random().nextInt(this.meta_data_unsorted.size()));
    }
    
/**
     * User function that will return a MusicMeta object by search (using either
     * song or artist as search conditions)
     *
     * @param s name of the song
     * @param isSong flag for if the user is searching for a song
     * @return MusicMeta result of the search
     */
    public String getSong(String searchString) {
        System.out.println("Hey, we made it here at least");

        ArrayList<MusicMeta> metaList = new ArrayList();
        
        
        searchDriver = new SearchDriver(searchString);
        meta_data_result = searchDriver.execute();
        
        int counter = 0;

        for (MusicMeta M : meta_data_result) {
            if (M.getSong().getTitle().toLowerCase().contains(searchString.toLowerCase())) {
                metaList.add(M);
                System.out.println("Adding number: " + counter +"\n"
                            + "Name: " + M.getSong().getTitle());
                counter++;
                if(counter == 15) break;
            }
        }

        Gson gson = new Gson();
        //System.out.println(gson.toJson(metaList));
        return gson.toJson(metaList);
    }
    
    // search for artist
    public String getArtist(String s) {
        System.out.println("Hey, we made it here at least");

        ArrayList<MusicMeta> metaList = new ArrayList();
        
        int counter = 0;

        for (MusicMeta M : meta_data_unsorted) {
            if (M.getArtist().name.toLowerCase().contains(s.toLowerCase())) {
                metaList.add(M);
                System.out.println("Adding number: " + counter +"\n"
                            + "Name: " + M.getSong().getTitle());
                counter++;
                if(counter == 15) break;
            }
        }

        Gson gson = new Gson();
        //System.out.println(gson.toJson(metaList));
        return gson.toJson(metaList);
    }
    
    public String getNormalPage(int index) {
        
        ArrayList<MusicMeta> page = new ArrayList();
        
        int i = index * 15;
        int stop = i + 15;
        
        // tried to do this in a for loop but it wasnt really working out
        while(i != (stop-1)) {
            page.add(this.meta_data_unsorted.get(i));
            i++;
        }
        
        
        Gson gson = new Gson();
        return gson.toJson(page);
    }
    
    public String getAlphaPage(int index) {
        
        ArrayList<MusicMeta> page = new ArrayList();
        
        int i = index * 15;
        int stop = i + 15;
        
        // tried to do this in a for loop but it wasnt really working out
        while(i != (stop-1)) {
            page.add(this.meta_data_sorted.get(i));
            i++;
        }
        
        
        Gson gson = new Gson();
        return gson.toJson(page);
    }
    
    public String getRandomPage() {
        Random rand = new Random();
        
        ArrayList<MusicMeta> meta = new ArrayList();
        
        for(int i = 0; i < 15; i++) {
            meta.add(this.meta_data_unsorted.get(
                    rand.nextInt(this.meta_data_unsorted.size())));
        }
        
        Gson gson = new Gson();
        return gson.toJson(meta);
    }
    
    public String getPlaylistMeta(String json) {
        
        Gson gson = new Gson();
        
        //TypeToken<List<Playlist>> token = new TypeToken<List<Playlist>>(){};
        Playlist p = new Gson().fromJson(json, Playlist.class);
        
        
        ArrayList<MusicMeta> meta_list = new ArrayList();
        for (int id : p.getList()) {
            for (MusicMeta M : this.meta_data_unsorted) {
                if (M.getRelease().getId() == id) {
                    System.out.println("Adding song: " + M.getRelease().getId());
                    meta_list.add(M);
                    break;
                }
            }
        }
        System.out.println("Size of meta: " + meta_list.size());
        return gson.toJson(meta_list);
    }
}