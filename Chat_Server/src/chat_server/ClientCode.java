package chat_server;

import java.net.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

import javax.swing.JComboBox;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author vinod thorat : 
 *
 *This class is for client which will obtain the connection with server.
 *When we run this class client user will get added to server after successful connection with given port and IP address.
 */
public class ClientCode extends javax.swing.JFrame 
{
	

    /**
	 * serialVersionUID for client
	 */
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = Logger.getLogger(ClientCode.class);
	
	String username;
    String address_IP = "localhost";
    ArrayList<String> clientUserList = new ArrayList<String>();
    int port = 2222;
    Boolean connection_Done = false;
    String[] data_Values;
    Socket socket;
    BufferedReader readerInfo,readerInfo123;
    PrintWriter writerInfo;
    
    /**
     * ListenThread method : 
     * 
     * This method is for listening thread.
     */
    public void ThreadListening() 
    {
    		logger.info("CLIENT INFO : Called ThreadListening Method");
         Thread ThreadReader = new Thread(new ThreadReader());
         
         logger.info("CLIENT INFO : Startig Thread");
         ThreadReader.start();
    }
    
    /**
     * addUser : 
     * This method is for adding online users in a given list.
     * 
     * @param info
     * 
     * 
     */
    public void addUser(String info) 
    {
    		logger.info("CLIENT INFO : Adding User information");
        clientUserList.add(info);
    }
    
    /**
     * removeUser : 
     * This method is for removing users in a given list.
     * 
     * @param info
     * 
     * 
     */
    public void removeUser(String info) 
    {
    		logger.info("CLIENT INFO : Removing User information");
         //ta_chat.append(info + " this is disconnected now.\n");
    }
    
    /**
     * userInformationWrite : 
     * 
     * Contains writing logic for given list of user.
     */
    public void userInformationWrite() 
    {
    		logger.info("CLIENT INFO : userInformation Write User information");
        
         String[] tempList = new String[(clientUserList.size())];
         clientUserList.toArray(tempList);
//         for (String token:tempList) 
//         {
//             //users.append(token + "\n");
//         }
    }
    
    /**
     * sendingDisconnectInformation : 
     * 
     * User disconnect Information.
     */
    public void sendingDisconnectInformation() 
    {
    		logger.info("CLIENT INFO : Now sendingDisconnectInformation ");
        
        String bye = (username + ": :Disconnect");
        try
        {
            writerInfo.println(bye); 
            writerInfo.flush(); 
            data_Values = new String[0];
        } catch (Exception e) 
        {
            ta_chat.append("Could not send Disconnect message.\n");
            logger.error("CLIENT INFO : Could not send Disconnect message. ");
            
        }
    }

    /**
     * disconnectClient : 
     * 
     * ADD information to the chat text that user disconnected
     * and call sendingDisconnectInformation()
     */
    public void disconnectClient() 
    {
    		logger.info("CLIENT INFO : Now disconnectClient");
        
        try 
        {
            ta_chat.append("Disconnected.\n");
            socket.close();
            logger.info("CLIENT INFO : Connection with socket Closed Successfully.");
            
        } catch(Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
            logger.error("CLIENT INFO : Failed to disconnect, Please check given information. ");
            
        }
        connection_Done = false;
        tf_username.setEditable(true);

    }
    
    
    public ClientCode() 
    {
    		logger.info("CLIENT INFO : Initializing All user defined components.");
        initializingAllUIVariables();
    }
    
    
    public class ThreadReader implements Runnable
    {
        @Override
        public void run() 
        {
        		logger.info("CLIENT INFO : calling run() method from  ThreadReader.");
            
            String[] data;
            String streamReaderInfo;
            String done = "Done"; 
            	String connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try 
            {
            		// Get messages from the client, line by line; return them
                while ((streamReaderInfo = readerInfo.readLine()) != null) 
                {
                     data = streamReaderInfo.split(":");

                     System.out.println("DATATATATATTAA 2222222222 : " + data[2]);
                     if (data[2].equals(chat)) 
                     {
                    	 	logger.info("CLIENT INFO : stream data matched with chat option");
                         
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                     } 
                     else if (data[2].equals(connect))
                     {
                    	 	logger.info("CLIENT INFO : stream data matched with connect option");
                        ta_chat.removeAll();
                        addUser(data[0]); // add each extracted detail from the text file that was stored in the list of the stuff object
                        
                        if(cbox1.getSelectedIndex() == -1) {
                        			cbox1.addItem("All");
                                    
                        }
                    	    cbox1.addItem(data[0]); 
                    	    
                    	    Object selected = cbox1.getSelectedItem();
                            System.out.println("Selected Item  = " + selected);
                            if("All".equals(selected)) {
                        			//System.out.println("Action Command =  ALLLLLLLLLLLLLLLLL");
                            }
                        
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                    	 	logger.info("CLIENT INFO : stream data matched with disconnect option and calling remove user method");
                         
                         removeUser(data[0]);
                         cbox1.removeItem(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                    	 	logger.info("CLIENT INFO : Done with this information so clearing data from user list before it calling userInformationWrite method.");
                    	 	cbox1.removeItem(data[0]);
                            
                        userInformationWrite();
                        clientUserList.clear();
                     }
                }
           }catch(Exception ex) { }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void initializingAllUIVariables() {
    		
    		logger.info("CLIENT INFO : Initializing All the UI components.");
        
        lb_address = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        b_anonymous = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();
        
        cbox = new javax.swing.JLabel();
        cbox.setText("Sort by client Name: ");
        cbox1  = new javax.swing.JComboBox();
        cbox1.setSelectedIndex(-1);
        
        
        cbox1 = new JComboBox(clientUserList.toArray());  // a constructor which accepts an array of string items unlike AWT Choice component 
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Application - Client Window");
        setName("client"); 
        setResizable(true);

        lb_address.setText("IP Address : ");

        tf_address.setText("localhost");
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipAddressAction(evt);
            }
        });

