package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private String collaboratorNames;
	
	private Document textDocument;
	private final DocumentListener documentListener;
	
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
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				exitDocument();	
			}
		});		
		
		documentListener = new DocumentListener() {
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
		if ((posChange < textArea.getText().length()) && (posChange >= 0)) {
			this.textArea.setText(newContent);
			this.textArea.setCaretPosition(posChange);
		}
		
	}
	
	/**
	 * Method that returns the content in the text area
	 * @return Content in the text area
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

	public static void main(String[] args){
		DocEdit main = new DocEdit(new PrintWriter(System.out), "Document name", "victor", "", "collab");
		main.setVisible(true);
	}
	
}
