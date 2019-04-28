package project5;

import java.util.ArrayList;

public class YearNames extends AVLTree<Name> implements Comparable<YearNames> {
	//Initializing year
	private int year = 0;
	//Initializing totalcount to represent all the babies born in a particular year
	private int totalcount = 0;
	/**This is the YearNames constructor that will take in a year and allows the YearName object to be called upon. 
	 * @param int year takes this in so that for every year there will be a tree with name objects
	 * @throws e IllegalArgumentException when the year that is inputed in the data file is outside the scope of the program(specs)
	 */
	public YearNames(int year) throws IllegalArgumentException {
		if (year < 1900 || year > 2018) {
			throw new IllegalArgumentException("year must be between 1900 and 2018");
			
		}
		this.year = year;
	}
	/**This is the YearNames constructor that will take in a year and a Name and allows the YearName object to be called upon and
	 * will add a name object to the list automatically. 
	 * @param int year takes this in so that for every year there will be a tree with name objects
	 * @param Name name takes this so that it can add a name object
	 * @throws e IllegalArgumentException when the year that is inputed in the data file is outside the scope of the program(specs)
	 */
	public YearNames(int year, Name name) throws IllegalArgumentException {
		if (year <= 1900 || year >= 2018) {
			throw new IllegalArgumentException("year must be between 1900 and 2018");
			
		}
		this.year = year;
		add(name);
	}
	/**method that adds a new Name object to the list.
	 * @param Name name
	 */
	public void add (Name name) {
		totalcount += name.getCount();
		super.add(name);
	}
	/**method that returns the year number associated with this object.
	 * @return method returns an integer that the represents the year
	 */
	public int getYear () {
		return this.year;
		
	}
	/**method that returns the count for all the counties in a given year associated with this object.
	 * @return method returns an integer that the represents the year
	 */
	public int getTotal() {
		return this.totalcount;
	}
	/**Method that returns the number of babies that were given the name specified by the argument. 
	 * This should include the names matching two different genders and all counties.
	 * @param String name is the only thing required for this frequency method because we are searching based only on that one parameter
	 */
	public int getCountByName (String name) {
		return getCountByNameCounty(name, "ALL");
		//makes call to other method because this really just repeats the process with all counties instead of just one
	}
	/**method that returns the fraction of babies that were given the name specified by the argument (this is the number of such babies)
	 * divided by the total number of babies born in the year).
	 * @param name is required to get the specific fraction 
	 * @return double this is the fraction that is number of such babies
	 * divided by the total number of babies born in the year
	 */
	public double getFractionByName (String name) {
		return getFractionByNameCounty(name, "ALL");
		//makes call to other method because this really just repeats the process with all counties instead of just one
	}
	/**method that returns the number of babies that were given the name in the county specified by the argument. This should include
	 * the names matching both genders.
	 * @param name requires this to look at the name objects in the tree
	 * @param county requires this to look at the county data field of the name objects in the tree
	 * @return int returns the number of babies that were born in a particular county with a given name
	 */
	public int getCountByNameCounty (String name, String county) {
		//instantiating the value to be returned
		int ret = 0;
		//instantiating a name object so that we can look for the node in the find method
		Name search_record = new Name(name,"f", 1, county);
		//instantiating a list so that we don't have to look into the T from the node
		ArrayList<Name> names = find(search_record);
		for (int i=0; i<names.size(); i++) {
			//used in debugging
			// System.out.println("Found: " + names.get(i).toString());
			//this ensures that the correct count will be used for the county
			ret += names.get(i).getCount();
			
		}
		return ret;
	}
	/**method that returns the fraction of babies that were given the name in the county specified by the argument (this is the number
	 * of such babies divided by the total number of babies born in that county in the year)
	 * @param name This is used in searching for the name with the call to the getCountByCounty
	 * @param county This is also used in searching
	 * @return a double representing the number of such babies divided by the 
	 * total number of babies born in that county in the year
	 */
	public double getFractionByNameCounty (String name, String county) {
		double fraction =  ((double)getCountByNameCounty(name, county) / (double) totalcount);
		//Here's where we use the totalcount data field 
		return fraction;
	}
	/**indicated in specifications to be an override method, but the AVLTree does not need an equals in this implementation.
	 * This will return true if two YearName objects have the same year data field
	 * @param yearname is needed to compare it with the YearName it is being called on
	 * @return A boolean is returned true or false whether or not the YearName objects are the same
	 */
	public boolean equals(YearNames yearname) {
		if (yearname == null) {
			return false;
		}
		if (this.year == yearname.year) {
			return true;
		}
		return false;
	}
	/** This is a simple toString method that allows us to represent our AVL Tree using the method toStringTreeFormat provided to us
	 * @return String is return representing the tree of the particular year it is called on
	 */
	public String toString() {
		return Integer.toString(this.year) + toStringTreeFormat();
	}
	/** This method can compare two AVLTrees and report which one is of the greater or less year depending on the usage of whatever function calls this
	 * @param yearname is needed so that the yearname this method is being called on can be compared with it
	 * @return int -1 for the parameter yearname being smaller, 1 for it being larger, and zero if they are the same year, which should not happen in this implementation
	 */
    public int compareTo(YearNames yearnames) 
    {
    		if (this.year < yearnames.year) {
    			return -1;
    		}
    		else if (this.year > yearnames.year) {
    			return 1;
    		}
    		return 0;
    	}
	
}
