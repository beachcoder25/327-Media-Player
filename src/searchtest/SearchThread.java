/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchtest;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Taylor Meyer
 */
public class SearchThread implements Runnable{
    
    // fp
    private String filepath = "~default~";
    
    // gson token
    private TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
    
    // the entire chunk
    private ArrayList<MusicMeta> meta;
    
    // the results of search
    private ArrayList<MusicMeta> result;
    
    // target search with default value
    private String target = "~notarget~";
    
    // if search target is song, if no -> artist
    private boolean isSong;
    // constructor
    public SearchThread(String s, String target, boolean isSong) {
        this.filepath = s;
        this.target = target;
        this.isSong = isSong;
    }
    
    @Override
    public void run() 
    {
        
        // loads in the meta from chunk
        try {
            // Read chunk
            Reader read = new FileReader(this.filepath);
            // Use GSON
            meta = new Gson().fromJson(read, this.token.getType());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // search
        this.search();
        
    } 
    
    // return result
    public ArrayList<MusicMeta> getResult() {
        return this.result;
    }
    
    // search
    private void search() {
        
        this.result = new ArrayList();
        
        // if song title contains target, add to result
        for (MusicMeta M : this.meta) {
            
            // song search
            if(this.isSong){
            if (M.getSong().getTitle().toLowerCase().contains(this.target.toLowerCase())){
                //System.out.println("adding: " + M.getSong().getTitle());
                this.result.add(M);
            }
            }
            
            // artist search
            else {
               if (M.getArtist().getName().toLowerCase().contains(this.target.toLowerCase())){
                //System.out.println("adding: " + M.getSong().getTitle());
                this.result.add(M);
            } 
            }
            
        }
        System.out.println("thread size: " + this.result.size());
        
    }
    
    // early testing
    public static void main(String[] args) 
    { 
        //SearchThread t = new SearchThread(); 
        //t.start(); 
        System.out.println("Main method executed by main thread"); 
    } 
    
    
}