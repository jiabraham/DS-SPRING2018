package project5;

import java.util.ArrayList;
/**
 * This tree defines and creates an AVL tree. 
 * @author Joseph Abraham
 *
 * @param <T> this just means that within every node of the tree there is some data T
 */
public class AVLTree<T extends Comparable<T>> {

	// root of the tree
	protected Node<T> root;
	// current number of nodes in the tree
	protected int numOfElements;
	//helper variable used by the remove methods
	private boolean found;

	/**
	 * Default constructor that creates an empty tree.
	 */
	public AVLTree() {
		this.root = null;
		numOfElements = 0;
	}

	/**
	 * Add the given data item to the tree. If item is null, the tree does not
	 * change. If item already exists, the tree does not change.
	 * @param item the new element to be added to the tree
	 */
	public void add(T item) {
		if (item == null)
			return;
		root = add (root, item);
		//indirect recursion

	}

	/*
	 * Actual recursive implementation of add. This will not look on every element of the tree when looking for a place to add a node,
	 * it is supposed to only travel down the particular path where it can be eventually added at a leaf. If we are trying to add something
	 * that is already there this implementation should simply increment the count
	 *
	 * @param item the new element to be added to the tree
	 * @param node this is required so we know what to compare item with and which child to next look to to find a place for item
	 */
	private Node<T> add(Node<T> node, T item) {
		//System.out.println("Add1: "+ this.toStringTreeFormat());
		if (node == null) {
			//increment the total number of elements in for the tree
			numOfElements++;
			//returns the node that was just added
			return new Node<T>(item);
		}
		//Determines which path to take
		int data_compare = node.data.get(0).compareTo(item);
		//System.out.println("Compare: "+ node.toString() + " new item: " + item + " ret " + data_compare);
		if (data_compare > 0) {
			node.left = add(node.left, item);
			//System.out.println("After add child to left: "+ this.toString() + " new item: " + item);
		}
		else if (data_compare < 0) {
			node.right = add(node.right, item);
			//System.out.println("After add child to right: "+ this.toString() + "new item: " + item);
		}
		else {
			node.count++;
			node.addData(item);
			return node;
		}
		//System.out.println("Tree: "+ this.toStringTreeFormat());
		// After adding, the heights on that specific path need to be updated
		updateHeight(node);
		// Should this be the root or from the current node?
		node = updateBalanceFactor(node, item);
		//System.out.println("TreeHeight: "+ this.toHeightString());
		//System.out.println("Return from add : "+ node.toString() + " new item: " + item);
		return node;
	}

	/**
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged.
	 *
	 * @param target the item to be removed from this tree
	 */
	public boolean remove(T target)
	{
		found = false;
		ArrayList<T> search_target = new ArrayList<T>();
		search_target.add(target);
		root = recRemove(search_target, root);
		if (found) {
			numOfElements--; 
			
		}
		return found;
	}


	/*
	 * Actual recursive implementation of remove method: find the node to remove.
	 *
	 * @param target the item to be removed from this tree
	 */
	private Node<T> recRemove(ArrayList<T> target, Node<T> node)
	{
		if (node == null)
			found = false;
		else if (target.get(0).compareTo(node.data.get(0)) < 0)
			node.left = recRemove(target, node.left);
		else if (target.get(0).compareTo(node.data.get(0)) > 0)
			node.right = recRemove(target, node.right );
		else {
			node = removeNode(node);
			found = true;
		}
		
		return node;
	}

	private Node<T> childRemove(ArrayList<T> target, Node<T> node)
	{
		if (node == null) {
		}
		else if (target.get(0).compareTo(node.data.get(0)) < 0) {
			node.left = childRemove(target, node.left);
		}
		else if (target.get(0).compareTo(node.data.get(0)) > 0) {
			node.right = childRemove(target, node.right );
	    }	
		else {
			node = null;
		}	
		return node;
	}
	
