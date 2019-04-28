package project1;

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
 * This method stores the name, gender, and count of any particular name that is contained in the file.
 * This method will print out the year, the percent of that particular name, and bars to represent the count.
 * @author Joseph Abraham
 * @version 02/13/2018
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
	 * This method is to add a Yearname object into the array yearnames and does not return anything.
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
     * @param fraction this double is to be passed and used to 
     * @return
     */
    private String getBars(double fraction) {
    		int numbar = (int) Math.ceil(fraction * 100);
    		String bars = "";
    		for (int i = 0; i < numbar; i++) {
    			bars += "|";
    		}
    		return bars;
    }
    /**
     * This method allows truncation of the large double to 4 decimal places as requested in the project specs
     * @param fraction is passed that needs to be modified
     * @return fraction2 is the modified double that only has 4 decimal places
     */
    private String formatFraction(double fraction) {
    		DecimalFormat jk = new DecimalFormat("#.####");
 
    		String fraction2 = jk.format(fraction);
    		return fraction2;
    	}

    
    private void plotHistogram(String name) {
    		System.out.println("Looking for: " + name);
    		for (int i = 0; i < yearnames.size(); i++) {
    			double fraction = yearnames.get(i).getFractionByName(name) * 100;
    			System.out.println(yearnames.get(i).getYear() + " (" + formatFraction(fraction) + "): "
    					 + getBars(fraction));
    			
    		}
    }


	public static void main(String[] args) throws FileNotFoundException,java.io.IOException 
	{
	
		
	    if (args.length == 0) 
		{
			System.err.println("Usage Error: the program expects file name as an argument");
			System.exit(1);
		}
	    Scanner scan = new Scanner(System.in);
	    FileReader fr = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(fr);
		NYSBabyNames babynames = new NYSBabyNames();
		String line;
		br.readLine();
		while ((line = br.readLine()) != null) {
			ArrayList<String> entries = split_method.splitCSVLine(line);
			Name name = new Name(entries.get(1), entries.get(3), Integer.parseInt(entries.get(4)));
			babynames.addYearName(Integer.parseInt(entries.get(0)), name);
		}
		br.close();
		Collections.sort(babynames.yearnames);
		while(true) {
			System.out.println("If you want to search, enter a name, otherwise enter q to quit:");
			String userinput = scan.nextLine().toLowerCase();
			if (userinput.equals("q")) {
				System.out.println("Thank you and goodbye...");
				break;
			}
			babynames.plotHistogram(userinput);
		}
	}
		
		/**
		 *System.out.println("If you want to search, enter a name, otherwise enter q to quit:");
		 *String userinput = scan.nextLine().toLowerCase();
		 *babynames.plotHistogram(userinput);
		 *System.out.println("done");
		 */
	

}