package Server;

import Phase3_Other.DFS;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonah
 */
public class CommServer {

    private Dispatcher dispatcher = new Dispatcher();
    private final int FRAGMENT_SIZE = 65000;
    DFS dfs;

    /**
     * Default constructor
     */
    public CommServer() {
        
        System.out.println("Server waiting.");
    }

    public static void main(String[] args) throws SocketException {

        CommServer server = new CommServer();

        server.recieveFromClient();
    }

    public DFS getDfs() {
        return dfs;
    }

    
    /**
     * Recieves request from client and calls dispatcher
     */
    public void recieveFromClient() {

        DatagramSocket skt = null;

        try {

            skt = new DatagramSocket(6788);

            byte[] buffer = new byte[FRAGMENT_SIZE];

            while (true) {

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);

                // Create a new thread that calls dispatcher
                skt.receive(request); // Recieve message

                String stringMsg = new String(request.getData()).trim();

                System.out.println("Server got DEEZ: " + stringMsg);
                String retString = dispatcher.dispatch(stringMsg);

                byte[] sendMsg = (retString).getBytes();

                DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, request.getAddress(), request.getPort());

                skt.send(reply);

                buffer = new byte[FRAGMENT_SIZE];

            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

}
