
public class AssEx1 {
	public static void main(String[] args) {
//Part One: Collecting input to create a Customer object. 	
		//Initialising a CustInterface in order to call its methods to create a CustAccount object. 	
		CustInterface newInterface = new CustInterface();		
		
		CustomerAccount customer = new CustomerAccount(newInterface.customerName(), newInterface.customerBalance());
		//Printing a statement confirming the data entered:		
		System.out.println(customer.custOutput);
			
		
//Part two: Logging each instance of a sale or return and correcting the balance accordingly. 
		int numberOfWines = 0;		
		boolean moreWine = true;
		//While loop starts to catch if the user wants to exit the program. 
		while (moreWine) {
			String title = newInterface.wineName();	
			if(title.equals("")) {
				moreWine = false;
				//exits 'while' loop if customer enters blank line at title stage
			} else {
				//Asks for the rest of the input to create the next wine object if the user wishes to continue
				Wine newWine = new Wine(title, newInterface.pricePerUnit(), newInterface.numberOfUnits());
			
			//updateBalance calculates the customer's new balance based on the total price of the wine transaction	
			customer.updateBalance(newWine.setPrice());
			//numberOfWines isn't strictly necessary and just keeps track of the number of transactions for the final output
			numberOfWines++;
			//This adds the details of each transaction and the new customer account balance to the final output
			customer.line = String.format(customer.line + newWine + 
					"New Balance: £" + "%.2f" +"\n" + "\n", customer.displayBalance());
			//This provides the user with a running total of their balance based on the last transaction
			String wineOutput = String.format("Thank you. After purchasing " + newWine.getNumberOfUnits() +
					 " bottles of " + newWine.getTitle() + 
					 ", your new balance is: £%.2f", customer.displayBalance());
			
			System.out.println(wineOutput);
			}
		}
		
//Part Three: Exporting final file for the user.
		//'line' should have a record of each transaction, so this simply adds some final info for the user.
		customer.line = String.format(customer.line + "\n" + "Number of Sales/Returns: " + numberOfWines + "\n" +
		 "Final Balance: £" + "%.2f", customer.displayBalance());	
		
		//The method for exporting the final file is in the customerAccount class. 
		customer.exportFile(customer.line);		
	}
	
}
