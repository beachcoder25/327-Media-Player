package pkg327;

/**
 *
 * @author Jonah
 */
public interface RemoteRefInterface {
    

    public String[] getRemoteReference(String methodName);
    
}


    /*
    * Executes the  remote method "remoteMethod". The method blocks until
    * it receives the reply of the message. 
    * 1) It obtains the Json object of the remote method using the 
    *    method getRemoteReference of the RemoteRefInterface interface. 
    * 2) It prepares the message. In this case another Json object with 
    * the parameters as follows: 
    */
