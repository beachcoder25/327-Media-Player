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
import java.util.List;

/**
 *
 * @author Jonah
 */
public class SearchPeerThread implements Runnable{
    public ChordMessageInterface peer = null;
    public Long guid;
    public String keyword;
    public List<String> results;
    
    
     @Override
    public void run() 
    {
        
        
        if (peer != null)
        {
            results = peer.search(guid, keyword);
            
            
        }
        
    } 
}
