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

@SuppressWarnings("serial")
public class DocTable extends JFrame{
	static private final int NAME_COLUMN = 0;
	
	private JLabel tableLabel;
	
	private JTable documentTable;
	private DefaultTableModel tableModel;
	private JScrollPane tableScroll;
	
	private JButton newDocumentButton;
	private JTextField newDocumentName;
	
	private JButton logoutButton;
	
	private PrintWriter out;
	
	private String userName;
	
	public DocTable (PrintWriter outputStream, String userName) {
		super("Document List");
		
		this.userName = userName;
		out = outputStream;
		
		tableLabel = new JLabel();
		tableLabel.setName("tableLabel");
		tableLabel.setText("Document table");
		
		//Table that contains all of the user's documents
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
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(tableLabel)
						)
				.addGroup(
						completeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(tableScroll)
						)
			);
				
		newDocumentName.addActionListener( new ActionListener() {
		    @Override 
		    public void actionPerformed(ActionEvent e){
	            newDocument();
		    }
		});
		
		newDocumentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				newDocument();	
			}
		});
		
		logoutButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e){
				logout();
			}
		});
		
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
	
	void openDocument(String docName) {
		out.println("OPENDOC " + userName + " " + docName);
	}

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
	
	private void logout() {
		out.println("LOGOUT " + userName);
	}

	private void newDocument(){
		String docName = newDocumentName.getText();
		out.println("NEWDOC " + userName + " " + docName);
	}
	
	public static void main(String[] args){
	    DocTable main = new DocTable(new PrintWriter(System.out), "victor");
	    main.setVisible(true);
	}
}
