package oocashregister;

public class Drawer {
	final private static int[] denomValues = { 
			100_00, 50_00, 20_00, 10_00, 
			5_00,1_00,25,10, 5, 1 };
	
	private int[] denominations;
	
	public Drawer() {
		denominations = new int[denomValues.length];
	}

	public Drawer(int[] initDenomCounts) {
		this();
		
		if(initDenomCounts != null && initDenomCounts.length == denomValues.length) {
			for(int i=0; i < initDenomCounts.length; i++) {
				denominations[i] = initDenomCounts[i];
			}
		}
	}
	
	public void display() {
		for(int i : denomValues) {
			System.out.printf("\t%.02f", ((double)i /100));
		}
		System.out.println();
		for(int i : denominations) {
			System.out.print("\t" + i);
		}
		System.out.println();
		System.out.println();
	}
	
	public void addDenomination(int index, int count) {
		denominations[index] += count;
	}
	
	public void removeDenomination(int index, int count) {
		denominations[index] -= count;
	}
	
	public int getNumDenomination(int index) {
		return denominations[index];
	}
	
	static public int[] getDenominations() {
		return denomValues;
	}
	
	/*
	final static int HUNDRED_INDEX = 0;
	final static int FIFTY_INDEX = 1;
	final static int TWENTY_INDEX = 2;
	final static int TEN_INDEX = 3;
	final static int FIVE_INDEX = 4;
	final static int ONE_INDEX = 5;
	final static int QUARTER_INDEX = 6;
	final static int DIME_INDEX = 7;
	final static int NICKEL_INDEX = 8;
	final static int PENNY_INDEX = 9;
	 */
}
