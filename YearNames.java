package project1;


import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 * Write a description of class YearNames here.
 *
 * @author Joseph Abraham
 * @version 2/13/18
 */
public class YearNames implements Comparable<YearNames>
{
	private ArrayList<Name> names = new ArrayList<Name>(100);
	//The ArrayList meant to hold the Name objects that can be searched through to be sorted and placed accordingly
	private int year;
	//This variable will contain the year in the csv file that is associated with any given name
	private int totalcount;
	//This variable contains the total number of all the babies born in a single year to calculate the fraction later on
	/**
	 * This constructor is to make sure that Yearname objects are instantiated properly.
	 * @param year An integer passed that must be between 1800 and 2018
	 */
	public YearNames(int year) throws IllegalArgumentException
	{
		if (year < 1900 || year > 2018) {
			throw new IllegalArgumentException("Year must be between 1800 and 2018");
		}
		this.year = year;
	}
	/**
	 * This constructor is to make sure that Yearname objects are instantiated properly.
	 * @param year An integer passed that must be between 1800 and 2018
	 * @param name the object Name will be passed to here so that output will be more efficiently created
	 */
	public YearNames(int year, Name name) throws IllegalArgumentException
	{
		if (year < 1900 || year > 2018) {
			throw new IllegalArgumentException("Year must be between 1800 and 2018");
		}
		this.year = year;
		this.add(name);
	}
	/**
	*This is to sort the ArrayList that holds the Yearnames object so that they are able to be printed in ascending order
	*@param this will take a yearnames object to compare and ensure the correct order
	*@return this.year-compareyear a positive integer when the previous year is larger than the next year
	*/
	@Override
    public int compareTo(YearNames yearnames) {
        int compareyear=((YearNames)yearnames).getYear();
        return this.year-compareyear;
    }
	/**
	 * Get method to return the year or a Yearnames object
	 *@return this.year an integer that is the year contained in the Yearnames object
	 */
	public int getYear() {
		return this.year;
	}
	/**
	 * Add method so that Name objects can be added to the names Arraylist and if they already exist then the count is incremented
	 *@param name a Name object needs to be passed in order to place it into the ArrayList
	 */
	public void add(Name name) 
	{
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).compareTo(name) == 0) {
				names.get(i).addCount(name.getCount());
				totalcount += name.getCount();
				return;
			}
		}
		names.add(name);
		totalcount += name.getCount();
	}
	/**
	 * This is method is to search through the names array and count the number of times it appears in a given year
	 * @return int count returns an integer count that is used in the output to represent how often a name appears
	 */
	public int getCountByName(String name) 
	{
		int count = 0;
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).compareWithoutGender(name) == 1) {
				count += names.get(i).getCount();
			}
		}
		return count;
	}
	/**
	 * This method provides the number of babies with a given name compared with the number of babies in a given year
	 * This is a major part of the output
	 * @param  name a string that allows the method getCountByName() to search for that string within the ArrayList names
	 * @return fraction a double that is babies with the same name regardless of gender divided by the total number of babies in a year
	 */
	public double getFractionByName(String name)
	{
		
		double fraction =  ((double)getCountByName(name) / (double) totalcount);
		return fraction;
	}
}
