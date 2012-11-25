package view;

import javax.swing.*;

@SuppressWarnings("serial")
public class DocEdit extends JFrame {
	
	private JLabel messageLabel;
	private JButton exitButton;
	private JLabel collabLabel;
	private JLabel collaborators; 
	private JTextArea textArea;
	private JScrollPane scrollText;
	
	public DocEdit(String documentName){
		super(documentName);
		
		messageLabel = new JLabel("Welcome!");
		exitButton = new JButton("Exit Doc");
		
		collabLabel = new JLabel("Also working: ");
		collaborators = new JLabel("collab people");
		
		textArea = new JTextArea(20, 50);
		scrollText = new JScrollPane(textArea);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
		hGroup.addGroup(
				layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
							.addComponent(collabLabel)
							.addComponent(collaborators)
							)
					.addComponent(messageLabel)
					);
		hGroup.addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addComponent(exitButton)
					.addComponent(scrollText)
					);
		layout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(
					layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(messageLabel)
						.addComponent(exitButton)
					);
		vGroup.addGroup(
					layout.createParallelGroup()
						.addComponent(collabLabel)
						.addComponent(collaborators)
					);
		vGroup.addGroup(
					layout.createParallelGroup()
						.addComponent(scrollText)
					);
		layout.setVerticalGroup(vGroup);
		this.pack();
	}
	
	public static void main(String[] args){
		DocEdit main = new DocEdit("Document name");
		main.setVisible(true);
	}
	
}
