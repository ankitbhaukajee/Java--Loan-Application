package project4;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class loanFrame extends JFrame{
	
	private JRadioButton simple;
	private JRadioButton amortized;
	private JLabel name,principal,length, interest;
	private JTextField nameInput,principalInput,lengthInput;
	private JTextArea summary;
	private ActionListener listener;
	private JComboBox combo;
	private JButton calculate,clear,add,search,edit,update,delete,viewSummary,save;
	private Loan data;
	private ButtonGroup group;
	private LoanManager lm;
	private JPanel summaryPanel,buttons;
	FileWriter writer = null;
	String filename = "LoanDetails.txt";
	private ArrayList<Loan> loans;	
	PrintWriter out;
	
	public loanFrame(LoanManager lm){
		this.lm=lm;
		try
	    {  
			File file = new File(System.getProperty("user.dir")+"/src/"+filename);
			 Scanner inf = new Scanner(file);
			//InputStream in = new FileInputStream(System.getProperty("user.dir")+"/src/"+filename);
			 
			 while (inf.hasNextLine())
			 {	
				 String line = inf.nextLine();
				 String[] tokens = line.split(",");
				 String name=tokens[0];
				 int datainterest=Integer.parseInt(tokens[1]);
				 int datalength=Integer.parseInt(tokens[2]);
				 int principle=Integer.parseInt(tokens[3]);
				 
				 String datatype=tokens[5];
				 if(datatype.equals("Simple Interest Loan"))
				 {
					 lm.addSimpleLoan(name, datainterest, datalength, principle);
				 }
				 else
				 {
					 lm.addAmortizedLoan(name, datainterest, datalength, principle);
				 }
			 }
			 inf.close();
			 
			 
	    }
		catch (IOException exception)
		{
			//
		}
		
		
		listener=new ButtonListener();
		
		createControlPanel();
		setSize(500,500);		
		
		
	}
	
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			
			SwingUtilities.invokeLater(new Runnable() {

	            @Override
	            public void run() {
	                
	                nameInput.grabFocus();
	                //nameInput.requestFocus();
	                
	                
	            }
	        });
			
			
			if(event.getSource()== calculate){
				if(!nameInput.getText().equals("") && !principalInput.getText().equals("") &&  !lengthInput.getText().equals("") )
				{
					String name=nameInput.getText();
					Integer principal=Integer.parseInt(principalInput.getText());
					Integer length=Integer.parseInt(lengthInput.getText());
					Integer rate= Integer.parseInt(combo.getSelectedItem().toString());
					
				
				
					if (simple.isSelected()){
						SimpleLoan sl=new SimpleLoan(name,rate,length,principal);
						sl.process();
						double s=sl.getMonthlyPayment();
						//output=new JLabel(s);
						summary.setText("Total Monthly Payment: "+Double.toString(s));
							
					}
					else{
						AmortizedLoan al=new AmortizedLoan(name,rate,length,principal);
						al.process();
						double s=al.getMonthlyPayment();
						summary.setText("Total Monthly Payment: "+Double.toString(s));
					}	
				}
			
				else
				{
					JOptionPane.showMessageDialog(null, "Some Fields are missing");				   
				}
			}
			else if (event.getSource() == clear) {
				
				nameInput.setEnabled(true);
				nameInput.setText("");
				principalInput.setText("");
				lengthInput.setText("");
				//summary.setText("");
				principalInput.setEnabled(true);
				lengthInput.setEnabled(true);
				calculate.setEnabled(true);
				add.setEnabled(true);
				simple.setEnabled(true);
				amortized.setEnabled(true);
				combo.setEnabled(true);
				update.setEnabled(false);
				search.setEnabled(true);
				
			}

			else if (event.getSource() == add) {
				
				if(!nameInput.getText().equals("") && !principalInput.getText().equals("") &&  !lengthInput.getText().equals("") && group.getSelection()!=null ){
					try{
						String name=nameInput.getText();
						if(lm.search(name)==null)
						{
							Integer principal=Integer.parseInt(principalInput.getText());
							Integer length=Integer.parseInt(lengthInput.getText());
							Integer rate= Integer.parseInt(combo.getSelectedItem().toString());
						
						
						
							if(principal>0 && length>0 )
							{
							
								if (simple.isSelected()){
									
									lm.addSimpleLoan(name, rate, length, principal);
									
									
										
								}
								else{
									lm.addAmortizedLoan(name, rate, length, principal);
									 
									
								}
								summary.setText(lm.summaryLoans());
			
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "The principal or length should be positive number");		
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Cannot make duplicate entry");
						}
						
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "The principal or length should be a number");	
					}
					
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Some Fields are missing");				   
				}
				nameInput.setText("");
				principalInput.setText("");
				lengthInput.setText("");
				principalInput.setEnabled(true);
				lengthInput.setEnabled(true);//				calculate.setEnabled(true);
				add.setEnabled(true);
				simple.setEnabled(true);
				amortized.setEnabled(true);
				combo.setEnabled(true);
				save.setEnabled(true);
			}
			
			else if (event.getSource() == search) {
				
				String name = new String(nameInput.getText());
				
				
				if(name.length() == 0){
					JOptionPane.showMessageDialog(null, "Insert Name to Search");		
					nameInput.setText("");
					principalInput.setText("");
					lengthInput.setText("");
					simple.setEnabled(true);
					amortized.setEnabled(true);
					combo.setEnabled(true);
						
				}
				else
				{
					if(lm.search(name)==null)
					{
						JOptionPane.showMessageDialog(null, "No Such Record");		
						nameInput.setText("");
						principalInput.setText("");
						lengthInput.setText("");
						simple.setEnabled(true);
						amortized.setEnabled(true);
						combo.setEnabled(true);
						summary.setText("");
					}
					else
					{
					
						data=lm.search(name);
						nameInput.setText(name);
						principalInput.setText(Integer.toString(data.getPrinciple()));
						combo.setSelectedItem(Integer.toString(data.getDisplayRates()));
						lengthInput.setText(Integer.toString(data.getDisplayLength()));
						
						data.process();
						
						summary.setText("Monthly Payment: "+Double.toString(data.getMonthlyPayment()));
						if(data.toString().equals("Simple Interest Loan"))
						{
							simple.setSelected(true);
						}
						else
						{
							amortized.setSelected(true);
						}
						
						principalInput.setEnabled(false);
						lengthInput.setEnabled(false);
						calculate.setEnabled(false);
						add.setEnabled(false);
						simple.setEnabled(false);
						amortized.setEnabled(false);
						combo.setEnabled(false);
						edit.setEnabled(true);
						delete.setEnabled(true);
					}
						
				

							
				}
				
				
			}
			
			
			
			else if (event.getSource() == edit) {

				SwingUtilities.invokeLater(new Runnable() {

		            @Override
		            public void run() {
		                
		            	principalInput.grabFocus();
		            	principalInput.selectAll();
		            	lengthInput.selectAll();
		                
		                
		            }
		        });
				nameInput.setEnabled(false);
				principalInput.setEnabled(true);
				lengthInput.setEnabled(true);
				calculate.setEnabled(true);
				add.setEnabled(false);
				simple.setEnabled(true);
				amortized.setEnabled(true);
				combo.setEnabled(true);
				edit.setEnabled(false);
				search.setEnabled(false);
				clear.setEnabled(true);
				update.setEnabled(true);
							
				
			}
			
			else if (event.getSource() == update) {
				
				
				if(!nameInput.getText().equals("") && !principalInput.getText().equals("") &&  !lengthInput.getText().equals("") && group.getSelection()!=null ){
					try{
						
						String name=nameInput.getText();
						Integer principal=Integer.parseInt(principalInput.getText());
						Integer length=Integer.parseInt(lengthInput.getText());
						Integer rate= Integer.parseInt(combo.getSelectedItem().toString());
						lm.remover(data);
					
					
						if(principal>0 && length>0 )
						{
						
							if (simple.isSelected()){
								
								lm.addSimpleLoan(name, rate, length, principal);
								
								
									
							}
							else{
								lm.addAmortizedLoan(name, rate, length, principal);
								 
								
							}
							summary.setText(lm.summaryLoans());
							JOptionPane.showMessageDialog(null, "Database Updated");	
							nameInput.setText("");
							principalInput.setText("");
							lengthInput.setText("");
							principalInput.setEnabled(true);
							lengthInput.setEnabled(true);//				calculate.setEnabled(true);
							add.setEnabled(true);
							simple.setEnabled(true);
							amortized.setEnabled(true);
							combo.setEnabled(true);
							search.setEnabled(true);
							update.setEnabled(false);
							delete.setEnabled(false);
							nameInput.setEnabled(true);
							save.setEnabled(true);
		
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The principal or length should be positive number");		
						}
						
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "The principal or length should be a number");	
					}
					
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Some Fields are missing");				   
				}
					
			}
			
			else if (event.getSource() == delete) {
				
				String name=nameInput.getText();
				lm.remover(data);
				
				nameInput.setText("");
				principalInput.setText("");
				lengthInput.setText("");
				summary.setText(lm.summaryLoans());
				principalInput.setEnabled(true);
				lengthInput.setEnabled(true);
				calculate.setEnabled(true);
				add.setEnabled(true);
				simple.setEnabled(true);
				amortized.setEnabled(true);
				combo.setEnabled(true);
				edit.setEnabled(false);
				search.setEnabled(true);
				clear.setEnabled(true);
				update.setEnabled(false);
				nameInput.setEnabled(true);
				save.setEnabled(true);
				JOptionPane.showMessageDialog(null, name+"'s record deleted from database");		
			    
			    
			}
			
			
			
			else if (event.getSource() == viewSummary) {
				lm.summary();
				int totalNumberLoans=lm.totalLoans;
				int simpleLoans=lm.simpleLoanCounter;
				int amortizedLoans=lm.amortizedLoanCounter;
				int totalLoanAmount=lm.totalLoanAmount;
				summary.setText("Total Number of Loans: "+totalNumberLoans+"\nTotal Number of Simple Loan: "+simpleLoans+"\nTotal Number of Amortized Loans: "+amortizedLoans+"\nTotal Loan Amounts: "+totalLoanAmount);	
			}
			
			else if(event.getSource()== save)
			{
				try
			    {  
					File file = new File(System.getProperty("user.dir")+"/src/"+filename);
				
					file.delete();
		    			
			    }
				catch(Exception e)
				{
					
				}
				loans=lm.arrange();
				try
				{
					writer = new FileWriter(System.getProperty("user.dir")+"/src/"+filename,true);
					for(Loan l: loans)
					{
						String name=l.getName();
						int interestRate=l.getDisplayRates();
						int length=l.getDisplayLength();
						int principle=l.getPrinciple();
						double monthlyPayment=l.getMonthlyPayment();
						String type=l.toString();
						String summary=name+","+interestRate+","+length+","+principle+","+monthlyPayment+","+type;
						writer.write(summary+ System.getProperty("line.separator"));
						
					}
					writer.close();
					summary.setText("Data Saved to "+filename);
				}
				catch (IOException exception) {
					summary.setText("Error in Saving");
				}
				
				
				
			}
			
			
			
		}
	}
	
	
