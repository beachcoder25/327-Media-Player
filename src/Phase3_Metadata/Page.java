package Phase3_Metadata;

/**
 *
 * @author Taylor Meyer
 */
public class Page {

    public long guid;
    private long size;
    private String creationTS;
    private String readTS;
    private String writeTS;
    private String referenceCount;
    //private int fileIDCount; // Used to associate replications of a given page

    public Page() {
        this.guid = 0;
        this.size = 0;
        this.creationTS = "a";
        this.readTS = "a";
        this.writeTS = "a";
        this.referenceCount = "a";
        //this.fileIDCount = 0;
    }

    public long getGuid() {
        return guid;
    }

    public long getSize() {
        return size;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setCreationTS(String creationTS) {
        this.creationTS = creationTS;
    }

    public void setReadTS(String readTS) {
        this.readTS = readTS;
    }

    public void setWriteTS(String writeTS) {
        this.writeTS = writeTS;
    }

    public void setReferenceCount(String referenceCount) {
        this.referenceCount = referenceCount;
    }

    public String getCreationTS() {
        return creationTS;
    }

    public String getReadTS() {
        return readTS;
    }

    public String getWriteTS() {
        return writeTS;
    }

    public String getReferenceCount() {
        return referenceCount;
    }

//    public int getFileIDCount() {
//        return fileIDCount;
//    }
//
//    public void setFileIDCount(int fileIDCount) {
//        this.fileIDCount = fileIDCount;
//    }
}
