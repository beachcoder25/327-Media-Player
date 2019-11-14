package Phase3_Metadata;

/**
 *
 * @author Taylor Meyer
 */
public class Page {

    private String guid;
    private long size;
    private String creationTS;
    private String readTS;
    private String writeTS;
    private String referenceCount;


    
    public Page() {
        this.guid = "a";
        this.size = 0;
        this.creationTS = "a";
        this.readTS = "a";
        this.writeTS = "a";
        this.referenceCount = "a";
    }

    public String getGuid() {
        return guid;
    }

    public long getSize() {
        return size;
    }

    public void setGuid(String guid) {
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

    /*
    [{"guid":"46312", "size": "1024", "creationTS":"1256933732","readTS":"1256953732", "writeTS":"1256953732", "referenceCount":"0
     */
    public String getReferenceCount() {
        return referenceCount;
    }

}