	/*
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private Node<T> removeNode(Node<T> node)
	{
		ArrayList<T> data;
		if (node.left == null && node.right == null) {
			return null;
		}
		else if (node.left == null && node.right != null) {
			return node.right ;
		}
		else if (node.right  == null && node.left != null) {
			return node.left;
		}
		data = getPredecessor(node.left);
		if (node.left.data.get(0).compareTo(data.get(0)) == 0) {
			Node<T >temp = node.left;
			node.left = temp.left;
			node.data = data;
		}
		else {
			node.left = childRemove(data, node.left);
			node.data = data;
		}
		node = updateBalanceFactor(node, node.data.get(0));
		return node;
	}
	/**
	 * This method should go through the necessary path and update the node heights.
	 * @param node requires a node to be updated 
	 * @return returns the new height of the node
	 */
	public int updateHeight(Node<T> node) {
		//need to test if the node itself is null first to avoid a null pointer
		if(node == null) {
			return 0;
		}
		int leftheight = 0;
		int rightheight = 0;
		if (node.left != null) {
			leftheight = updateHeight(node.left);
		}
		if (node.right != null) {
		    	rightheight = updateHeight(node.right);
		}
		node.height = Math.max(leftheight, rightheight) + 1;
		// System.out.println("Setting height: " + node.toString() + " height --> " + node.height);
		return node.height;
	}
	/**
	 * This method should go through the necessary path and update the node balance factors.
	 * @param node requires a node to be updated 
	 * @param T item is requires so the method doesn't have to go through the whole tree
	 * @return returns the new root of the node
	 */
	public Node<T> updateBalanceFactor(Node<T> node, T item) {
		if(node == null) {
			return null;
		}
		int leftheight = 0;
		int rightheight = 0;
		if (node.left != null) {
			node.left = updateBalanceFactor(node.left, item);
			leftheight = node.left.height;
		}
		if (node.right != null) {
			node.right = updateBalanceFactor(node.right, item);
			rightheight = node.right.height;
		}
		node.bf = leftheight - rightheight;
		//System.out.println("BF: node " + node.toString() + " " + leftheight + " " + rightheight + " " + node.bf);
		Node<T> updatedSubtreeRoot = rebalance(node, item);
		//System.out.println("After Rebalance: " + this.toStringTreeFormat());
		return updatedSubtreeRoot;
	}

	/*
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 */
	private ArrayList<T> getPredecessor(Node<T> subtree)
	{
		if (subtree==null) throw new NullPointerException("getPredecessor called with an empty subtree");
		Node<T> temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}



	/**
	 * Determines the number of elements stored in this AVL.
	 *
	 * @return number of elements in this BST
	 */
	public int size() {
		return numOfElements;
	}

	/**
	 * Returns a string representation of this tree using an inorder traversal .
	 * @see java.lang.Object#toString()
	 * @return string representation of this tree
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		inOrderPrint(root, s);
		return s.toString();
	}
	/**This will print the string of the height of a node
	 * @return string that is a number of the height of a node in a given tree
	 */
	public String toHeightString() {
		StringBuilder s = new StringBuilder();
		inOrderPrintHeight(root, s);
		return s.toString();
	}
	
	/*
	 * Actual recursive implementation of in order traversal to produce string
	 * representation of this tree.
	 *
	 * @param tree the root of the current subtree
	 * @param s the string that accumulated the string representation of this BST
	 */
	private void inOrderPrint(Node<T> tree, StringBuilder s) {
		if (tree != null) {
			s.append(tree.data.toString() + "  ");
			inOrderPrint(tree.left, s);
			inOrderPrint(tree.right , s);
		}
	}

	private void inOrderPrintHeight(Node<T> tree, StringBuilder s) {
		if (tree != null) {
			s.append(tree.data.toString() + "_" + tree.height + "_" + tree.bf + " ");
			inOrderPrintHeight(tree.left, s);
			inOrderPrintHeight(tree.right , s);
		}
	}
	/**
	 * This method should do a simple rotation to the left used in rebalancing the tree.
	 * @param node requires the grandparent node so that the rotation can be performed
	 * @return returns the new root of the subtree
	 */
	public Node<T> rotateLeft(Node<T> node) {
		//System.out.println("RotateLeft: " + node.toString());
		Node<T> tempright = node.right;
		//System.out.println("RotateLeft tempright: " + tempright.toString());
		Node<T> leftTemp = tempright.left;
		
		// Rotation
		//   Moving left -- meaning right subtree is becoming root
		tempright.left = node;
		node.right = leftTemp;
		adjustHeight(node);
		adjustHeight(tempright);
		return tempright;
	}

