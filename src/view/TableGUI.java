package view;

import java.io.PrintWriter;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TableGUI extends JFrame{
	
	private JLabel tableLabel;
	
	private JTable documentTable;
	private DefaultTableModel tableModel;
	private JButton newDocumentButton;
	
	private PrintWriter out;
	
	public TableGUI (PrintWriter outputStream) {
		super("Etherpad");
		
		out = outputStream;
		
		tableLabel = new JLabel();
		tableLabel.setName("tableLabel");
		tableLabel.setText("Document table");
		
		//Table model that contains data within the Document table
		tableModel = new DefaultTableModel();
		tableModel.addColumn("DocumentName");
		tableModel.addColumn("LastDateModified");
		tableModel.addColumn("Collaborators");
		
		//Table that contains all of the user's documents
		documentTable = new JTable(tableModel);
		documentTable.setName("documentTable");
		
		newDocumentButton = new JButton();
		newDocumentButton.setName("newDocumentButton");
		newDocumentButton.setText("New Document");
		
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
                		.addComponent(tableLabel)
                		)
                .addGroup(
                		completeLayout.createSequentialGroup()
                		.addComponent(newDocumentButton)
                		)
                .addGroup(
                		completeLayout.createSequentialGroup()
                		.addComponent(documentTable)
                		)               
			);
		
		completeLayout.setVerticalGroup(completeLayout
				.createSequentialGroup()
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(tableLabel)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(newDocumentButton)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(documentTable)
						)
			);
		
		this.pack();
	}
	
	public static void main(String[] args){
	    TableGUI main = new TableGUI(new PrintWriter(System.out));
	    main.setVisible(true);
	}
}
