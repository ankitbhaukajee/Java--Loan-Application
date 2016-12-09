package project4;

import javax.swing.JFrame;

public class test {
	public static void main(String []args){
		LoanManager lm=new LoanManager();
		loanFrame loan=new loanFrame(lm);
		
		loan.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		loan.setVisible(true);
		

	}
}