	/**
	 * This method should do a simple rotation to the right used in rebalancing the tree.
	 * @param node requires the grandparent node so that the rotation can be performed
	 * @return returns the new root of the subtree
	 */
	public Node<T> rotateRight(Node<T> node) {
		//System.out.println("RotateRight: " + node.toString());
		Node<T> templeft = node.left;
		//System.out.println("Rotateright templeft: " + templeft.toString());
		Node<T> rightTemp = templeft.right;
		if (rightTemp != null) {
			//System.out.println("Rotateright righttemp: " + rightTemp.toString());
		}
		// Rotation
		//   Moving right -- meaning left subtree is becoming root
		templeft.right = node;
		node.left = rightTemp;
		adjustHeight(node);
		adjustHeight(templeft);
		return templeft;
	}

	public void adjustHeight(Node<T> node) {
		if (node == null) return;

		if (node.left != null) {
			node.height = node.left.height + 1;
		}
		if (node.right != null && node.height <= node.right.height) {
			node.height = node.right.height + 1;
		}
	}
		
	/**
	 * This is the method for rebalancing the entire tree when/if ever necessary
	 * @param node requires the node that is out of balance
	 * @param item needs the T item so that it can travel through the tree checking only the necessary values if they are unbalanced
	 * @return returns the new subtree root to rectify the unbalancedness of the tree
	 */
	public Node<T> rebalance(Node<T> node, T item) {
		if (node == null) return node;
		if (Math.abs(node.bf) <= 1) return node;
		Node<T> subtreeRoot = node;
		//System.out.println("Rebalance : " + node.toString() + " bf " + node.bf);
		//System.out.println("Added node : " + item);
		if(node.bf > 1 && node.left.data.get(0).compareTo(item) > 0) {
			// Right Rotate on node
			//System.out.println("RR: " + node.toString());
			subtreeRoot = rotateRight(node);
			//System.out.println("After RR: " + this.toStringTreeFormat());
			return subtreeRoot;
		}
		if (node.bf < -1 && node.right.data.get(0).compareTo(item) < 0) {
			// Left Rotate on node
			//System.out.println("LL: " + node.toString());
			subtreeRoot = rotateLeft(node);
			//System.out.println("After LL: " + this.toStringTreeFormat());
			return subtreeRoot;
		}
		if(node.bf > 1 && node.left.data.get(0).compareTo(item) < 0) {
			// Combination rotation
			// Left on node.left
			// Right on Node
			//System.out.println("LR: " + node.toString());
			node.left = rotateLeft(node.left);
			subtreeRoot = rotateRight(node);
			//System.out.println("After LR: " + this.toStringTreeFormat());
			return subtreeRoot;
		}
		if (node.bf < -1 && node.right.data.get(0).compareTo(item) > 0) {
			// Combination rotation
			// Right on node.right
			// Left on node
			//System.out.println("RL: " + node.toString());
			node.right = rotateRight(node.right);
			//System.out.println("RotateRight: Tree " + this.toStringTreeFormat());
			subtreeRoot = rotateLeft(node);
			return subtreeRoot;
		}
		return subtreeRoot;
	}
	/**
	 * This method can locate a given node in a tree with only the T data.
	 * @param input requires the input so that we can look for only the necessary nodes and do not have to traverse the whole tree
	 * @return returns the arraylist of data that it could potentially find(most won't have more than one element).
	 */
	public ArrayList<T> find(T input) {
		ArrayList<T> founddata = new ArrayList<T>();
	    findNode(this.root, input, founddata);
		return founddata;
	}
	
	public void findNode (Node<T> node, T input, ArrayList<T> founddata) {
		// Efficient?
		//System.out.println("Searching for: " + input.toString() + " comparing to: " + node.data.toString());
		if (node.data.get(0).compareTo(input) == 0) {
         	//System.out.println("Searching for: " + input.toString() + " found: " + node.data.toString() + " " + founddata.size());
			//if () {
				for (int i=0; i<node.data.size(); i++) {
					founddata.add(node.data.get(i));
				}
			//}
				
		}
		if (node.left != null) {
			findNode(node.left, input, founddata);
		}
		if (node.right != null) {
			findNode(node.right, input, founddata);
		}
	}
	
	/**
	 * DO NOT MOFIFY THIS METHOD.
	 * INCLUDE IT AS-IS IN YOUR CODE.
	 *
	 * Produces tree like string representation of this BST.
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {

		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * DO NOT MOFIFY THIS METHOD.
	 * INCLUDE IT AS-IS IN YOUR CODE.
	 *
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 *
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   BST
	 */
	private void preOrderPrint(Node<T> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|-- ";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}


	

}
