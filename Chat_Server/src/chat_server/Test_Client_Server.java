package chat_server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.jupiter.api.Test;


class Test_Client_Server {

	private ServerSocket serverSocket;
	private OutputStream serverOut;
    private InputStream serverIn;
    /**
     * Shared lock between the "client" and "server" code, to make the test case
     * synchronous.
     */
    private Semaphore checkLock = new Semaphore(0);

	
	@Before 
    public void before() { 
            System.out.println("@Before"); 
            Thread myThread = new Thread() { 
                    public void run() { 
                            System.out.println("@Before myThread run()"); 
                            try { 
                                    ServerSocket myServer = new ServerSocket(2222); 
                                    System.out 
                                                    .println("@Before myThread run() - server socket created."); 
                                    myServer.accept(); 
                                    System.out 
                                                    .println("@Before myThread run() - accepted connection"); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
                    } 
            }; 
            myThread.start(); 
    } 
	
	
	@Test
	public void itDoesntAssertInSeparateThreads() {
	  Thread otherThread = new Thread(new Runnable() {
	    @Override
	    public void run() {
	      assertTrue(false);
	    }
	  });

	  otherThread.start();
	  assertTrue(true);
	}
	
	
    
	 
    /**
     * Tests server and client side sockets in one flow. A lock object is used for
     * synchronous between the two sides.
     */
    @Test
    public void testClientServer() throws IOException, InterruptedException {
      ServerSocket server = new ServerSocket(2020);
      
     // ServerStart sc = new server_frame.ServerStart();
      listen(server);

      Socket client = new Socket("localhost", 2020);
      client.getOutputStream();
      client.getInputStream();

      System.out.println("Waiting for lock");
      checkLock.acquire();
      System.out.println("Acquired lock");

     
      client.close();
      server.close();
    }
    
    private void listen(ServerSocket server) {
        new Thread(() -> {
          try {
            Socket socket = server.accept();
            System.out.println("Incoming connection: " + socket);

            serverOut = socket.getOutputStream();
            serverIn = socket.getInputStream();

            checkLock.release();
            System.out.println("Released lock");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();
      }


}
