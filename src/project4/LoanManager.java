package project4;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class LoanManager {
	int simpleLoanCounter=0;
	int amortizedLoanCounter=0;
	int totalLoans=0;
	int totalLoanAmount=0;
	private ArrayList<Loan> loans;
	// add instance variables as needed
	public LoanManager () {
		loans=new ArrayList<Loan>();
		
	}
	public void addSimpleLoan(String name, int rate, int years, int amount){
		loans.add(new SimpleLoan(name,rate,years,amount));

	}
	
	public void addAmortizedLoan(String name, int rate, int years, int amount){
		loans.add(new AmortizedLoan(name,rate,years,amount));

	}
	public String summaryLoans()
	{
		String data="";
		for(Loan l: loans)
		{

			data+=l.process()+"\n\n";
		}
		return data;
	}
	
	public Loan search(String name)//returns loan object or null
	{

		for(Loan l: loans)
		{
			
			if(l.getName().equals(name))
			{
				
				return l;
			}
		}
		return null;
	}
	public void summary()
	{
		for(Loan l: loans)
		{
			if(l instanceof SimpleLoan)
			{
				
				simpleLoanCounter++;
			}
			else
			{
				
				amortizedLoanCounter++;
			}
			totalLoanAmount+=l.getPrinciple();
			totalLoans++;
			
		}
		
	}
	public ArrayList<Loan> arrange()
	{
		Collections.sort(loans);
		
		return loans;
	}

	
	public void remover(Loan name)
	{
		Iterator<Loan> iter = loans.iterator();
		while (iter.hasNext()) 	
		
		{
			Loan user = iter.next();
			if(user==name)
			{
			
				iter.remove();
				
			}
		}
	}
	
	public String toString () {
		return "Loans";
	}
}