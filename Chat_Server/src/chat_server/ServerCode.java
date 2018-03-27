package chat_server;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author vinod thorat : 
 * 
 * A TCP server that runs on port 2222.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class ServerCode extends javax.swing.JFrame 
{
	
   /**
	 * serialVersionUID for Server
	 */
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(ServerCode.class);
	
	int port = 2222;
	
	Boolean connection_Done = false;
    
    
	ArrayList userDataOutputStreams;
	ArrayList<String> users_Info;
	
	HashMap<String, String> hashmap = new HashMap<>();

   
public class CommunicationPhase implements Runnable	
   {
       BufferedReader reader;
       Socket socket;
       PrintWriter clientInformation;

       public CommunicationPhase(Socket clientSocket, PrintWriter user) 
       {
            clientInformation = user;
            try 
            {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Unexpected error... \n");
                logger.error("ERROR : Error Information while getting information about user using getInputStream");
            }

       }

       @Override
       public void run() 
       {
            String information;
            String connect = "Connect", disconnect = "Disconnect";
            String chat = "Chat" ;
            String[] dataStream;

            try 
            {
                while ((information = reader.readLine()) != null) 
                {
                    ta_chat.append("Getting The data : " + information + "\n");
                    dataStream = information.split(":");
                    logger.error("This is error for  : " + dataStream[0]);
                    for (String single_Data:dataStream) 
                    {
                        ta_chat.append(single_Data + "\n");
                    }

                    if (dataStream[2].equals(connect)) 
                    {
                        broadcastMessage((dataStream[0] + ":" + dataStream[1] + ":" + chat));
                        addUserData(dataStream[0]);
                        logger.info("INFO : On Connect: Num clients after connect: " + userDataOutputStreams.size());
                        
                    } 
                    else if (dataStream[2].equals(disconnect)) 
                    {
                    	logger.info("INFO : dataStream   On Disconnect: Num clients after disconnect: " + dataStream[2]);
                        
                        broadcastMessage((dataStream[0] + ":has disconnected." + ":" + chat));
                        removeUserData(dataStream[0]);
                        userDataOutputStreams.remove(clientInformation);
                        logger.info("INFO : On Disconnect: Num clients after disconnect: " + userDataOutputStreams.size());
                    } 
                    else if (dataStream[2].equals(chat)) 
                    {
                        broadcastMessage(information);
                        logger.info("INFO : On Chat Information: Num clients after disconnect: " + userDataOutputStreams.size());
                        
                    } 
                    else 
                    {
                        ta_chat.append("No Conditions were met for this, Please check it once. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Connection Lost. \n");
                ex.printStackTrace();
                logger.error("ERROR : This is error for Lost Connection  : " + ex.getMessage());
                
                userDataOutputStreams.remove(clientInformation);
                logger.info("INFO : Remove operation on given data userDataOutputStreams");
                
             } 
	} 
    }

    public ServerCode() 
    {
        initializingUIcomponentsServer();
    }

    private void initializingUIcomponentsServer() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        
        lb_port.setText("(Port)");

        tf_port.setText("2222");
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portNumberAction(evt);
            }
        });

        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Application - Server  Window");
        setName("server"); 
        setResizable(false);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setText("START SERVER");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonAction(evt);
            }
        });

        b_end.setText("END SERVER");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonAction(evt);
            }
        });

        b_users.setText("Online Client");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineUserButtonAction(evt);
            }
        });

        b_clear.setText("Clear Text");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonAction(evt);
            }
        });

        lb_name.setText("vthorat1");
        lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            
                            )
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_users, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
                    .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        		.addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE))
                
    )
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_name)
                .addGap(209, 209, 209))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_start)
                    .addComponent(b_users)
                    )
                .addGap(10,10,10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                		.addComponent(lb_port)
                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_clear)
                    .addComponent(b_end))
                .addGap(4, 4, 4)
                .addComponent(lb_name))
        );

        pack();
    }
    private void endButtonAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        try 
        {
            Thread.sleep(6000);//6000 milliseconds is Six second.
        	
            tf_port.setEditable(true);
            logger.info("INFO : Here Thread is sleeping for five seconds.");
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        broadcastMessage("Server:is stopping and all users will be disconnected.\n:Chat");
        logger.error("ERROR : Server:is stopping and all users will be disconnected.\\n:Chat and Server stopping");
        
        ta_chat.append("Server stopping... \n");
        
        ta_chat.setText("");
    }
    
    private void startButtonAction(java.awt.event.ActionEvent evt) {
    	
    		tf_port.setEditable(false);
    		logger.info("INFO : Here Thread Start Begin.");
            
    		Thread starter = new Thread(new ServerStart());
        starter.start();
        if(connection_Done == false) {
        ta_chat.append("Server started Successfully and Ready for communication.\n");
        }
    }
    
    private void onlineUserButtonAction(java.awt.event.ActionEvent evt) {
        ta_chat.append("\n Online users : \n");
        logger.info("INFO : Getting online users or clients information");
        
        for (String presentUser : users_Info)
        {
            ta_chat.append(presentUser);
            ta_chat.append("\n");
        	}    
    }
    
    private void portNumberAction(java.awt.event.ActionEvent evt) {
    	logger.info("INFO : Action happened on Port number");
        
    }
    
    private void clearButtonAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
        logger.info("INFO : Action happened on Clear Button");
        
    }
    
    
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
        		connection_Done = true;
            
            userDataOutputStreams = new ArrayList();
            users_Info = new ArrayList();  
            port = Integer.parseInt(tf_port.getText());
            
            logger.info("INFO : Information about User and port number : " + port);
            
            try 
            {
                ServerSocket serverSock = new ServerSocket(port);

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				
				logger.info("INFO : Adding information into the userDataOutputStreams from socket getOutputStream");
				userDataOutputStreams.add(writer);
				
				logger.info("INFO : num clients: " + userDataOutputStreams.size());
				
				Thread listener = new Thread(new CommunicationPhase(clientSock, writer));
				   
				listener.start();
				ta_chat.append("Got a connection. \n");
				 }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error while making a connection. Please check if connection exist.\n");
            }
            finally {
            	logger.warn("WARN : Please do close all the cooresponding connections");
				
			}
        }
    }
    
    /**
     * Adding User Data into the list to access online Users in future.
     * @param data
     */
    public void addUserData (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.append("Before adding user now  " + name + " added. \n");
        users_Info.add(name);
        ta_chat.append("After adding user now  " + name + " added. \n");
        String[] tempList = new String[(users_Info.size())];
        users_Info.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            broadcastMessage(message);
        }
        broadcastMessage(done);
    }
    
    /**
     * Removing User Data from the list to access online Users in future.
     * @param data
     */
    
    public void removeUserData (String data) 
    {
        String message, add = ": :Disconnect", done = "Server: :Done", name = data;
        users_Info.remove(name);
        String[] tempList = new String[(users_Info.size())];
        users_Info.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            broadcastMessage(message);
        }
        broadcastMessage(done);
    }
    
    /**
     * Send the given message to all the users means send all the messages to the clients (Broadcasting all the information related to the each other.)
     * @param message
     */
    public void broadcastMessage(String message) 
    {
    		Iterator it = userDataOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                logger.info("INFO : Sending messages :" + message);
                ta_chat.append("Sending: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
            		ta_chat.append("Error : While Broadcasting Message to each client. \n");
            		logger.error("Error : While Broadcasting Message to each client.");
            }
        } 
    }
    
    // Component declaration which is used during this Server phase.
    //This variables used for the giving best look and working functionality of this application.
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_name;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JLabel lb_port;
    private javax.swing.JTextField tf_port;
    
    
    /**
     * Runs the server.
     */
    public static void main(String args[]) 
    {
    	
    		String log4jConfigFile = System.getProperty("user.dir")
                 + File.separator + "src" + File.separator + "log4j.properties";
         PropertyConfigurator.configure(log4jConfigFile);
         logger.info("START : this is a information log message");
         logger.info("INFORMATION : Client Application Starting");
         
         
         java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new ServerCode().setVisible(true);
                
            }
        });
    }
   
    
}
