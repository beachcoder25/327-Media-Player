package Server;

import Phase3_Other.DFS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jonah
 */

public class Dispatcher implements DispatcherInterface {

    private HashMap<String, Object> ListOfObjects;
    private LoginServices loginServices = new LoginServices();
    private PlaylistServices playlistServices = new PlaylistServices();
    private MusicServices musicServices = new MusicServices();
DFS dfs;
    
    public Dispatcher() {
        
        
        try {
            dfs = new DFS(2000);
            //DFS dfs = new DFS(2000);
            // De-serialize the data
//        deserializeData();
// Sort the one list that needs to be sorted
//        Collections.sort(this.meta_data_sorted);
        } catch (Exception ex) {
            Logger.getLogger(MusicServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ListOfObjects = new HashMap<String, Object>();
        musicServices.setDFS(dfs);
        loginServices.setDFS(dfs);
    }

    /**
     * Un-Marshals request, calls method, gets return value
     *
     * @param request A JSON request to be processed and called to the proper
     * method
     * @return The return value of the function that was called in a string
     * format
     */
    public String dispatch(String request) {

        System.out.println("Request in dispatch: " + request);
        JsonObject jsonReturn = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject jsonRequest = parser.parse(request).getAsJsonObject();

        String classType = jsonRequest.get("objectName").getAsString();

        // If we add a service we do not need to hardcode it, hashmap of every class
        if (classType.equals("LoginServices")) {
            this.registerObject(loginServices, "LoginServices");
        }

        if (classType.equals("PlaylistServices")) {
            this.registerObject(playlistServices, "PlaylistServices");
        }

        if (classType.equals("MusicServices")) {
            this.registerObject(musicServices, "MusicServices");
        }

        if (jsonRequest.get("callSemantic").getAsString().equals("Maybe") || jsonRequest.get("callSemantic").getAsString().equals("AtMostOnce")) {

            System.out.println("\n\n\nCall Semantic in Dispatcher: " + jsonRequest.get("callSemantic").getAsString() + "\n\n\n");

            try {
                // Obtains the object pointing to SongServices
                Object object = ListOfObjects.get(jsonRequest.get("objectName").getAsString());
                System.out.println(Arrays.asList(ListOfObjects)); // method 1
                System.out.println(object);
                System.out.println("IN DISPATCHER");
                System.out.println(object.getClass());
                System.out.println("IN DISPATCHER pt II");
                Method[] methods = object.getClass().getMethods();
                Method method = null;

                String regex = "\\Q" + jsonRequest.get("remoteMethod").getAsString() + "\\E";

                final Pattern pattern = Pattern.compile(regex);

                for (int i = 0; i < methods.length; i++) {

                    final Matcher matcher = pattern.matcher(methods[i].getName());

                    if (matcher.find()) {
                        method = methods[i];
                    }
                }

                if (method == null) {
                    jsonReturn.addProperty("error", "Method does not exist");
                    return jsonReturn.toString();
                }
                // Prepare the  parameters 
                Class[] types = method.getParameterTypes();

                Object[] parameter = new Object[types.length];
                String[] strParam = new String[types.length];
                JsonObject jsonParam = jsonRequest.get("param").getAsJsonObject();

                int j = 0;
                for (Map.Entry<String, JsonElement> entry : jsonParam.entrySet()) {
                    strParam[j++] = entry.getValue().getAsString();
                }

                for (int i = 0; i < types.length; i++) {

                    switch (types[i].getCanonicalName()) {
                        case "java.lang.Long":
                            parameter[i] = Long.parseLong(strParam[i]);
                            break;
                        case "int":
                            parameter[i] = Integer.parseInt(strParam[i]);
                            break;
                        case "java.lang.String":
                            parameter[i] = new String(strParam[i]);
                            break;
                    }
                }

                Class returnType = method.getReturnType();
                System.out.println("\n\nReturn type: " + returnType + "\n\n");

                String ret = "";

                switch (returnType.getCanonicalName()) {

                    case "java.lang.Long":
                        ret = method.invoke(object, parameter).toString();
                        break;
                    case "int":
                        ret = method.invoke(object, parameter).toString();
                        break;
                    case "java.lang.String":
                        ret = (String) method.invoke(object, parameter);
                        break;
                    case "boolean":

                        ret = method.invoke(object, parameter).toString();
                        System.out.println("Being returned: !!! " + ret);

                        break;

                    case "void":

                        method.invoke(object, parameter);

                        ret = "void";
                        System.out.println("Being returned: !!! " + ret);

                        break;
                }
                jsonReturn.addProperty("ret", ret);

            } catch (InvocationTargetException | IllegalAccessException e) {
                //    System.out.println(e);
                jsonReturn.addProperty("error", "Error on " + jsonRequest.get("objectName").getAsString() + "." + jsonRequest.get("remoteMethod").getAsString());
            }
        }

        return jsonReturn.toString();
    }

    /* 
    * registerObject: It register the objects that handle the request
    * @param remoteMethod: It is the name of the method that 
    *  objectName implements. 
    * @objectName: It is the main class that contaions the remote methods
    * each object can contain several remote methods
     */
    public void registerObject(Object remoteMethod, String objectName) {
        ListOfObjects.put(objectName, remoteMethod);
    }

}
