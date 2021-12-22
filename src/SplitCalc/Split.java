package SplitCalc;
import java.util.*;
//import java.util.Scanner;

public class Split{
	private Stack <Distance> split = new Stack<Distance>();
	public static Stack<Double> undo = new Stack<>();
	private double sumMile = 0, initMile = 0,firstSplit = 0, middleSplit = 0, lastSplit = 0; 
	private int counter = 0;


	/*	default constructor will be automatically provided */
	
	public void addSplit(Distance d) {
		this.split.add(d);
	}
	
	
	public boolean addSplitMile() {
		double mile = 0;
		for (Distance j: split) {
			mile = j.getSplit();
			undo.add(mile);
		// addSplitMile is executed in Manager class only when when o is selected
		} sumMile += mile;
		if(sumMile <= initMile) {
			return true;
		}
		return false;
	}



	// output split and pace
	public void outSplit() {
	// save split and pace on ArrayList for further formatting (commas, and period)
	ArrayList<Double> _getSplit = new ArrayList<>(), _getPace = new ArrayList<>();
	double sum = 0;
	for (Distance j: split) {
		_getSplit.add(j.getSplit());
		_getPace.add(j.getPace());
		}
	// first
	System.out.println("0.0 --- " + String.format("%.2f", _getSplit.get(1))+ "\t*"
		+_getSplit.get(1)+ " @"+_getPace.get(0)+"Mph");
	// middle
	for(int i=1; i<_getSplit.size()-1;++i) {
		if(split.size()>2 && i!=_getSplit.size()-1) {
			sum += _getSplit.get(i);
			System.out.println(sum+ " --- "
				+ String.format("%.2f", sum+_getSplit.get(i+1)) + "\t "
				+_getSplit.get(i+1) +" @"+_getPace.get(i) +"Mph");
			}
		}
	// last
	calcSplit();
	System.out.println(String.format("%.2f", sumMile) + " --- End\t "
		+String.format("%.1f", 1-sumMile)+ " @"+ _getPace.get(split.size()-1) +"Mph");
	}
	
	
	// for counting after adding split
	public int counter() {
		return counter++;
	}
	
	
	// Calculating subsequent split entries from user
	public double calcSplit() {
		firstSplit = 0;		// reset
		middleSplit = 0;	// reset
		lastSplit = 0;		// reset

		// updates totalMile, getSplit, and getPace
		ArrayList<Double> _getSplit = new ArrayList<>() ,_getPace = new ArrayList<>();
		for (Distance j: split) {
			_getSplit.add(j.getSplit());
			_getPace.add(j.getPace());
			}
		initMile = _getSplit.get(0);
		
		double Pace = populateConversion(_getPace.get(0));	// getPace() to value in seconds
		
		if(split.size()==1) {
			return initMile*Pace;	// e.g. 1mile * 12 minutes, 0 seconds
			}
		else {
		// *****FIRST SPLIT IN RACE***** //
			firstSplit = (_getSplit.get(1))*Pace;
		//	System.out.println("CHECK - firstSplit is " + firstSplit);	// choose [0.2, 6] = 144.0
			
		// *****MIDDLE SPLIT IN RACE***** //
			for(int i=0; i<split.size(); ++i) {	// loop from 'second?' entry to 'second-to-last'
				if(i > 1) {	// 0 is initMile, 1 goes to First Split, size()-1 goes to Last Split 
					middleSplit += _getSplit.get(i)*(populateConversion(_getPace.get(i-1)));
					// _getSplit.get() is MPH from user, multiplied by corresponding Pace.
		//	System.out.println("CHECK - middleSplit is " + middleSplit);		// choose [0.3, 6.5] = 180.0, [0.2, 7] = 110.6
				}
			}
		// *****LAST SPLIT IN RACE***** //
			Pace = populateConversion(_getPace.get(counter)); //counter is the last entry
			lastSplit = (initMile-sumMile) * Pace;	// <------ LAST SPLIT IN RACE
		//	System.out.println("CHECK - lastSplit is " + lastSplit);
			
			return firstSplit + middleSplit + lastSplit;
			}
	}
	
	
	// convert MPH to Pace
	private int populateConversion(double k) {
		return (int)((((60/k) - ((60/k)%1))*60) + ((60/k%1)*60));	// minute + second
		}
	
	// prints out "M minute, S seconds"
	private void convertToString(Double t) {
		System.out.print(t.intValue()/60+" minutes, "+(t.intValue()-(t.intValue()/60)*60)+" seconds");
	}
	
	// Average MPH
	public void average() {
		System.out.println("AVERAGE MPH: " + String.format("%.1f", 60*60/calcSplit()));
	}
	
	// Undo
	public void undo() {
		if(split.size()>1) {
			split.pop();
			counter--;
			sumMile -= undo.pop();
			System.out.println("** UNDO SUCCESSFUL! **");
			}
	}
	
	public void output() {	
		if (counter == 0) {
			calcSplit();
			System.out.println("Splits: " + counter);
			System.out.println("TOTAL DISTANCE: " + initMile + " Miles(s)");
			System.out.print("TOTAL TIME: ");
			convertToString(calcSplit());	// to reformat tPace to String.
			}
		else {
			calcSplit();
			System.out.println("TOTAL DISTANCE: " + initMile + " Mile(s)");
			System.out.print("TOTAL TIME: ");
			convertToString(calcSplit());
			
			System.out.print("\nSplits: "+counter);
			System.out.print("     Split left is: ");
			System.out.printf("%.2f", initMile-sumMile);
			System.out.println();
			System.out.println("     *** BREAKDOWN ***");

			outSplit();
			}
		}	
}
