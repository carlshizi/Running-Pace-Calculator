package SplitCalc;

import java.util.*;
import java.util.stream.Stream;

public class Manager{
	public static double sumMile = 0;
	public static Stack<Double> undo = new Stack<>();

	
	// ***** MAIN ***** //
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		char userIn;
		double pace, mile;
		char quitCmd = 'a';

		System.out.println("TREADMILL/RUNNING SPLIT CALCULATOR (v1.4) - 2021\n");
		
		while (quitCmd != 'q') {
			try {
				mile = getMile(scnr);
				pace = getPace(scnr);
				space(1);
				
				Distance distance = new Distance();
				distance.setSplit(mile);
				distance.setPace(pace);

				Split split = new Split(); 		// for subsequent splits
				split.addSplit(distance);
				

				userIn = userMenu(scnr, split);
			} catch (Exception excpt) {System.out.println(excpt.getMessage());
//			System.out.println("Press q to quit OR Enter any other button to continue");
			}
	
			quitCmd = scnr.next().charAt(0);
		}

		return;	
	}
	
	
	// ***** GET USER MILE ***** //
	public static double getMile(Scanner scnr)throws Exception{
		double mile;
		System.out.println("Enter Distance:");
		mile = scnr.nextDouble();
		if (!(mile > 0)) {
			throw new Exception("Distance must be a positive integer\nEnter any button to continue or q to quit");
		}
		return mile;
	}
	

	// ***** GET USER PACE ***** //
	public static double getPace(Scanner scnr)throws Exception{
		double pace;
		System.out.println("Select MPH:");
		pace = scnr.nextDouble();
		if (!(pace >= 3 && pace <= 13)) {
			throw new Exception("Pace must be from 3.0 to 13.0!\nEnter any button to continue or q to quit");
		}
		return pace;
	}
	
	
	// ***** USER MENU ***** //
	public static char userMenu(Scanner scnr, Split split) {
		char ch = ' ';
		
		Stream<String> menu
			= Stream.of("MENU", "a - Add split", "u - Undo split", "o - Output program",
					"v - Average MPH", "q - Quit\n");
		menu.forEach(s -> System.out.println(s));
		
		while (ch != 'a' && ch != 'o' && ch!= 'v' && ch != 'q' && ch != 'u') {
			System.out.println("Choose an option:\n");
			ch = scnr.next().charAt(0);
			}
			scnr.nextLine();
			while (ch != ' ') {
				
				// ADD SPLIT
				if (ch == 'a') {
					System.out.println("Enter split break point:");
					double splitMile = scnr.nextDouble();
					undo.add(splitMile);	// to implement UNDO function
					sumMile += splitMile;
					if (splitMile < 0 || sumMile > 1) {
						undo.pop();
						sumMile -= splitMile;
						System.out.println("** ERROR **\nEither you entered a negative integer OR a "
								+String.format("%.1f", sumMile-1) +" overage occurred!");
						space(1);
						return userMenu(scnr, split);
					}
					
					
					System.out.println("Enter split pace:");
					double pace = scnr.nextDouble();
					if (!(pace >= 3 && pace <= 13)) {
						System.out.println("Pace must be a positive integer!");
						space(1);
						return userMenu(scnr, split);
					}
					Distance distance = new Distance();
					distance.setSplit(splitMile);
					distance.setPace(pace);
					split.addSplit(distance);
					split.addSplitMile();		// for totaling up split right after outSplit() is called.
					
					split.outSplit();
					space(1);
					split.counter();
					space(1);
					return userMenu(scnr, split);
				}
				
				
				// OUTPUT PROGRAM
				else if (ch == 'o') {
//					System.out.println(split.calcSplit() + " seconds");
					split.output();
					space(2);
					return userMenu(scnr, split);
				}
				
				// AVERAGE MPH
				else if (ch == 'v') {
					split.average();
					space(2);
					return userMenu(scnr, split);
				}
				
				// UNDO
				else if (ch == 'u') {
					if(sumMile == 0) {
						System.out.println("** NOTHING TO UNDO **");
						space(1);
						return userMenu(scnr, split);
					}
					split.undo();
					sumMile -= undo.pop();
					space(1);
					return userMenu(scnr, split);
				}
				
				else if (ch == ' ') {
					System.out.println("");
				}
				
				else if(ch == 'q')
					System.exit(0);
			}
			
		return ch;

	}
	
	
	// ***** SPACE ***** //
	public static void space(int x) {
		for (int i = 0; i < x; ++i) {
		System.out.println();
		}
	}
}