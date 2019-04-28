package project5;

public class AVLTreeTest {

	public static void main(String[] args) {
		AVLTree<Integer> myavltree = new AVLTree<Integer>();
		myavltree.add(1);
		myavltree.add(5);
		myavltree.add(2);
		myavltree.add(4);
		myavltree.add(17);
		myavltree.add(-5);
		myavltree.add(21);
		myavltree.add(20);
		myavltree.add(13);
		myavltree.add(31);
		myavltree.remove(2);
		System.out.println("My tree: " + myavltree.toStringTreeFormat());
	}

}
