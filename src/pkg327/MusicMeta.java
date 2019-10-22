
package pkg327;

/**
 *
 * @author nicka
 */
public class MusicMeta implements Comparable<MusicMeta>{
    private Artist artist;
    private Release release;
    private Song song;
    
    public MusicMeta() { 
        
    }  
    
    public Artist getArtist(){
        return this.artist;
    }
    
    public Release getRelease(){
        return this.release;
    }
    
    public Song getSong(){
        return this.song;
    }
    
    @Override
    public int compareTo(MusicMeta m) {
        return this.song.getTitle().compareTo(m.getSong().getTitle());
    }
}
