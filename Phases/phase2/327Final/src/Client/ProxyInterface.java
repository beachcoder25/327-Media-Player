package Client;

import com.google.gson.JsonObject;

/**
 *
 * @author Jonah
 */

public interface ProxyInterface {

    
    
    /**
     * Allows for synchronous execution calls
     * @param remoteMethod Name of remoteMethod being called
     * @param param Parameters for said remoteMethod
     * @return 
     */
    
    public JsonObject synchExecution(String remoteMethod, String[] param);


    /**
     * Executes the  remote method remoteMethod and returns without waiting
     * for the reply. It does similar to synchExecution but does not 
     * return any value
     * @param remoteMethod Name of remoteMethod being called
     * @param param Parameters for said remoteMethod
     */
    public void asynchExecution(String remoteMethod, String[] param);
}


