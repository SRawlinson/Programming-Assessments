import java.util.InputMismatchException;
import java.util.Scanner;
/*
 * All the methods used for acquiring input is stored in this class. Each numeric value is acquired as a double and
 * multiplied by 100 for calculation purposes. 
 */

public class CustInterface {
	Scanner customerInput = new Scanner(System.in);
	Scanner wineInput = new Scanner(System.in);

	
	public String customerName() {		
		System.out.println("Please enter your name");		
		return customerInput.nextLine();
	}
	
	public int customerBalance() {
		System.out.println("Please enter your balance:");
		while(true) {
			try {
				double a = customerInput.nextDouble();
				return (int) (a*100);
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid number:");
				customerInput.nextLine();
			}
		}
	
	}
	
	public String wineName() {
		System.out.println("Please enter the title "
				+ "of the wine you'd like to purchase or press return for the list of transactions. ");		
		return wineInput.nextLine();
		//Printed statement asks for input but also alerts the user about how to exit the program. 
	}
	
	public int pricePerUnit() {
		System.out.println("Please enter the price per unit of the wine:");
		while(true) {
			try {
				double a = customerInput.nextDouble();
				return (int) (a*100);
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid number:");
				customerInput.nextLine();
			}
		}
	}
	
	public int numberOfUnits() {
		System.out.println("Please enter the number of bottles you would like to purchase " +
				"(Please enter the negative number for returns, eg: Returning 5 bottles = '-5'):");
		while(true) {
			try {
				return (customerInput.nextInt());
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid number:");
				customerInput.nextLine();
			}
		}
				/*This method is where the difference between sales and returns  is logged - with how it affects
				 * pricing declared in the 'Wine.setPrice()' method.
				 */
	}
}

