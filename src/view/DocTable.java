package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *  Represents the DocTable GUI element. Displays all documents available
 *  to edit by the user. Contains meta data about the documents.
 *  User can also create a new document in the document table; and also logout.
 */
@SuppressWarnings("serial")
public class DocTable extends JFrame{
	static private final int NAME_COLUMN = 0;
	
	private JLabel tableLabel;
	private JLabel messageLabel;
	
	private JTable documentTable;
	private DefaultTableModel tableModel;
	private JScrollPane tableScroll;
	
	private JButton newDocumentButton;
	private JTextField newDocumentName;
	
	private JButton logoutButton;
	
	private PrintWriter out;
	
	private String userName;
	
	/**
	 * Constructor of the DocTable GUI element.
	 * @param outputStream PrintWriter onto which the Doc table publishes requests to the server
	 * @param userName User name associated with the client associated with this DocTable
	 */
	public DocTable (PrintWriter outputStream, String userName) {
		super(userName + " - Document List");
		
		this.userName = userName;
		out = outputStream;
		
		tableLabel = new JLabel();
		tableLabel.setName("tableLabel");
		tableLabel.setText("Document table");
		
		messageLabel = new JLabel();
		messageLabel.setName("messageLabel");
		messageLabel.setText("Insert messages here");
		
		//Table that contains information about all of the user's documents
		String[] columnNames = new String[]{"Name", "Last Modified", "Collab"};
		tableModel = new DefaultTableModel(null, columnNames);
		documentTable = new JTable();
		documentTable.setName("documentTable");
		documentTable.setModel(tableModel);
		tableScroll = new JScrollPane(documentTable);
		
		newDocumentButton = new JButton();
		newDocumentButton.setName("newDocumentButton");
		newDocumentButton.setText("New Document");
		
		newDocumentName = new JTextField();
		newDocumentName.setName("newDocumentName");
		
		logoutButton = new JButton("Logout");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Using a group layout to describe the actual layout of the different components of the GUI
		GroupLayout completeLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(completeLayout);
		
		completeLayout.setAutoCreateGaps(true);
        completeLayout.setAutoCreateContainerGaps(true);
        
		completeLayout.setHorizontalGroup(completeLayout
				.createParallelGroup()
				.addGroup(
                		completeLayout.createSequentialGroup()
                			.addComponent(newDocumentName)
                			.addComponent(newDocumentButton)
                			.addComponent(logoutButton)
                		)
                .addGroup(
                		completeLayout.createSequentialGroup()
                			.addComponent(messageLabel)
                		)
                .addGroup(
                		completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                			.addComponent(tableLabel)
                		)
                .addGroup(
                		completeLayout.createSequentialGroup()
                			.addComponent(tableScroll)
                		)               
			);
		
		completeLayout.setVerticalGroup(completeLayout
				.createSequentialGroup()
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            			.addComponent(newDocumentName)
	            			.addComponent(newDocumentButton)
	            			.addComponent(logoutButton)
						)
				.addGroup(
						completeLayout.createSequentialGroup()
							.addComponent(messageLabel)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(tableLabel)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(tableScroll)
						)
			);
		
		// Action listener on the new document text box
		newDocumentName.addActionListener( new ActionListener() {
		    @Override 
		    public void actionPerformed(ActionEvent e){
	            newDocument();
		    }
		});
		
		// Action listener on the new document button
		newDocumentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				newDocument();	
			}
		});
		
		// Action listener on the logout button
		logoutButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e){
				logout();
			}
		});
		
		// Action listener on the document table. Allows the user to choose a document from the table to edit
		documentTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					String docName = (String) tableModel.getValueAt(row, NAME_COLUMN);
					openDocument(docName);
				}
			}
		});

		
		this.pack();
	}
	
	/**
	 * Method that publishes a open document request to the server
	 * @param docName Name of the new document. User-defined
	 */
	void openDocument(String docName) {
		out.println("OPENDOC " + userName + " " + docName);
	}

	/**
	 * Completely rewrites the table with data present in the documentList
	 * @param documentList List of string arrays that contains all data that is to be stored within the table
	 */
	void updateTable(List<String[]> documentList){
	    tableModel.setNumRows(0);
	    for (String[] documentInfo: documentList){
	    	String docName = documentInfo[0];
	    	String docDate = documentInfo[1];
	    	String docCollab = documentInfo[2];
	    	String[] rowData = new String[]{docName, docDate, docCollab};
	    	tableModel.addRow(rowData);
	    }
	}
	
	/**
	 * Adds a row of data to the table
	 * @param documentInfo String array that contains data to be added into the table
	 */
	void addData(String[] documentInfo) {
		String docName = documentInfo[0];
    	String docDate = documentInfo[1];
    	String docCollab = documentInfo[2];
    	String[] rowData = new String[]{docName, docDate, docCollab};
    	tableModel.addRow(rowData);
	}
	
	/**
	 * Sets error message when a create request is sent to the server with an already existing document name 
	 */
	void setDuplicateErrorMessage() {
		setMessage("Document name already exists. Duplicates not allowed.");
	}
	
	/**
	 * Sets error message when a create request is sent to the server with an empty string document name 
	 */
	void setEmptyErrorMessage() {
		setMessage("Document name cannot be an empty string");
	}
	
	/**
	 * Method to update message on the client
	 * @param message Message to be displayed on the client
	 */
	private void setMessage(String message) {
		messageLabel.setText(message);
	}
	
	/**
	 * Method that publishes a request to the server to log the current user out
	 */
	private void logout() {
		out.println("LOGOUT " + userName);
	}

	/**
	 * Method that publishes a request to the server to create a new document
	 * with the user-entered name
	 */
	private void newDocument(){
		String docName = newDocumentName.getText();
		if(docName.equals("")){
			setEmptyErrorMessage();
			return;
		}
		// setMessage("");
		out.println("NEWDOC " + userName + " " + docName);
	}
	
	/**
	 * Sets up a new DocTable GUI element. For testing purposes alone
	 * @param args Unused
	 */
	public static void main(String[] args){
	    DocTable main = new DocTable(new PrintWriter(System.out), "victor");
	    main.setVisible(true);
	}
}
