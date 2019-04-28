package project5;


/**
 * Write a description of class Name here.
 *
 * @author Joseph Abraham
 * @version 04/22/18
 */
public class Name implements Comparable<Name>
{
    private String name;
    private String gender;
    private int count;
    private String county;
    
    /**
     * Constructor for objects of class Name
     * @param name is the string representing the name part of the Name object
     * @param gender is the string that represents the gender part of the Name object
     * @param count is the total number of babies in a given county with that particular name regardless of gender
     * @param county a string representing the place of a birth for a name object
     * @throws e IllegalArgumentException to prevent nullpointer exceptions and parameters that are not meaningful
     */
    public Name(String name, String gender, int count, String county) {
    		//Everything gets shifted to lower case so that the compareTo method will not have trouble with user confusion
    		if(name.equals(null) || name.length() < 1) {
    			throw new IllegalArgumentException("Name parameter cannot be null");
    		}
    		//this says gender can only be m or f
    		gender = gender.toLowerCase();
    		if(!(gender.equals("m") || gender.equals("f"))) {
    			throw new IllegalArgumentException("Gender parameter cannot be null");
    		}
    		if(county == null) {
    			throw new IllegalArgumentException("County parameter cannot be null");
    		}
    		if(county.equals("") || county.length() < 2) {
    			throw new IllegalArgumentException("County parameter cannot be illegal");
    		}
    		if (count < 0) {
    			throw new IllegalArgumentException("Count cannot be less than 1!");
    		}
    		if (name.equals("")) {
    			throw new IllegalArgumentException("There must be a name present!");
    		}
    		this.name = name.toLowerCase();
    		this.county = county.toLowerCase();
        this.gender = gender.toLowerCase();
        this.count = count;
        
    }
    
    
    /** 
     * compares Name objects to find out if they are different or the same so that multiple objects are not
     * made with the name and gender parameters being the same
     * if county or name is "all" then it allows any item to match
     * @return returns integer 0 if the objects are the same and normal compareTo return values otherwise
     */
    public int compareTo(Name name) 
    {
    		int ret = this.name.compareTo(name.name.toLowerCase());
    		if (ret == 0 || name.name.equals("all")) {
    			    	if (name.county.equals("all")) {
    					return 0;
    				}
    				return (this.county.compareTo(name.county.toLowerCase()));
    		
    		}
    		return ret;
    	}
    /**
     * compares Name objects to find out regardless of gender if they're the same, used in last project
     * @param name is needed to compare with other names
     * @return int 0 if names are the same or 1 if they are different
     */
    public int compareWithoutGender(String name) {
   		if (this.name.equals(name)) {
			return 1;
		}
		return 0;
    }
    /**
     * This method is useful in being able to increment the count
     * @param count is needed so the program knows by exactly how much to increment by
     */
    public void addCount(int count) {
    		this.count += count;
    }
    /**
     * This method return the count of a specific name with a name object
     * @return int this.count is returned to be used in frequency calculations
     */
    public int getCount() {
    		return this.count;
    }


    /**
     * The compareTo pretty much covers everything I need in my implementation.
     * @param  Name name is passed through to test whether or not this is the same as another given Name object
     * @return boolean representing whether the objects are the same or different
     */
    public boolean equals(Name name)
    {
      if (this.name.equals(name.name) &&
        this.gender.equals(name.gender) &&
        this.count == name.count &&
      	this.county.equals(name.county)){
    	  	return true;
      }
      return false; 
    }
    
    /**
     * Did not end up using this method
     * @return String this.name just indicating the name of some Name object
     */
    public String toString() 
    {
        return this.name +  " " + this.county + " " + this.gender + " " + this.count;
    }
}
