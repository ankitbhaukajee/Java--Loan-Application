package project4;

public class AmortizedLoan extends Loan {
	public AmortizedLoan(String name, double rate, int years, int amount) {
			super(name, rate, years, amount);
			// TODO Auto-generated constructor stub
		}
	public void calcMonthPayment () {
		// calculate the monthly payment using the appropriate formula
		// assign the result to the data field monthlyPayment
		double n=Math.pow(1 + interestRate, length);
		monthlyPayment=( principle * interestRate * n)/(n - 1);
	}
	public String toString() {
		return "Amortized Loan";
	}
}
