package pkg327;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 *
 * @author Jonah
 */
class RemoteMethod implements RemoteRefInterface {

    private TypeToken<List<Catalog>> token = new TypeToken<List<Catalog>>() {
    };
    List<Catalog> catalogList;

    public RemoteMethod() {

        this.deserializeRemoteReferences();
    }

    private void deserializeRemoteReferences() {

        try (Reader read = new FileReader("catalog.json")) {
//        try (Reader read = new FileReader("catlogTEST.json")) {

            catalogList = new Gson().fromJson(read, token.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        RemoteMethod r0 = new RemoteMethod();
        String[] test = r0.getRemoteReference("DeleteSong");
    }

    @Override
    public String[] getRemoteReference(String remoteMethodName) {

        for (Catalog c : catalogList) {

            if (c.getRemoteMethod().equals(remoteMethodName)) {

                List<Param> crunchifyList = c.getParams();
                String paramString = "";

                for (int i = 0; i < crunchifyList.size(); i++) {

                    if (i < crunchifyList.size() - 1) {
                        paramString += crunchifyList.get(i);
                    } else { // If last element we don't want the separator
                        paramString += crunchifyList.get(i);
                    }

                }

                String[] returnArray = {c.getRemoteMethod() + " ", c.getObjectName(), paramString + " ", c.getCallSemantic(), c.getReturnType()};

                return returnArray;
            }
        }

        String[] p = {"tone"};
        return p;
    }
}