//	public void setVisible(boolean value) {
//	    super.setVisible(value);
//	    nameInput.requestFocusInWindow();
//	}
	
	public void createControlPanel()
	{
		JPanel radioButtons= createRadioButton();
		JPanel details=createDetails();
		JPanel nameDetails=nameDetails();
		JPanel buttons=createButtons();
		
		
		
		//creating summary panel
		summaryPanel=new JPanel();
		summary=new JTextArea(10,40);
		summary.setEditable(false);
		JScrollPane scroll = new JScrollPane(summary);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		//summaryPanel.add(summary);
		summaryPanel.add(scroll);
		
		
		JPanel controlPanel=new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(radioButtons);
		controlPanel.add(nameDetails);
		controlPanel.add(details);
		
		
		controlPanel.add(buttons);
		controlPanel.add(summaryPanel);
		//controlPanel.add(scrollPane);
		
		add(controlPanel);
	}
	public JPanel createRadioButton(){
		JLabel type=new JLabel("Type");
		simple=new JRadioButton("Simple Loan");
		simple.addActionListener(listener);
		simple.setActionCommand("Simple Loan");
		
		amortized= new JRadioButton("Amortized Loan");
		amortized.addActionListener(listener);
		amortized.setActionCommand("Amortized Loan");
		
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		//creating button group to act as a single group
		//which creates mutually exclusive event
		group=new ButtonGroup();
		group.add(simple);
		group.add(amortized);
		panel.add(type);
		panel.add(simple);
		panel.add(amortized);
		
		return panel;
	}
	public JPanel createDetails(){
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(4, 3));
		principal=new JLabel("Principal");
		principalInput=new JTextField(10);
		principalInput.addActionListener(listener);
		length=new JLabel("Length (in years)");
		lengthInput=new JTextField(10);
		lengthInput.addActionListener(listener);
		
		interest = new JLabel("Interest Rate (in %)");
		//int[] interestArray={3,4,5,6,7,8};
		combo=new JComboBox();
		combo.addItem("3");
		combo.addItem("4");
		combo.addItem("5");
		combo.addItem("6");
		combo.addItem("7");
		combo.addItem("8");
		combo.addActionListener(listener);
		
		
		panel.add(principal);
		panel.add(principalInput);
		panel.add(length);
		panel.add(lengthInput);
		panel.add(interest);
		panel.add(combo);
		
		return panel;
	}
	
	public JPanel nameDetails(){
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		name=new JLabel("Borrower's Name");
		nameInput=new JTextField(10);
//		{
//			public void addNotify() {
//	            super.addNotify();
//	            requestFocus();
//			}
//		};
		nameInput.addActionListener(listener);
		search=new JButton("Search");
		search.addActionListener(listener);
		
		
		panel.add(name);
		panel.add(nameInput);
		panel.add(search);
		
		return panel;
	}
	
	public JPanel createButtons(){
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		//creating button in panel
		calculate=new JButton("Calculate");
		calculate.addActionListener(listener);
				
		clear=new JButton("Clear All");
		clear.addActionListener(listener);
		
		add=new JButton("Add");
		add.addActionListener(listener);
		
		
		
		edit=new JButton("Edit");
		edit.addActionListener(listener);
		edit.setEnabled(false);
		
		update=new JButton("Update");
		update.addActionListener(listener);
		update.setEnabled(false);
		
		delete=new JButton("Delete");
		delete.addActionListener(listener);
		delete.setEnabled(false);
		
		viewSummary=new JButton("View Summary");
		viewSummary.addActionListener(listener);
		viewSummary.setEnabled(true);
		
		save=new JButton("Save");
		save.addActionListener(listener);
		save.setEnabled(false);
		
		panel.add(calculate);
		panel.add(clear);
		panel.add(add);
		panel.add(edit);
		panel.add(update);
		panel.add(delete);
		panel.add(viewSummary);
		panel.add(save);
		return panel;
	}

}
