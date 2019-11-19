package Server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TaylorMeyer
 */
public class SearchDriver {
    
    private ArrayList<String> filepathList; 
    ArrayList<MusicMeta> results;
    private String target = "";
    
    String fp1 = "music1.json";
    String fp2 = "music2.json";
    String fp3 = "music3.json";

    String[] fpArray = {fp1,fp2,fp3};

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args){

        MusicServices mS = new MusicServices();
        String sResult = mS.getSong("dad");
        
        System.out.println(sResult);
    }
    
    
    public SearchDriver(String target){
        
        this.target = target;
        filepathList = new ArrayList();

        for(String s : fpArray){
            filepathList.add(s);
        }
        
        
        

    }
    
    public ArrayList<MusicMeta> execute() {

        ArrayList<Thread> threads = new ArrayList();
        ArrayList<SearchThread> searches = new ArrayList();
        for (int i = 0; i < filepathList.size(); i++) {
            SearchThread searchThread = new SearchThread(this.filepathList.get(i), this.target, true);
            searches.add(searchThread);
            Thread tempThread = new Thread(searchThread);
            threads.add(tempThread);
            tempThread.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
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
        
        return results;
    }
}
