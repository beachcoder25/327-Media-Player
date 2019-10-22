package pkg327;

/**
 * The Dispatcher implements DispatcherInterface.
 *
 * @author Oscar Morales-Ponce
 * @version 0.15
 * @since 02-11-2019
 */
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dispatcher implements DispatcherInterface {

    HashMap<String, Object> ListOfObjects;
    LoginServices loginServices = new LoginServices();
    PlaylistServices playlistServices = new PlaylistServices();
    MusicServices musicServices = new MusicServices();
    //PlaylistServices playlistServices = new PlaylistServices();

    public Dispatcher() {
        ListOfObjects = new HashMap<String, Object>();
    }

    /**
     * Un-Marshals request, calls method, gets return value
     * @param request A JSON request to be processed and called to the proper method
     * @return The return value of the function that was called in a string format
     */
    public String dispatch(String request) {

        System.out.println("Request in dispatch: " + request);
        JsonObject jsonReturn = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject jsonRequest = parser.parse(request).getAsJsonObject();

        String classType = jsonRequest.get("objectName").getAsString();

        if (classType.equals("LoginServices")) {
            this.registerObject(loginServices, "LoginServices");
        }

        if (classType.equals("PlaylistServices")) {
            this.registerObject(playlistServices, "PlaylistServices");
        }

        if (classType.equals("MusicServices")) {
            this.registerObject(musicServices, "MusicServices");
        }

        
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
    
    /*
    public static void main(String[] args){
        Dispatcher d = new Dispatcher();
        String test = "{\"remoteMethod\":\"getValidatedID\",\"objectName\":\"LoginServices\",\"param\":{\"username\":\"TaylorM\"},\"callSemantic\":\"Maybe\",\"returnType\":\"String\"}";
        d.dispatch(test);
    }
    */
}
