package Phase3_Metadata;

import Phase3_Other.RemoteInputFileStream;
import Server.Account;
import Server.Playlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jonah
 */
public class Metadata implements IMetaData, Serializable {

    private List<Metafile> metafile;
//    private TypeToken<List<Metafile>> token = new TypeToken<List<Metafile>>() {
//    };
    public String pageString = "";
    public String metaDataJSON = "metadata.json";
    public int maxPageSize = 4096;

    public Metadata(List<Metafile> metafile) {
        this.metafile = metafile;
    }

    public Metadata() {
        this.metafile = new ArrayList<Metafile>();
        //this.deserializeMetadata();
    }

    public String serializeMetadata() {

        Gson gson = new Gson();
        return gson.toJson(this, Metadata.class);
    }

    public String getMetafilePages(String name) {

        for (Metafile m : metafile) {
            System.out.println(m.getName());
            if (m.getName().equals(name)) {
                List<Page> pageList = m.getPages();
                pageString = "";

                for (int i = 0; i < pageList.size(); i++) {

                    pageString += "Page" + (i + 1) + " GUID: " + pageList.get(i).getGuid() + "\n";
                }
            }
        }
        return pageString;
    }

    @Override
    public void create(String fileName, long size) {

        // create file
        Metafile mFile = new Metafile();

        // Create TS
        LocalTime now = LocalTime.now(ZoneId.systemDefault()); // LocalTime = 14:42:43.062
        String ts = String.valueOf(now.toSecondOfDay()); // Int = 52963

        // set file attributes
        mFile.setName(fileName);
        mFile.setSize(size);
        mFile.setCreationTS(ts);
        mFile.setReadTS(ts);
        mFile.setWriteTS(ts);
        mFile.setMaxPageSize(maxPageSize);

        // add file to meta data
        this.addFile(mFile);

        List<Metafile> list = this.getMetafile();

        this.save(list);

    }

    public void createPageCopies(String fileName) {

    }

    @Override
    public String lists() {

        //String listOfFiles = mData.getMetafile().toString();
        String listOfFiles = "";

        for (int i = 0; i < this.getSize(); i++) {
            listOfFiles += this.getMetafile().get(i).getName() + "\n";
        }

        return listOfFiles;
    }

    @Override
    public void delete(String fileName) {

        // index for deletion
        int index = -1;

        //find file
        for (int i = 0; i < this.getSize(); i++) {
            if (fileName.equals(this.getMetafile().get(i).getName())) {
                index = i;
            }
        }

        // remove file from list of metadata
        if (index >= 0) {
            // get file
            Metafile mFile = this.getFile(index);
            this.removeFile(index);
            this.save(metafile);
        } else {
            // file not found
            System.out.println("The file could not be found.");
        }
    }

    @Override
   public int append(String fileName, RemoteInputFileStream data) {
        Page newPage = new Page();

        long guid;
        // find file to append page to
        for (int i = 0; i < this.getSize(); i++) {
            if (fileName.equals(this.getMetafile().get(i).getName())) {

                Metafile mF = this.getMetafile().get(i);
                guid = md5(fileName + mF.numPages() + 1);
                String guidString = String.valueOf(guid);
                guidString = guidString.substring(0, 8);

                //TODO: create page to be appended to file
                newPage.setGuid(parseLong(guidString));
                newPage.setCreationTS(mF.getCreationTS());
                newPage.setReadTS(mF.getCreationTS());
                newPage.setWriteTS(mF.getCreationTS());

                long temp = mF.getSize() - (long) data.fileSize;

                if (temp < 0) {
                    newPage.setSize(0);

                }

                newPage.setSize(((long) data.fileSize) + 1);
                newPage.setReferenceCount("0");

                mF.addPagee(newPage);
                mF.setNumberOfPages(mF.getNumberOfPages());
                this.save(metafile);

                return Integer.parseInt(guidString);
            }
        }
        return 0;
    }
    
    public int appendCopies(String fileName, RemoteInputFileStream data) {
        Page newPage = new Page();

        long guid;
        // find file to append page to
        for (int i = 0; i < this.getSize(); i++) {
            if (fileName.equals(this.getMetafile().get(i).getName())) {

                Metafile mF = this.getMetafile().get(i);
                String guidString = "-1";

                // REPLICATION
                for (int j = 0; j < 3; j++) {

                    guid = md5(fileName + mF.numPages() + (i + 1));
                    guidString = String.valueOf(guid);
                    guidString = guidString.substring(0, 8);
                    System.out.println("guidString: " + guidString);

                    //TODO: create page to be appended to file
                    newPage.setGuid(parseLong(guidString));
                    newPage.setCreationTS(mF.getCreationTS());
                    newPage.setReadTS(mF.getCreationTS());
                    newPage.setWriteTS(mF.getCreationTS());

                    long temp = mF.getSize() - (long) data.fileSize;

                    if (temp < 0) {
                        newPage.setSize(0);
                    }

                    newPage.setSize(((long) data.fileSize) + 1);
                    newPage.setReferenceCount("0");

                    mF.addPagee(newPage);
                    mF.setNumberOfPages(mF.getNumberOfPages());
                    this.save(metafile);
                }

                return Integer.parseInt(guidString);
            }
        }
        return 0;
    }

    @Override
    public void move(String oldName, String newName) {

        for (int i = 0; i < this.getSize(); i++) {

            if (this.getMetafile().get(i).getName().equals(oldName)) {

                Metafile mF = this.getMetafile().get(i);
                mF.setName(newName);
                this.save(metafile);
            }
        }
    }

    private void save(List<Metafile> list) {

        // Create JSON to save to file using GSON.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Create JSON with GSON.
        String jsonLine = gson.toJson(list);
        try {
            // Create writer, write, close.
            FileWriter write = new FileWriter(metaDataJSON, false);
            write.write(jsonLine);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long md5(String objectName) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(objectName.getBytes());
            BigInteger bigInt = new BigInteger(1, m.digest());
            return Math.abs(bigInt.longValue());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public void removeFile(int index) {
        metafile.remove(index);
    }

    public List<Metafile> getMetafile() {
        return metafile;
    }

    public void setMetafile(List<Metafile> metafile) {
        this.metafile = metafile;
    }

    public void addFile(Metafile file) {
        metafile.add(file);
    }

    public int getSize() {
        return metafile.size();
    }

    public Metafile getFile(int index) {
        return metafile.get(index);
    }

}
