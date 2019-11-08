package Phase3_Metadata;

/**
 *
 * @author Taylor Meyer
 */
public class Page {

    private String guid;
    private String size;
    private String creationTS;
    private String readTS;
    private String writeTS;
    private String referenceCount;

    public Page(String guid, String size, String creationTS, String readTS, String writeTS, String referenceCount) {
        this.guid = guid;
        this.size = size;
        this.creationTS = creationTS;
        this.readTS = readTS;
        this.writeTS = writeTS;
        this.referenceCount = referenceCount;
    }
    
    public Page() {
        this.guid = "a";
        this.size = "a";
        this.creationTS = "a";
        this.readTS = "a";
        this.writeTS = "a";
        this.referenceCount = "a";
    }

    public String getGuid() {
        return guid;
    }

    public String getSize() {
        return size;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setSize(String size) {
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
