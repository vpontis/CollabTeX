package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class EtherpadGUI extends JFrame {
	private JButton loginButton;
	private JButton logonButton;
	private JTextField userName;
	private JTextField password;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	
	private JLabel resultLabel;
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public EtherpadGUI() throws UnknownHostException, IOException {
		super("Etherpad GUI");
		
		socket = new Socket("127.0.0.1",4444);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

		//Initializing the login button
		loginButton = new JButton();
		loginButton.setName("newLoginButton");
		loginButton.setText("Login");
		
		//Initializing the logon button
		logonButton = new JButton();
		logonButton.setName("newLogonButton");
		logonButton.setText("Logon");
		
		//Initializing the username text field
		userName = new JTextField();
		userName.setName("userNameField");
		
		//Initializing the password text field
		password = new JTextField();
		password.setName("passwordField");
		
		userNameLabel = new JLabel();
		userNameLabel.setName("userNameLabel");
		userNameLabel.setText("User Name : ");
		
		passwordLabel = new JLabel();
		passwordLabel.setName("userNameLabel");
		passwordLabel.setText("Password : ");
		
		resultLabel = new JLabel();
		resultLabel.setName("resultLabel");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GroupLayout completeLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(completeLayout);
		
		completeLayout.setAutoCreateGaps(true);
        completeLayout.setAutoCreateContainerGaps(true);
		
		completeLayout.setHorizontalGroup(completeLayout
				.createParallelGroup()
				.addGroup(
						completeLayout.createSequentialGroup()
						.addComponent(resultLabel)
						)
				.addGroup(
						completeLayout.createSequentialGroup()
						.addComponent(userNameLabel)
                        .addComponent(userName)
                        )
                .addGroup(
                		completeLayout.createSequentialGroup()
                		.addComponent(passwordLabel)
                		.addComponent(password)
                		)
                .addGroup(
                		completeLayout.createSequentialGroup()
                		.addComponent(loginButton)
                		.addComponent(logonButton)
                		)
                
			);
		
		completeLayout.setVerticalGroup(completeLayout
				.createSequentialGroup()
				.addGroup(
						completeLayout.createParallelGroup()
						.addComponent(resultLabel)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(userNameLabel)
						.addComponent(userName)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(passwordLabel)
						.addComponent(password)
						)
				.addGroup(
						completeLayout.createParallelGroup()
						.addComponent(loginButton)
						.addComponent(logonButton)
						)
			);
		
		//Action listener for the new login button
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = userName.getText();
				String passwordname = password.getText();
				String output = "login " + name + " " + passwordname;
				out.println(output);
				
				try {
					String response = in.readLine();
					resultLabel.setText(response);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				userName.setText("");
				password.setText("");
			}
		});
		
		//Action listener for the new logon button
		logonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = userName.getText();
				String passwordname = password.getText();
				String output = "logon " + name + " " + passwordname;
				out.println(output);
				
				try {
					String response = in.readLine();
					resultLabel.setText(response);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				userName.setText("");
				password.setText("");
			}
		});
		
	}
	
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EtherpadGUI main;
				try {
					main = new EtherpadGUI();
					main.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		});
	}
}
