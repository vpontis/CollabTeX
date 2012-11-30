package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class DocEdit extends JFrame {
	
	private JLabel messageLabel;
	private JButton exitButton;
	private JLabel collabLabel;
	private JLabel collaborators; 
	private JTextArea textArea;
	private JScrollPane scrollText;
	
	private PrintWriter out;
	private String docName;
	private String userName;
	private String docContent;
	
	private Document textDocument;
	
	public DocEdit(PrintWriter outputStream, String documentName, String userName, String content){
		super(documentName);
		
		out = outputStream;
		this.docName = documentName;
		this.userName = userName;
		this.docContent = content;

		messageLabel = new JLabel("Welcome!");
		exitButton = new JButton("Exit Doc");
		
		collabLabel = new JLabel("Also working: ");
		collaborators = new JLabel("collab people");
		
		textArea = new JTextArea(20, 50);
		scrollText = new JScrollPane(textArea);
		textArea.setText(docContent);
		textDocument = textArea.getDocument();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		//this sets up the horizontal alignment
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
		
		//this sets up the vertical alignment
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
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				exitDocument();	
			}
		});		
		
		textDocument.addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				//No code here yet
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {				
					int position = e.getOffset();
					int length = e.getLength();
					String change = textDocument.getText(position, length);
					if (change.equals("\n")) {
						out.println("CHANGE|" + docName + "|" + position + "|" + "\t");
					}  else if (! change.equals("")){
						out.println("CHANGE|" + docName + "|" + position + "|" + change);
					}
					
					
				} catch (BadLocationException e1) {
					throw new UnsupportedOperationException();
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				int position = e.getOffset();
				int length = e.getLength();
				out.println("CHANGE|" + docName + "|" + "del" + "|" + position + "|" + length);
				
			}
		});
		
		this.pack();
	}
	
	/**
	 * Method to update content in the text area
	 * @param newContent New content in the text area
	 */
	public void updateContent(String newContent) {
		this.textArea.setText(newContent);
	}
	
	/**
	 * Method that returns the content in the text area
	 * @return Content in the text area
	 */
	public String getContent() {
		String content = this.textArea.getText();
		return content;
	}
	
	/**
	 * Method for the user to exit the given document
	 */
	private void exitDocument() {
		out.println("EXITDOC " + userName + " " + docName);	
	}
	
	/**
	 * getter for the name of the GUI element
	 */
	public String getName() {
		return docName;
	}

	public static void main(String[] args){
		DocEdit main = new DocEdit(new PrintWriter(System.out), "Document name", "victor", "");
		main.setVisible(true);
	}
	
}
