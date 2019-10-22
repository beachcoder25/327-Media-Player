
package pkg327;

/**
 * The Proxy implements ProxyInterface class. The class is incomplete
 *
 * @author Oscar Morales-Ponce
 * @version 0.15
 * @since 2019-01-24
 */
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static java.lang.Integer.parseInt;

public class Proxy implements ProxyInterface {

    
    CommClient commClient = new CommClient();

    public Proxy() {
        //this.dispacher = dispacher;
    }

    /**
     * Allows for synchronous execution calls
     * @param remoteMethod Name of remoteMethod being called
     * @param param Parameters for said remoteMethod
     * @return 
     */
    public JsonObject synchExecution(String remoteMethod, String[] param) {

        JsonObject jsonRequest = new JsonObject();
        JsonObject jsonParam = new JsonObject();

        jsonRequest.addProperty("remoteMethod", remoteMethod);

        
        RemoteMethod remoteMethodClass = new RemoteMethod();

        String[] remoteArray = remoteMethodClass.getRemoteReference(remoteMethod);

        jsonRequest.addProperty("objectName", remoteArray[1]); // Check if this is right...

        String[] temp = remoteArray[2].split(" "); // Splits params into individual objects

        for (int i = 0; i < temp.length; i++) {

            String[] parameters = temp[i].split(" ");

            for (String s : parameters) {

                String[] paramSplit = temp[i].split(":");

                if (paramSplit[1].equals("Integer")) {
                    jsonParam.addProperty(paramSplit[0], parseInt(param[i]));
                } else if (paramSplit[1].equals("String")) {
                    jsonParam.addProperty(paramSplit[0], param[i]);
                }
            }
        }
        jsonRequest.add("param", jsonParam);
        jsonRequest.addProperty("callSemantic", remoteArray[3]); // We know that remoteArray[3] and [4] contain the next properties

        jsonRequest.addProperty("returnType", remoteArray[4]);
        JsonParser parser = new JsonParser();

        System.out.println("JsonRequest sent to dispatcher: " + jsonRequest.toString() + "\n\n");

        //String s = dispacher.dispatch(jsonRequest.toString());
        
        String s = commClient.sendToServer(jsonRequest.toString());
        System.out.println("after");

        s = s.trim(); // Trim whitespace
        
        JsonObject jsonResponse = parser.parse(s).getAsJsonObject();
        System.out.println("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }

    
    /**
     * Executes the  remote method remoteMethod and returns without waiting
     * for the reply. It does similar to synchExecution but does not 
     * return any value
     * @param remoteMethod Name of remoteMethod being called
     * @param param Parameters for said remoteMethod
     */
    public void asynchExecution(String remoteMethod, String[] param) {
        
                JsonObject jsonRequest = new JsonObject();
        JsonObject jsonParam = new JsonObject();

        jsonRequest.addProperty("remoteMethod", remoteMethod);

        
        RemoteMethod remoteMethodClass = new RemoteMethod();

        String[] remoteArray = remoteMethodClass.getRemoteReference(remoteMethod);

        jsonRequest.addProperty("objectName", remoteArray[1]); // Check if this is right...

        String[] temp = remoteArray[2].split(" "); // Splits params into individual objects

        for (int i = 0; i < temp.length; i++) {

            String[] parameters = temp[i].split(" ");

            for (String s : parameters) {

                String[] paramSplit = temp[i].split(":");

                if (paramSplit[1].equals("Integer")) {
                    jsonParam.addProperty(paramSplit[0], parseInt(param[i]));
                } else if (paramSplit[1].equals("String")) {
                    jsonParam.addProperty(paramSplit[0], param[i]);
                }
            }
        }
        jsonRequest.add("param", jsonParam);
        jsonRequest.addProperty("callSemantic", remoteArray[3]); // We know that remoteArray[3] and [4] contain the next properties

        jsonRequest.addProperty("returnType", remoteArray[4]);
        JsonParser parser = new JsonParser();

        System.out.println("JsonRequest sent to dispatcher: " + jsonRequest.toString() + "\n\n");

        
        String s = commClient.sendToServer(jsonRequest.toString());
        System.out.println("after");

        s = s.trim(); // Trim whitespace
        
        JsonObject jsonResponse = parser.parse(s).getAsJsonObject();
        System.out.println("jsonResponse: " + jsonResponse);
        
    }


}
