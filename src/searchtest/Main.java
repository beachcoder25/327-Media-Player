/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchtest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TaylorMeyer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String fp1 = "music1.json";
        String fp2 = "music2.json";
        String fp3 = "music3.json";
        
        ArrayList<String> l = new ArrayList();
        l.add(fp1);
        l.add(fp2);
        l.add(fp3);
        
        String tar = "did";
        
        ArrayList<Thread> threads = new ArrayList();
        ArrayList<SearchThread> searches = new ArrayList();
        for (int i = 0; i<3; i++) {
            SearchThread s = new SearchThread(l.get(i), tar, true);
            searches.add(s);
            Thread temp = new Thread(s);
            threads.add(temp);
            temp.start();
        }
        
        for (Thread t : threads) {
            try{
            t.join();
            }
            catch(InterruptedException e) {
                    e.printStackTrace();
                    }
        }
        
        
        ArrayList<MusicMeta> results = new ArrayList();
        
        for (SearchThread s : searches) {
            if (s.getResult() != null) {
            for (int i = 0; i < s.getResult().size(); i++) {
                results.add(s.getResult().get(i));
            }
            }
        }
        //test
        System.out.println(results.size());
        for(MusicMeta m : results) {
            
            System.out.println(m.getSong().getTitle());
        }
        
    }
    
}
