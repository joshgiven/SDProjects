package oocashregister;

import java.util.Scanner;

public class PointOfSale {
	private CashRegister register;
	
	public PointOfSale() {
		this.register = new CashRegister();
	}
		
	public static void main(String[] args) {
		PointOfSale pos = new PointOfSale();
		pos.processTransactions();
	}
	
	public void processTransactions() {
		String   currencyFormat = USCurrencyFormat;
		String[] moneyNameTable = moneyNameTableUS;
		String   cSign = "$";

		int price, payment, change;
		do {
			price = getUserInput("What is the price of the item (" + cSign + ") (enter 0 to exit)? ");
			if(price == 0)
				break;
			
			payment = getUserInput("How much money was tendered (" + cSign + ")? ");

			change = payment - price;
			
			int[] handfulOfChange = register.performTransaction(payment, price);		

			if(handfulOfChange == null) {
				if(change > 0)
					System.out.println("ERROR: I need to call my manager.");
				else
					System.out.println("ERROR: Insufficent funds.");
			}
			else if(isEmptyHandful(handfulOfChange)) {
				System.out.println("Exact change. Transaction complete.");
			}
			else {
				System.out.print(formatMoney(price, currencyFormat));
				System.out.print(" out of " + formatMoney(payment, currencyFormat));
				System.out.println(" makes "  + formatMoney(change, currencyFormat));
	
				System.out.print("Return the following change: ");
				displayChange(handfulOfChange, moneyNameTable);
			}
			
			System.out.println("\nNext transaction: ");
		} while(true);
		
		System.out.println("Goodbye.");

	}
	
	private boolean isEmptyHandful(int[] handful) {
		int sum = 0;
		for(int i : handful) {
			sum += i;
		}
		
		return (sum == 0);
	}
	
	private static String formatMoney(int cents, String format) {
		return String.format(format, (cents / 100), Math.abs(cents % 100));
	}
	
	private static void displayChange(int[] moneyUnits, String[] moneyNameTable) {
		String str = "";
		boolean printComma = false;
		
		for(int i = 0; i < moneyUnits.length; i++) {
			int nUnits      = moneyUnits[i];
			String unitName = moneyNameTable[i];
			
			if(nUnits > 0) {
				if(printComma) {
					str += ", ";
				}
				else {
					printComma = true;
				}
				
				str += nUnits + " " + unitName;
				if(nUnits > 1) {
					if(str.charAt(str.length()-1) == 'y') {
						str = str.substring(0, str.length()-1) + "ie";
					}
					str += "s";
				}					
			}
		}
		System.out.println(str);
	}

	private static int getUserInput(String prompt) {
		double entry;
		Scanner kb = new Scanner(System.in);
		
		do {
			System.out.print(prompt);
			if(kb.hasNextDouble()) {
				entry = kb.nextDouble();
				if(entry >= 0) {
					break;
				}
			}
			else {
				kb.next();  // discard the next String
			}
			System.out.println("bad val. try again.");
		} while(true);		
		
		// kb.close();  // Scanner.close() also closes System.in
		
		return (int)Math.round(entry*100) ;
	}

	static final int[] moneyValTableUS = 
		{ 100_00, 50_00, 20_00, 10_00, 5_00,1_00,
			25, 10, 5, 1 };

	static final String[] moneyNameTableUS = 
		{ "hundred-dollar bill", "fifty-dollar bill", "twenty-dollar bill", 
				"ten-dollar bill", "five-dollar bill", "one-dollar bill",
				"quarter", "dime", "nickel", "penny" };
	
	static final String USCurrencyFormat = "$%d.%02d"; 
	static final String USBillFormat = "$%d"; 
	static final String USCoinFormat = "%d\u00a2"; 
	static final String[] moneyFormatTableUS = 
		{ USBillFormat, USBillFormat, USBillFormat, USBillFormat, USBillFormat, USBillFormat,
			USCoinFormat, USCoinFormat, USCoinFormat, USCoinFormat };
	

}
