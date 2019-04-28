package project1;

/**
 * Write a description of class Name here.
 *
 * @author Joseph Abraham
 * @version 02/13/18
 */
public class Name implements Comparable<Name>
{
    private String name;
    private String gender;
    private int count;
    
    /**
     * Constructor for objects of class Name
     * @param name is the string representing the name part of the Name object
     * @param gender is the string that represents the gender part of the Name object
     * @param count is the total number of babies with that particular name regardless of gender
     */
    public Name(String name, String gender, int count) throws IllegalArgumentException
    {
    		if (count < 1) {
    			throw new IllegalArgumentException("Count cannot be less than 1!");
    		}
    		if (name.equals("")) {
    			throw new IllegalArgumentException("There must be a name present!");
    		}
    		this.name = name.toLowerCase();
        this.gender = gender.toLowerCase();
        this.count = count;
    }
    /**
     * compares Name objects to find out if they are different or the same so that multiple objects are not made with the name and gender parameters being the same
     * @return returns int 1 if the objects are the same and zero if they are different
     */
    public int compareTo(Name name) 
    {
    		if (this.name.equals(name.name.toLowerCase()) && 
    				(this.gender.equals(name.gender))) {
    			return 0;
    		}
    		return 1;
    }
    /**
     * compares Name objects to find out regardless of gender if they're the same
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
     * Did not end up using this method
     * @param  Name name is passed through to test whether or not this is the same as another given Name object
     * @return boolean representing whether the objects are the same or different
     */
    public boolean equals(Name name)
    {
      if (this.name.equals(name.name) &&
        this.gender.equals(name.gender) &&
        this.count == name.count) {
    	  	return true;
      }
      return false; 
    }
    @Override
    /**
     * Did not end up using this method
     * @return String this.name just indicating the name of some Name object
     */
    public String toString() 
    {
        return this.name;
    }
}
