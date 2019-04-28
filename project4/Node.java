package project5;

import java.util.ArrayList;

/**
 * Node class is used to represent nodes in a binary search tree.
 * It contains a data item that has to implement Comparable interface
 * and references to left and right subtrees.
 *
 * @author Joanna Klukowska
 *
 * @param <T> a reference type that implements Comparable<T> interface
 */
public class Node <T extends Comparable <T>>
					implements Comparable <Node <T> > {


	protected Node <T> left;  //reference to the left subtree
	protected Node <T> right; //reference to the right subtree
	protected ArrayList<T> data = new ArrayList<T>();      //data item stored in the node

	protected int height;
	protected int desc; 	//num of descendants
	protected int bf;
	
	protected int count;


	/**
	 * Constructs a AVLNode initializing the data part
	 * according to the parameter and setting both
	 * references to subtrees to null.
	 * @param data
	 *    data to be stored in the node
	 */
	protected Node(T data) {
		this.data.add(data);
		left = null;
		right = null;
		height = 1;
		desc = 0;
	}
	/**
	 * Adds data to the arraylist of stored data carried within each node
	 * @param data needed to update the arraylist
	 */
	public void addData(T data) {
		this.data.add(data);	
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Node<T> other) throws IllegalArgumentException {
		if (other == null) {
			throw new IllegalArgumentException("Parameter can not be null in the compareTo of a Node");
		}
		int ret = this.data.get(0).compareTo(other.data.get(0));
		//System.out.println("Compare to: " + this.toString() + " o " + other.toString() + " ret -> " + ret);
		return ret;
	}

	/* This method is a simple toString for nodes
	 * @return a string representation of a given node
	 */
	@Override
	public String toString() {
		return data.toString();
	}



}