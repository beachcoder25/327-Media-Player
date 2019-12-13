package Server;

/**
 *
 * @author Jonah
 */

public interface DispatcherInterface {

    /**
     * Dispatces request for unmarshalling, and RPC call
     * @param request Request to be unmarshalled
     * @return The return value of method that was invoked
     */
    public String dispatch(String request);

    /**
     * Registers object so methods of class can be called
     * @param remoteMethod Name of remote method being called
     * @param objectName Name of class remote method resides in
     */
    public void registerObject(Object remoteMethod, String objectName);
}
