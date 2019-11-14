/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Taylor Meyer
 */
public class Search {
    
    private String songname; // target
    
    // constructor
    public Search(String s) {
        this.songname = s;
        
        // do search
        this.search(this.songname);
    }
    
    
    private void search(String songname) {
        
        // set filepaths, hardcoded for now
        // each music.json is roughly 1/3 of the master,
        //      copied pasted by hand
        String fp1 = "folder1\\music.json";
        String fp2 = "folder2\\music.json";
        String fp3 = "folder3\\music.json";
        
        // Create 3 objects of SearchThread class, pass filepath, and target
        SearchThread t1 = new SearchThread(fp1, this.songname);
        SearchThread t2 = new SearchThread(fp2, this.songname);
        SearchThread t3 = new SearchThread(fp3, this.songname);
        
        // SearchThread class implements Runnable (not extends Thread)
        // So we create three new threads, and pass them the Runnable class
        //      and call Thread.start() which will call SearchThread overrided
        //      run function, which will all go concurrently
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        
        
        // need to change to join later instead of sleep
        // sleep 5 seconds to wait for threads
        try{
            
            TimeUnit.SECONDS.sleep(5);
        
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // compile results
        ArrayList<String> song_names = new ArrayList();
        
        // each thread might not return anything because they are smaller
        // portions of the master, so we need to check for null first
        if(t1.getResult() != null){
        for(MusicMeta M : t1.getResult()) {
            song_names.add(M.getSong().getTitle());
        }
        }
        
        if(t2.getResult() != null){
        for(MusicMeta M : t2.getResult()) {
            song_names.add(M.getSong().getTitle());
        }
        }
        
        if(t3.getResult() != null){
        for(MusicMeta M : t3.getResult()) {
            song_names.add(M.getSong().getTitle());
        }
        }
        
        // print results
        System.out.println("RESULTS\n-------------");
        for(String s : song_names) {
            System.out.println(s);
        }
        
        System.out.println("\n------------------\nNumber of results: " + song_names.size());
        
    }
}
