package project4;

public abstract class Loan implements Comparable<Loan>{
	protected String name; // the applicant’s name
	protected double interestRate; // the annual interest rate
	protected int length; // the length of the load in years
	protected int principle; // the principle
	protected double monthlyPayment; // the monthly payment
	protected int displayRates;
	

	protected int displayLength;
	
	public Loan (String name, double rate, int years, int amount ){
	// constructor
		this.name=name;
		interestRate=rate/1200;
		length=years*12;
		principle=amount;
		displayRates=(int)rate;
		displayLength=years;
		
	}
	public String process () {
	// call method calcMonthlyPayment()
	// call method makeSummary()
	// return the summary
		calcMonthPayment();
		return makeSummary();
		
	}
	
	abstract public void calcMonthPayment(); // an abstract method
	
	public String makeSummary () {
	// make and return a summary on the loan
		String summary="Name: "+name+"\nPrincipal: "+principle+"\nInterest Rate: "+displayRates+"\nLength of Loan: "+displayLength+"\nPayment: "+monthlyPayment;
		return summary;
	}
	
	public String getName() {
		return name;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getPrinciple() {
		return principle;
	}
	
	public double getMonthlyPayment() {
		return monthlyPayment;
	}
	
	public int getDisplayRates() {
		return displayRates;
	}
	
	public int getDisplayLength() {
		return displayLength;
	}
	public String toString() { 
		return "Loan"; 
	}
	
	 public int compareTo(Loan other) {
		 
	        /* For Ascending order*/
	        return this.name.compareTo(other.getName());
	        
	    }

	
}