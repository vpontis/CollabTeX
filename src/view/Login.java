package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Represents the Login GUI element. Allows the user to login to the
 * system. The user may either be logged in or denied access, depending
 * on whether the user with the same username is already logged in or 
 * not.
 */
@SuppressWarnings("serial")
public class Login extends JFrame{
	private JButton loginButton;
	private JTextField userName;
	//private JTextField password;
	private JLabel userNameLabel;
	
	private JLabel messageLabel;
	
	private PrintWriter out;

	/**
	 * Contructor of the Login GUI element
	 * @param outputStream PrintWriter onto which the login screen published requests to the server
	 */
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

		// Initialize the message label to contain a welcome message
		messageLabel = new JLabel("Hello there, enter a username and login.");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		GroupLayout completeLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(completeLayout);
		
		completeLayout.setAutoCreateGaps(true);
        completeLayout.setAutoCreateContainerGaps(true);
		
        // horizontal layout of elements within the login GUI
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
		
		// vertical layout of elements within the login GUI
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
		
		//Action listener for the text box in which the user types in the user name
		userName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
				
		this.pack();
	}
	
	/**
	 * Tries to log the current user into the server
	 * If the login is successful, the user can now edit documents
	 * If the login is not successful, the user remains in the login screen
	 */
	private void login() {
		String name = userName.getText().trim();
		if (name.contains(" ")){
			resetName();
			messageLabel.setText("Your login cannot have spaces");
			return;
		}
		String output = "LOGIN " + name;
		out.println(output);
	}
	
	/**
	 * Resets the name field within the GUI element, so that it no longer contains text
	 */
	synchronized private void resetName() {
		userName.setText("");
	}
	
	/**
	 * Method that is called when login fails. 
	 * Error message is displayed in the message label
	 */
	void failedLogin() {
		resetName();
		messageLabel.setText("Error: try a different name");
	}
		
	/**
	 * Sets up a new login GUI element. For testing purposes alone
	 * @param args Unused
	 */
	public static void main(String[] args){
	    Login main = new Login(new PrintWriter(System.out));
	    main.setVisible(true);
	}

}
