package view;

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
	
	public EtherpadGUI() {
		super("Etherpad GUI");
		
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
		
		GroupLayout completeLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(completeLayout);
		
		completeLayout.setAutoCreateGaps(true);
        completeLayout.setAutoCreateContainerGaps(true);
		
		completeLayout.setHorizontalGroup(completeLayout
				.createParallelGroup()
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
		
	}
	
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EtherpadGUI main = new EtherpadGUI();

				main.setVisible(true);
			}
		});
	}
}