        lb_port.setText("Port :");

        tf_port.setText("2222");
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portNumberAction(evt);
            }
        });
        
        cbox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	comboBoxUserDataAction(evt);
            }
        });
        
     
        lb_username.setText("User Name :");

        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userDataNameAction(evt);
            }
        });


        b_connect.setText("Connect");
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectAction(evt);
            }
        });

        b_disconnect.setText("Detach");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectAction(evt);
            }
        });

        b_anonymous.setText("Guest Login");
        b_anonymous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestAction(evt);
            }
        });

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_send.setText("SEND MESSAGE");
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageAction(evt);
            }
        });

        lb_name.setText("vthorat1");
        lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        getContentPane().setPreferredSize(new Dimension(700, 550));
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_chat)
                        //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_send))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                            .addComponent(tf_username))
                        .addGap(10, 10, 10)
                        
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            //.addComponent(lb_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        		//.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbox, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                        //.addComponent(cbox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                
                                
                                //.addGap(18, 18, 18) 
                                
                            .addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        		.addComponent(cbox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        
                            .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(b_connect)
                                .addGap(2, 2, 2)
                                .addComponent(b_disconnect)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(b_anonymous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_name)
                .addGap(201, 201, 201))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_address)
                    .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    //.addComponent(cbox)
                    //.addComponent(cbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    
                    .addComponent(lb_port)
                    .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_anonymous))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_username)
                    .addComponent(cbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_username)
                        .addComponent(cbox)
                        .addComponent(b_connect)
                        .addComponent(b_disconnect)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_chat)
                    .addComponent(b_send))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_name))
        );

        pack();
    }
    
    private void ipAddressAction(java.awt.event.ActionEvent evt) {
    	
    }
    private void portNumberAction(java.awt.event.ActionEvent evt) {
    	
    }
    private void comboBoxUserDataAction(ActionEvent evt) {
    		JComboBox<?> comboBox = (JComboBox<?>) evt.getSource();
    		logger.info("CLIENT INFO : calling combo box action for filtering data as per user.");
            
    		Object selected = comboBox.getSelectedItem();
            System.out.println("Selected Item  = " + selected);
         //   ThreadListening();
            
          //  if("All".equals(selected)) {
        	//	System.out.println("Action Command =  ALLLLLLLLLLLLLLLLL");
                
           // 	gellALlUsers();
            	
           // }
          //  else{
                
             /*   String[] data;
                String streamReaderInfo;
                String done = "Done"; 
                	String connect = "Connect", disconnect = "Disconnect", chat = "Chat";

                try 
                {
                	
                	System.out.println("Action Command =  USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                    
                		socket = new Socket("localhost", 2222);
                    InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
                    //readerInfo = new BufferedReader(streamreader);
                   // InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
                    readerInfo123 = new BufferedReader(streamreader);
                    writerInfo = new PrintWriter(socket.getOutputStream());
                    writerInfo.println(username + ":has connected.:Connect");
                    writerInfo.flush(); 
                    connection_Done = true; 
                } 
                catch (Exception ex) 
                {
                    ta_chat.append("Cannot Connect! Try Again, check port Number. \n");
                    tf_username.setEditable(true);
                    logger.error("CLIENT ERROR : Having Issue with connection, Cannot Connect! Try Again.");
                }
                
                ThreadListening123(selected);
             
*/              
       //     } 
             
            
        }
    

    private void ThreadListening123(Object selected) {
		// TODO Auto-generated method stub
    	
    	String[] data;
        String streamReaderInfo;
        String done = "Done"; 
        	String connect = "Connect", disconnect = "Disconnect", chat = "Chat";

   		// Get messages from the client, line by line; return them
        try {
			while ((streamReaderInfo = readerInfo123.readLine()) != null) 
			{
				System.out.println("Action Command =  USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
			    
			     data = streamReaderInfo.split(":");
			     
			     
			     if (data[0].contains(selected.toString())) {
					
			    	 
			    	 	System.out.println("Action Command =  USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
			            
			    	 ta_chat.removeAll();
			    	 ta_chat.append(data[0] + ": " + data[1] + "\n");
			      ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
			    	 
			     }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
	
		
	
	private void gellALlUsers() {
    
	}

	private void userDataNameAction(java.awt.event.ActionEvent evt) {
    	
    }
    
    private void connectAction(java.awt.event.ActionEvent evt) {
    	logger.info("CLIENT INFO : checking connection.");
        
    		if (connection_Done == false) 
        {
    			logger.info("CLIENT INFO : No previous connection exists.");
    		    
            username = tf_username.getText();
            tf_username.setEditable(false);
            port = Integer.parseInt(tf_port.getText());
            address_IP = tf_address.getText();
            try 
            {
            
            	if(username.isEmpty()) {
            		ta_chat.append("Please Connect with User Name for Future References. \n");
            		
            }
            
            	
            	logger.info("CLIENT INFO : Creating connection with given information of IP and Port." + address_IP + "and port is" + port);
                
                socket = new Socket(address_IP, port);
                InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
                readerInfo = new BufferedReader(streamreader);
                writerInfo = new PrintWriter(socket.getOutputStream());
                writerInfo.println(username + ":has connected.:Connect");
                writerInfo.flush(); 
                connection_Done = true; 
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again, check port Number. \n");
                tf_username.setEditable(true);
                logger.error("CLIENT ERROR : Having Issue with connection, Cannot Connect! Try Again.");
            }
            
            ThreadListening();
            
        } else if (connection_Done == true) 
        {
            ta_chat.append("You are already connected with given port. \n");
            logger.info("CLIENT INFO : You are already connected with given port.");
        }
    }

    private void disconnectAction(java.awt.event.ActionEvent evt) {
    		logger.info("calling disconnect event for disconnecting client.");
        
        sendingDisconnectInformation();
        disconnectClient();
    }
    
    private void guestAction(java.awt.event.ActionEvent evt) {
    	
    		logger.info("CLIENT INFO : Creating Guest even for adding guest using this action.");
        
        tf_username.setText("");
        if (connection_Done == false) 
        {
        		logger.info("CLIENT INFO : Creating Guest even for adding guest using this action.");
            
            String anon="Guest ";
            Random generator = new Random(); 
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            
            tf_username.setText(anon);
            tf_username.setEditable(false);
            port = Integer.parseInt(tf_port.getText());
            address_IP = tf_address.getText();

            try 
            {
                socket = new Socket(address_IP, port);
                InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
                readerInfo = new BufferedReader(streamreader);
                writerInfo = new PrintWriter(socket.getOutputStream());
                writerInfo.println(anon + ":has connected.:Connect");
                writerInfo.flush(); 
                connection_Done = true; 
                logger.info("CLIENT INFO : Creating Guest connection Successfully Done.");
                
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Having Issue, Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
                logger.error("CLIENT ERROR : Having Issue, Cannot Connect! Try Again.");
                
            }
            
            ThreadListening();
            
        } else if (connection_Done == true) 
        {
            ta_chat.append("Connection : You are already connected. \n");
            logger.warn("CLIENT WARN : Connection : You are already connected.");
            
        }        
    }
    private void sendMessageAction(java.awt.event.ActionEvent evt) {
    	
    		logger.info("CLIENT INFO : Sending Message.");
         
    		String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
               writerInfo.println(username + ":" + tf_chat.getText() + ":" + "Chat");
               writerInfo.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent, due to some issue, Please check logs. \n");
                logger.error("CLIENT ERROR : Message was not sent, due to some issue, Please check logs.");
                
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }
    
    
    // Component declaration which is used during this Client phase.
    //This variables used for the giving best look and working functionality of this application.
    private javax.swing.JButton b_anonymous;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;
    private javax.swing.JComboBox<String> cbox1;
    private javax.swing.JLabel cbox;
    
    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or host name of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
    public static void main(String args[]) 
    {
    	
    		String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "src" + File.separator + "log4j.properties";
    		PropertyConfigurator.configure(log4jConfigFile);
    		logger.info("CLIENT : START : this is a information log message");
    		logger.info("CLIENT INFORMATION : Client Application Starting");
       
    		java.awt.EventQueue.invokeLater(new Runnable() 
    		{
            @Override
            public void run() 
            {
                new ClientCode().setVisible(true);
                
            }
        });
    }
    

}
