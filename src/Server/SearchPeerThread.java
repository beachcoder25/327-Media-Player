/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Phase3_Other.ChordMessageInterface;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonah
 */
public class SearchPeerThread implements Runnable{
    public ChordMessageInterface peer = null;
    public Long guid;
    public String keyword;
    public ArrayList<MusicMeta> results;
   
    public SearchPeerThread(ChordMessageInterface peer, Long guid, String keyword){
        this.peer = peer;
        this.guid = guid;
        this.keyword = keyword;
        this.results = results;
        
    }
    
     @Override
    public void run() 
    {
        
        
        if (peer != null)
        {
            
            try {
                results = peer.search(guid, keyword);
            } catch (IOException ex) {
                Logger.getLogger(SearchPeerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    } 
}
