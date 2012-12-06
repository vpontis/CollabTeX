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

/**
 * Represents the DocEdit GUI element. Allows the user to edit a document.
 * All changes made in the document are updated back to the server.
 * It is possible for the user to return to the document table from the
 * DocEdit GUI element.
 */
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
	private String collaboratorNames;
	
	private Document textDocument;
	private final DocumentListener documentListener;
	
	/**
	 * Constructor of the DocEdit GUI element
	 * @param outputStream PrintWriter on which client publishes requests to the server
	 * @param documentName Name of the document which is currently being edited
	 * @param userName Name of the user currently making the edit on the document
	 * @param content Initial content of the document, when the document is loaded from the server
	 * @param collaboratorNames The initial list of collaborators of the document at the time the document is loaded from the server
	 */
	public DocEdit(PrintWriter outputStream, String documentName, String userName, String content, String collaboratorNames){
		super(documentName);
		
		out = outputStream;
		this.docName = documentName;
		this.userName = userName;
		this.docContent = content;
		this.collaboratorNames = collaboratorNames;

		messageLabel = new JLabel("Welcome!");
		exitButton = new JButton("Exit Doc");
		
		collabLabel = new JLabel("Collaborators: ");
		collaborators = new JLabel(collaboratorNames);
		
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
		
		// Add an action listener to the exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				exitDocument();	
			}
		});		
		
		// Adds a document listener to the document associated with the JTextArea
		documentListener = new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("CHANGED!");
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					int position = e.getOffset();
					int length = e.getLength();
					System.out.println(String.valueOf(position) + " " + String.valueOf(length));
					String change = textDocument.getText(position, length);
					if (change.equals("\n")) {
						// Delimit lines with tabs
						out.println("CHANGE|" + docName + "|" + position + "|" + "\t" + "|" + length);
					}  else if (! change.equals("")){
						out.println("CHANGE|" + docName + "|" + position + "|" + change + "|" + length);
					}
					
				} catch (BadLocationException e1) {
					throw new UnsupportedOperationException();
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				int position = e.getOffset();
				int length = e.getLength();
				out.println("CHANGE|" + docName + "|" + position + "|" + length);
				
			}
		};
				
		this.addListener();
		
		this.pack();
	}
	
	/**
	 * Method to update content in the text area
	 * @param newContent New content in the text area
	 */
	public synchronized void updateContent(String newContent, int position, int length) {
		int posChange = position + length;
		posChange = Math.min(posChange, textArea.getText().length());
		posChange = Math.max(0, posChange);

		removeListener();
		textArea.setText(newContent);
		addListener();
		textArea.setCaretPosition(posChange);
	}
	
	/**
	 * Method that returns the content in the text area
	 * @return Content of the document entered by the user
	 */
	public synchronized String getContent() {
		String content = this.textArea.getText();
		return content;
	}
	
	/**
	 * Method for the user to exit the given document
	 */
	private synchronized void exitDocument() {
		out.println("EXITDOC " + userName + " " + docName);	
	}
	
	/**
	 * Getter for the name of the GUI element
	 */
	public String getName() {
		return docName;
	}
	
	/**
	 * Method to update the displayed set of collaborators
	 * @param collaboratorNames The updated list of collaborators
	 */
	public void updateCollaborators(String collaboratorNames) {
		collaborators.setText(collaboratorNames);
	}
	
	/**
	 * Method that associates document listener to the document associated with the text area
	 */
	public void addListener() {
		textDocument.addDocumentListener(documentListener);
	}
	
	/**
	 * Method that disassociates the document listener from the document associated with the text area
	 */
	public void removeListener() {
		textDocument.removeDocumentListener(documentListener);
	}

	/**
	 * Sets up a new login DocEdit element. For testing purposes alone
	 * @param args Unused
	 */
	public static void main(String[] args){
		DocEdit main = new DocEdit(new PrintWriter(System.out), "Document name", "victor", "", "collab");
		main.setVisible(true);
	}
	
}
