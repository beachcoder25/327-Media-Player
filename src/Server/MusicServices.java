package Server;

import Phase3_Metadata.Metafile;
import Phase3_Metadata.Page;
import Phase3_Other.ChordMessageInterface;
import Phase3_Other.DFS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicka
 */
public class MusicServices {
DFS dfs;
    // Token for GSON to load in a list from JSON
    private TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {
    };

    public DFS getDfs() {
        return dfs;
    }
    // Unsorted list
    ArrayList<MusicMeta> meta_data_unsorted;
    // Sorted list
    ArrayList<MusicMeta> meta_data_sorted;
    
    //ArrayList<Accout> accountresults;

    

    public static void main(String[] args) throws Exception {

        //DFS dfs = new DFS(2000);
        MusicServices ms = new MusicServices();
        //ms.setDFS(dfs);
        System.out.println(ms.getArtist("cas"));

//        ms.getAllEntries("did", "SONG");
//        System.out.println("\n\n\n");
//        ms.getAllEntries("cas", "ARTIST");
    }

    public ArrayList<MusicMeta> getAllEntries(String keyword, String keywordType) throws Exception {
        //DFS dfs = new DFS(2000);
        Metafile metaFile = dfs.searchFile("musicJSON");
        int numPages = metaFile.getNumberOfPages();
        // Create n threads
        ArrayList<SearchPeerThread> searchPeerThreadList = new ArrayList();
        ArrayList<Thread> threads = new ArrayList();

        for (int i = 0; i < numPages; i++) {
            Page page = metaFile.getPage(i);

            ChordMessageInterface peer = dfs.chord.locateSuccessor(page.guid);

            //ChordMessageInterface peer, Long guid, String keyword
            SearchPeerThread peerThread = new SearchPeerThread(peer, page.guid, keyword, keywordType);
            searchPeerThreadList.add(peerThread);
            Thread thread = new Thread(peerThread);
            threads.add(thread);
            thread.start();

        }

        ArrayList<MusicMeta> results = new ArrayList();

        for (Thread thread : threads) {
            thread.join();
        }

        for (SearchPeerThread peerThread : searchPeerThreadList) {
            
            if (peerThread.results == null) {
            } else {
                for (int i = 0; i < peerThread.results.size(); i++) {
                    results.add(peerThread.results.get(i));
                }
            }

        }
        
        for (MusicMeta M : results) {
            //System.out.println(M.getSong().getTitle() + " - " + M.getArtist().getName());
        }

    
        return results;
    }
    
     public ArrayList<Account> getAllEntriesAccounts(String username, String password) throws Exception {
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
            SearchPeerThreadA pt = new SearchPeerThreadA(peer, p.guid, username, password);
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

    /**
     * Default constructor. Loads in the meta data for all the songs from the
     * JSON file using GSON.
     */
    public MusicServices() {
     
        
    }

    public MusicServices(String s) {
        
    }

    public void setDFS(DFS dfs) {
        this.dfs = dfs;
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
    public List<MusicMeta> getMetaData() {
        return this.meta_data_unsorted;
    }

    /**
     * Uses Random to pick out a random song from the list of music
     *
     * @return MusicMeta random song
     */
    public MusicMeta getRandomSong() {
        //return this.meta_data_unsorted.get(
          //      new Random().nextInt(this.meta_data_unsorted.size()));
        ArrayList<MusicMeta> metaList = new ArrayList();
        
        try {
            metaList = this.getAllEntries("", "SONG");
        } catch (Exception ex) {
            Logger.getLogger(MusicServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return metaList.get(new Random().nextInt(metaList.size()));
    }

    /**
     * User function that will return a MusicMeta object by search (using either
     * song or artist as search conditions)
     *
     * @param s name of the song
     * @param isSong flag for if the user is searching for a song
     * @return MusicMeta result of the search
     */
    public String getSong(String s) {

        System.out.println("Search Term: " + s);

        ArrayList<MusicMeta> metaListNew = new ArrayList();

        ArrayList<MusicMeta> metaList = new ArrayList();

        try {
            metaList = this.getAllEntries(s, "SONG");
            //metaList = metaList.subList(0, 15);
        } catch (Exception ex) {
            Logger.getLogger(MusicServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("metaList size: " + metaList.size() + " Search Term: " + s);
        int counter = 0;
        for (MusicMeta M : metaList) {
            if (M.getSong().getTitle().toLowerCase().contains(s.toLowerCase())) {
                metaListNew.add(M);
//                    System.out.println("Adding number: " + counter +"\n"
//                            + "Name: " + M.getSong().getTitle());
                counter++;
                if (counter == 15) {
                    break;
                }
            }
        }

        Gson gson = new Gson();
        //System.out.println(gson.toJson(metaList));
        System.out.println("metaList size: " + metaListNew.size());
        return gson.toJson(metaListNew);

    }


    public String getArtist(String s) {
        System.out.println("Search Term: " + s);

        ArrayList<MusicMeta> metaListNew = new ArrayList();

        ArrayList<MusicMeta> metaList = new ArrayList();

        try {
            metaList = this.getAllEntries(s, "ARTIST");
            //metaList = metaList.subList(0, 15);
        } catch (Exception ex) {
            Logger.getLogger(MusicServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("metaList size: " + metaList.size() + " Search Term: " + s);
        int counter = 0;
        for (MusicMeta M : metaList) {
            if (M.getArtist().name.toLowerCase().contains(s.toLowerCase())) {
                metaListNew.add(M);
                System.out.println("Adding number: " + counter +"\n"
                            + "Name: " + M.getSong().getTitle());
                counter++;
                if(counter == 15) break;
            }
            
        }

        Gson gson = new Gson();
        //System.out.println(gson.toJson(metaList));
        System.out.println("metaList size: " + metaListNew.size());
        return gson.toJson(metaListNew);
    }

    public String getNormalPage(int index) {
        
        ArrayList<MusicMeta> metaList = new ArrayList();

        ArrayList<MusicMeta> page = new ArrayList();
        
        try {
            metaList = this.getAllEntries("", "SONG");
        } catch (Exception ex) {
            Logger.getLogger(MusicServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        int i = index * 15;
        int stop = i + 15;

        // tried to do this in a for loop but it wasnt really working out
        while (i != (stop - 1)) {
            page.add(metaList.get(i));
            i++;
        }
        

        Gson gson = new Gson();
        return gson.toJson(page);
    }

    public String getAlphaPage(int index) {

        ArrayList<MusicMeta> metaList = new ArrayList();

        ArrayList<MusicMeta> page = new ArrayList();
        
        try {
            metaList = this.getAllEntries("", "SONG");
        } catch (Exception ex) {
            Logger.getLogger(MusicServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collections.sort(metaList);

        int i = index * 15;
        int stop = i + 15;

        // tried to do this in a for loop but it wasnt really working out
        while (i != (stop - 1)) {
            page.add(metaList.get(i));
            i++;
        }
        
        

        Gson gson = new Gson();
        return gson.toJson(page);
    }

    public String getRandomPage() {
        /*
        Random rand = new Random();

        ArrayList<MusicMeta> meta = new ArrayList();

        for (int i = 0; i < 15; i++) {
            meta.add(this.meta_data_unsorted.get(
                    rand.nextInt(this.meta_data_unsorted.size())));
        }

        Gson gson = new Gson();
        return gson.toJson(meta);
*/
        ArrayList<MusicMeta> metaList = new ArrayList();
        for (int i = 0; i < 15; i++) {
            metaList.add(this.getRandomSong());
        }
        Gson gson = new Gson();
        return gson.toJson(metaList);
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
