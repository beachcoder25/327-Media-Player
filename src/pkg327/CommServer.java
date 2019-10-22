package pkg327;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Jonah
 */
public class CommServer {

    /**
     * @param args the command line arguments
     */
    Dispatcher dispatcher = new Dispatcher();

    public final int FRAGMENT_SIZE = 65000;

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
