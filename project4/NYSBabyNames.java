package project5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Collections;

/**
 * This class is where the main method of the program which parses through the csv file. 
 * This method stores the name, county, gender, and count of any particular name that is contained in the file.
 * This method will print out the year, the percent of that particular name, and bars to represent the count.
 * @author Joseph Abraham
 * @version 04/26/2018
 */
public class NYSBabyNames
{
	//ArrayList to store the years and name object to simplify output
	private ArrayList<YearNames> yearnames = new ArrayList<YearNames>(2100);
	
    /**
     * Constructor for objects of class BabyNamesNYS(empty because we have all necessary objects elsewhere).
     */
	public NYSBabyNames()
    {
        
    }

	/**
	 * This method is to add a Yearname object into the yearnames list and does not return anything.
	 * This method also checks to make sure that the same name is not entered more than once in a single year.
	 * In that case, the add method is called which will increment the count but not make a new object.
	 * @param year an integer year needs to be passed that is within the scope of this program which is 1800-2018.
	 * @param name a Name object is passed and used to ensure no repeats and to add any names that are not yet present.
	 */
    private void addYearName(int year, Name name) {
    		for (int i = 0; i < yearnames.size(); i++) {
    			if (yearnames.get(i).getYear() == year) {
    				yearnames.get(i).add(name);
    				return;
    			}
    		}
    		yearnames.add(new YearNames(year, name));
    }
    /**
     * This method is to output the bars that are to represent the different years that any given name appears.
     * @param fraction this double is to be passed and used to find out how many bars are needed
     * @return This returns a string representation of bars are needed to represent a given number of babies which is outlined 
     */
    private String getBars(double fraction) {
    		int numbar = (int) Math.ceil(fraction * 100);
    		//ceil function given to help with formatting
    		String bars = "";
    		for (int i = 0; i < numbar; i++) {
    			bars += "|";
    		}
    		return bars;
    }
    /**
     * This truncates the fraction at 4 decimal places for the output as given in the specs
     * @param fraction given so that it can be formatted in the proper way
     * @return returns a string representation of the double so that in can be printed in the main method
     */
    private String formatFraction(double fraction) {
    		//creating a decimal formatter(same way one would create a scanner)
    		DecimalFormat jk = new DecimalFormat("#.####");
 
    		String fraction2 = jk.format(fraction);
    		return fraction2;
    	}

    /**
     * This method is all for the output of the program. It prints all the years where a 
     * given name is found, the fraction of relative babies , and the specific number of bars to represent the 
     * @param name The method needs name so that it can call getFractionByNameCounty to get all the needed information
     * @param county The method needs county so that it can call getFractionByNameCounty to get all the needed information
     */
    private void plotHistogram(String name, String county) {
    		//runs through all YearName objects
    		for (int i = 0; i < yearnames.size(); i++) {
    			//covers all the counties if that"all" is inputed
    			if (county.equals("all")) {
    				//fraction used in the output
    				double fraction = 100.0*(yearnames.get(i).getFractionByNameCounty(name, county));
    				//count used in debugging
    				int count = yearnames.get(i).getCountByNameCounty(name, county);
//    				System.out.println("DEB: " + yearnames.get(i).getYear() + " (" + formatFraction(fraction) + "): "
//    						+ "(" + count + "/" + yearnames.get(i).getTotal() + ") " 
//    						+ getBars(fraction));
    				//final print statement that gives year, fraction in parenthesis, then the bars
    				System.out.println(yearnames.get(i).getYear() + " (" + formatFraction(fraction) + "): "
    						+ getBars(fraction));
    			}
    			//This is used when it is just one county being inquired about
    			else {
    				//count used in debugging
    				int count = yearnames.get(i).getCountByNameCounty(name, county);
    				//int being used when the babies of one county are being inquired about
    				int totalcountForCounty = yearnames.get(i).getCountByNameCounty("all", county);
    				//fraction used in the output
    				double fraction = 100.0*((double) count / (double) totalcountForCounty) ;
    					//used in debugging
//    				System.out.println("DEB: " + yearnames.get(i).getYear() + " (" + formatFraction(fraction) + "): "
//    						+ "(" + count + "/" + totalcountForCounty + ") " 
//    						+ getBars(fraction));
    				System.out.println(yearnames.get(i).getYear() + " (" + formatFraction(fraction) + "): "
    						+ getBars(fraction));
    				
    			}
    			
    		}
    }

    /**
     * This is the main method which pulls all the different classes together to be able to efficiently search the an AVL for Baby Name information
     * @param args this will be file inputed into the command line argument
     * @throws FileNotFoundException This exception should be thrown when no file is present to look through
     * @throws java.io.IOException This exception will be thrown whenever some problem occurs with the input or output
     */
	public static void main(String[] args) throws FileNotFoundException, java.io.IOException
	{
		
	    if (args.length == 0) 
		{
			System.err.println("Usage Error: the program expects file name as an argument");
			System.exit(1);
		}
	    //Declaring scanner to take in user input
	    Scanner scan = new Scanner(System.in);
	    //Declaring file reader and buffered reader to read file in command line argument
	    FileReader fr = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(fr);
		//Instantiating an NYSBabyName object to access the ArrayList of YearNames kept
		NYSBabyNames babynames = new NYSBabyNames();
		String line;
		br.readLine();
		//int linenumber = 0;
		while ((line = br.readLine()) != null) {
			//csv file required
			ArrayList<String> entries = Split_method.splitCSVLine(line);
			//Instantiating name objects
			Name name = new Name(entries.get(1), entries.get(3), Integer.parseInt(entries.get(4)), entries.get(2));
			//Adding years to the YearNames list
			babynames.addYearName(Integer.parseInt(entries.get(0)), name);
			//number of lines in CSV used in debugging
//			linenumber++;
//			if (linenumber % 1000 == 0) {
//				System.out.println(linenumber + " ");
//			}
		}
		br.close();
		while (true) {
			//sets the years in order to fit with the project specs
			Collections.sort(babynames.yearnames);
			System.out.println("Enter a name:");
			String name = scan.nextLine().toLowerCase();
			if (name.equals("q")) {
				//signifies user has enter q or Q
				System.out.print("bye!");
				//break bad practice, seemed like the simplest way to exit from the program
				break;
			}
			System.out.println("Enter a county:");
			String usercounty = scan.nextLine().toLowerCase();
			babynames.plotHistogram(name, usercounty);
			System.out.println("done");
		}
	}

}