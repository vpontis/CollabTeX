package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class DocTable extends JFrame{
	
	private JLabel tableLabel;
	
	private JTable documentTable;
	private DefaultTableModel tableModel;
	private JScrollPane tableScroll;
	
	private JButton newDocumentButton;
	private JTextField newDocumentName;
	
	private JButton logoutButton;
	
	private PrintWriter out;
	
	public DocTable (PrintWriter outputStream) {
		super("Document List");
		
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
		
		this.pack();
	}
	
	private void newDocument(){
		//TODO: Implement
		String name = newDocumentName.getText();
		System.out.println("New document with name " + name + " created!");
	}
	
	public static void main(String[] args){
	    DocTable main = new DocTable(new PrintWriter(System.out));
	    main.setVisible(true);
	}
}
