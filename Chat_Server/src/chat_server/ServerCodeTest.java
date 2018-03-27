package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerCodeTest {
    /**
     * Set up logic.
     * @throws Exception Thrown in case of any error.
     */
    @Before
    public void beforeTestExec() throws Exception {
        System.out.println("Testing : Preparing for testcase execution: " + getClass().getSimpleName());
    }

    /**
     * Shut down logic.
     * @throws Exception Thrown in case of any error.
     */
    @After
    public void afterTestExec() throws Exception {
        System.out.println("Shuttig down test execution: " + getClass().getSimpleName());
    }

    /**
     * Example test ServerCode method.
     */
    @Test
    public void testServerCode() {
      boolean isCheck = false;
      ServerSocket serverSocket = null;
      final String hostName = "localhost";
      try {
          InetAddress inetAddress = InetAddress.getLocalHost();

          if (hostName.compareTo(inetAddress.getHostName())==0){
              serverSocket = new ServerSocket(2222);
              while (!isCheck){
                  Socket clientData =  serverSocket.accept();
                  System.out.println(getClass().getSimpleName() +"Getting Data " + clientData.getInetAddress());
                  ObjectInputStream objectInputStream = new ObjectInputStream(clientData.getInputStream());
                  ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientData.getOutputStream());
                  final Date checkdatewithObject  = (Date)objectInputStream.readObject();
                  System.out.println(getClass().getSimpleName() +" read data object from client " + checkdatewithObject);
                  objectInputStream.close();
                  objectOutputStream.close();
                  isCheck = true;
              }
          }
      } catch (UnknownHostException e) {

          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }finally{
          try {
              if(serverSocket != null){
                  serverSocket.close();
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      System.out.println(getClass().getSimpleName() + "Output from test class and method for connecting.");
    }
}