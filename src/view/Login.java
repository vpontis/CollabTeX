package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame{
	private JButton loginButton;
	private JTextField userName;
	private JTextField password;
	private JLabel userNameLabel;
	
	private JLabel messageLabel;
	
	private PrintWriter out;

	public Login(PrintWriter outputStream) {
		super("Etherpad GUI");
		out = outputStream;
		
		//Initializing the login button
		loginButton = new JButton();
		loginButton.setName("newLoginButton");
		loginButton.setText("Login");
				
		//Initializing the username text field
		userName = new JTextField();
		userName.setName("userNameField");
		
		userNameLabel = new JLabel();
		userNameLabel.setName("userNameLabel");
		userNameLabel.setText("Username: ");

		messageLabel = new JLabel("Hello there, enter a username and login.");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		GroupLayout completeLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(completeLayout);
		
		completeLayout.setAutoCreateGaps(true);
        completeLayout.setAutoCreateContainerGaps(true);
		
		completeLayout.setHorizontalGroup(completeLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(
						completeLayout.createSequentialGroup()
							.addComponent(messageLabel)
						)
				.addGroup(
						completeLayout.createSequentialGroup()
							.addComponent(userNameLabel)
	                        .addComponent(userName)
                        )
                .addGroup(
                		completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                			.addComponent(loginButton, GroupLayout.Alignment.CENTER)
                		)
                
			);
		
		completeLayout.setVerticalGroup(completeLayout
				.createSequentialGroup()
				.addGroup(
						completeLayout.createParallelGroup()
							.addComponent(messageLabel)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(userNameLabel)
							.addComponent(userName)
						)
				.addGroup(
						completeLayout.createParallelGroup()
							.addComponent(loginButton)
						)
			);
		
		//Action listener for the new login button
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
				
		this.pack();
	}
	
	private void login() {
		String name = userName.getText();
		String output = "login " + name;
		out.println(output);
	}
			
	synchronized void resetName() {
		userName.setText("");
	}

	void failedLogin() {
		resetName();
		messageLabel.setText("Error: try a different name");
	}
		
	public static void main(String[] args){
	    Login main = new Login(new PrintWriter(System.out));
	    main.setVisible(true);
	}

}
