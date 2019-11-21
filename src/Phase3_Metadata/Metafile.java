package Phase3_Metadata;

import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Taylor Meyer
 */
public class Metafile {
    
    private String name;
    private long size;
    private String creationTS;
    private String readTS;
    private String writeTS;
    private String referenceCount;
    private int numberOfPages;
    private int maxPageSize;
    private List<Page> pages;


    
    public Metafile() {
        this.name = "";
        this.size = 0;
        this.creationTS = "";
        this.readTS = "";
        this.writeTS = "";
        this.referenceCount = "";
        this.numberOfPages = 0;
        this.maxPageSize = 0;
        this.pages = new ArrayList();
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCreationTS() {
        return creationTS;
    }

    public void setCreationTS(String creationTS) {
        this.creationTS = creationTS;
    }

    public String getReadTS() {
        return readTS;
    }

    public void setReadTS(String readTS) {
        this.readTS = readTS;
    }

    public String getWriteTS() {
        return writeTS;
    }

    public void setWriteTS(String writeTS) {
        this.writeTS = writeTS;
    }

    public String getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(String referenceCount) {
        this.referenceCount = referenceCount;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public List<Page> getPages() {
        return pages;
    }
    
    public Page getPage(int i) {
        return this.pages.get(i);
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
    
    public void addPagee(Page page) {
        //TODO: figure out what info needs to be attached to page when they are added
        this.pages.add(page);
        this.setNumberOfPages(this.numPages());
    }
    
    public int numPages() {
        return pages.size();
    }
    
    public int getPageSizeSum(){
     
        int total = 0;
        for(Page page : this.pages){
            total += page.getSize();
        }
        
        return total;
    }


}