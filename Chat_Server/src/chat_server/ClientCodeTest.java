package chat_server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientCodeTest {
    /**
     * Set up logic for client information.
     * @throws Exception Thrown in case of any error.
     */
    @Before
    public void beforeTest() throws Exception {
        System.out.println("Testig : Preparing for test execution: " + getClass().getSimpleName());
    }

    /**
     * Shut down logic for given port socket.
     * @throws Exception Thrown in case of any error.
     */
    @After
    public void afterTest() throws Exception {
        System.out.println("Testig : Shutting down test execution: " + getClass().getSimpleName());
    }

    @Test
    public void testClientCode() {
        try {
             System.out.println(getClass().getSimpleName() +" Wait 6 seconds server to start and get connection");
             Thread.sleep(6000);
             final String hostName = "localhost";
             Socket socket = new Socket(hostName, 2222);
             ObjectOutputStream ou = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ou.writeObject("THIS IS TESTING FOR CLIENT COMMUNICATION >>>>>>>>>>>>>>>>>>> \n\n\n");
             ou.flush();
             ou.close();
             in.close();
           } catch(Exception e) {
             System.out.println(e.getMessage());
          }
    }
}