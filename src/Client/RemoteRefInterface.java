package Client;

/**
 *
 * @author Jonah
 */
public interface RemoteRefInterface {
    
    /**
     * Gets remoteMethod and prepares string for marshalling in proxy
     * @param methodName Name of remote method
     * @return String array of methods catalog definition used for marshalling request
     */
    public String[] getRemoteReference(String methodName);
    
}



