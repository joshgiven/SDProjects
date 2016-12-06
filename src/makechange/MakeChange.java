package makechange;
import java.util.Scanner;

public class MakeChange {
	public static void main(String[] args) {
		String   currencyFormat;
		int[]    moneyValTable;
		String[] moneyFormatTable;
		String[] moneyNameTable;
		String   cSign;
		
		String currency = (args.length > 0) ? args[0] : "USD";
		int outputMode  = (args.length > 1) ? 2 : 1;

		switch(currency.toUpperCase()) {
		case "EUR":
			currencyFormat   = EuroCurrencyFormat;
			moneyValTable    = moneyValTableEuro;
			moneyFormatTable = moneyFormatTableEuro;
			moneyNameTable   = moneyNameTableEuro;
			cSign = "\u20ac";
			break;
			
		case "MXN":
			currencyFormat   = MexCurrencyFormat;
			moneyValTable    = moneyValTableMex;
			moneyFormatTable = moneyFormatTableMex;
			moneyNameTable   = moneyNameTableMex;
			cSign = "MXN$";
			break;
			
		case "USD":
		default:
			currencyFormat   = USCurrencyFormat;
			moneyValTable    = moneyValTableUS;
			moneyFormatTable = moneyFormatTableUS;
			moneyNameTable   = moneyNameTableUS;
			cSign = "$";
			break;
		}
		
		int price, payment, change;
		do {
			price = getUserInput("What is the price of the item (" + cSign + ") (enter 0 to exit)? ");
			if(price == 0)
				break;
			
			payment = getUserInput("How much money was tendered (" + cSign + ")? ");
			change = payment - price;
	
			if(change < 0) {
				System.out.println("ERROR: Insufficent funds.");
			}
			else if(change == 0) {
				System.out.println("Exact change. Transaction complete.");
			}
			else {
				int[] handfulOfChange = makeChange(change, moneyValTable);
	
				System.out.print(formatMoney(price, currencyFormat));
				System.out.print(" out of " + formatMoney(payment, currencyFormat));
				System.out.println(" makes "  + formatMoney(change, currencyFormat));
	
				System.out.print("Return the following change: ");
				if(outputMode == 1) {
					displayChange2(handfulOfChange, moneyNameTable);
				}
				else {
					displayChange(handfulOfChange, moneyValTable, moneyFormatTable);
				}
			}
			
			System.out.println("\nNext transaction: ");
		} while(true);
		
		System.out.println("Goodbye.");
	}
	
	private static int[] makeChange(int change, int[] moneyTable) {
		int[] moneyUnits = new int[moneyTable.length];
		
		for(int i = 0; i < moneyTable.length; i++) {
			int denom = moneyTable[i];
			moneyUnits[i] = change / denom;
			change = change % denom;
		}
		
		return moneyUnits;
	}
	
	private static String formatMoney(int cents, String format) {
		return String.format(format, (cents / 100), Math.abs(cents % 100));
	}
	
	private static String formatUnit(int cents, String format) {
		int val = (cents >= 100) ? cents/100 : cents;
		return String.format(format, val);
	}
	
	private static void displayChange(int[] moneyUnits, int[] moneyTable, String[] moneyFormatTable) {
		String str = "";
		
		for(int i = 0; i < moneyUnits.length; i++) {
			int    denom       = moneyTable[i];
			String denomFormat = moneyFormatTable[i];
			
			if(moneyUnits[i] > 0) {
				if(!str.equals("")) 
					str += ", ";
					
				str += (moneyUnits[i] + " x ");
				if(denom >= 100) {
					str += formatUnit(denom, denomFormat);
				}
				else {
					str += formatUnit(denom, denomFormat);					
				}
			}
		}
		System.out.println(str);
	}
	
	private static void displayChange2(int[] moneyUnits, String[] moneyNameTable) {
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
	
	private static String[] initMoneyNameTable(int[] values, 
			String billFormat, String centFormat) {
		String[] names = new String[values.length];
		String format;
		
		for(int i=0; i < names.length; i++) {
			int val = values[i];
			if(val >= 100) {
				val = val/100;
				format = billFormat;
			}
			else {
				format = centFormat;
			}
			names[i] = String.format(format, val);
		}
		
		return names;
	}

	static final int[] moneyValTableUS = 
		{ 100_00, 50_00, 20_00, 10_00, 5_00,1_00,
			25, 10, 5, 1 };

	//	static final String[] moneyNameTableUS = 
	//		{ "Benjamin", "Grant", "Jackson", "Hamilton", "Fiver", "One",
	//				"quarter", "dime", "nickel", "penny" };
	
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
	
	//	Banknotes 	€5, €10, €20, €50, €100, €200, €500
	//	Coins 	1c, 2c, 5c, 10c, 20c, 50c, €1, €2
	static final int[] moneyValTableEuro = 
		{ 500_00, 100_00, 50_00, 20_00, 10_00, 5_00,
				2_00, 1_00, 50, 20, 10, 5, 2, 1 };
	
	static final String EuroCurrencyFormat = "\u20ac%d.%02d";
	static final String EuroBillFormat = "\u20ac%d";
	static final String EuroCentFormat = "%dc";
	static final String[] moneyFormatTableEuro = 
		{ EuroBillFormat, EuroBillFormat, EuroBillFormat, EuroBillFormat, 
				EuroBillFormat, EuroBillFormat, EuroBillFormat, EuroBillFormat, 
				EuroCentFormat, EuroCentFormat, EuroCentFormat, 
				EuroCentFormat, EuroCentFormat, EuroCentFormat };

	static final String[] moneyNameTableEuro = 
			initMoneyNameTable(moneyValTableEuro, EuroBillFormat, EuroCentFormat);
	
	//	Banknotes 	
	//	 Freq. used 	$20, $50, $100, $200, $500
	//	 Rarely used 	$1000
	//	Coins 	
	//	 Freq. used 	50¢, $1, $2, $5, $10
	//	 Rarely used 	5¢, 10¢, 20¢, $20
	static final int[] moneyValTableMex = 
		{ 500_00, 200_00, 100_00, 50_00,
				10_00, 5_00, 2_00, 1_00, 50,
				20, 10, 5, 1 };

	static final String MexCurrencyFormat = "$%d.%02d"; 
	static final String MexBillFormat = "$%d"; 
	static final String MexCentFormat = "%d\u00a2"; 
	static final String[] moneyFormatTableMex = 
		{ MexBillFormat, MexBillFormat, MexBillFormat, MexBillFormat,
				MexBillFormat, MexBillFormat, MexBillFormat, MexBillFormat, MexCentFormat,
				MexCentFormat, MexCentFormat, MexCentFormat, MexCentFormat };

	static final String[] moneyNameTableMex = 
			initMoneyNameTable(moneyValTableMex, MexBillFormat, MexCentFormat);
}

