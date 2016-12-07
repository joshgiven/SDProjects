package oocashregister;

public class CashRegister {
	final static int[] initDenomCounts = {
			0, 0, 10, 10, 10, 20, 100, 100, 100, 100 };

	private Drawer drawer;
	private boolean isVerbose = true;
	
	public CashRegister() {
		// init cash drawer
		drawer = new Drawer(initDenomCounts);
	}
	
	public int[] performTransaction(int payment, int price) {
		//insufficient
		if(price > payment) {
			return null;
		}
		else {
			int[] denomValues = Drawer.getDenominations();
			int[] paymentMoney = makeChange(payment, denomValues);
			int[] returnChange = makeDrawerChange(payment-price, denomValues);
			
			if(returnChange != null) {
				addToDrawer(paymentMoney);
				removeFromDrawer(returnChange);
			}
			
			if(isVerbose)
				drawer.display();
			
			return returnChange;
		}
	}
	
	private void addToDrawer(int[] paymentCounts) {
		for(int i=0; i<paymentCounts.length; i++)
			drawer.addDenomination(i, paymentCounts[i]);
	}
	
	private void removeFromDrawer(int[] moneyCounts) {
		for(int i=0; i<moneyCounts.length; i++)
			drawer.removeDenomination(i, moneyCounts[i]);
	}
	
	private int[] makeDrawerChange(int change, int[] moneyTable) {
		int[] moneyUnits = new int[moneyTable.length];
		
		for(int i = 0; i < moneyTable.length; i++) {
			int denom = moneyTable[i];

			int n = 0;
			while(change >= denom && drawer.getNumDenomination(i) > n) {
				change -= denom;
				n++;
			}
			
			moneyUnits[i] = n;
		}

		if(change > 0) {
			// not enough change in drawer	
			moneyUnits = null;
		}
		
		return moneyUnits;
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
}
