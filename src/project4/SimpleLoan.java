package project4;

public class SimpleLoan extends Loan {
	
	public SimpleLoan(String name, double rate, int years, int amount) {
		super(name, rate, years, amount);
		
	}
	public void calcMonthPayment () {
	// calculate the monthly payment using the appropriate formula
	// assign the result to the data field monthlyPayment
		monthlyPayment=( principle * (interestRate * length + 1)) / length; 
	}
	public String toString() { 
		return "Simple Interest Loan"; 
	}
}
