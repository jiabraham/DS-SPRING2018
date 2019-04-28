package project2;
import project2.CircularDoublyLinkedList;
/**
 * This is a simple tester class for project 2.
 * It DOES NOT provide complete testing of the CircularDoublyLinkedList class. 
 * 
 * @author Joanna Klukowska
 *
 */
public class Tester {

	public static void main(String[] args) throws CloneNotSupportedException {
		
		//create an empty list
		CircularDoublyLinkedList<String> list = new CircularDoublyLinkedList<>(); 
		
		//print an empty list 
		System.out.println( list );
		
		//add and remove elements 
		list.addFirst( "A");
		list.addLast( "B");
		
		System.out.println( list );
		
		list.removeLast();
		list.removeFirst();
		
		System.out.println( list );
		
		for (int i = 0; i < 5; i++) {
			list.addLast( (char)('Z' - i) + "");
			list.addFirst( (char)('A' + i) + "");
		}
		
		System.out.println( list );
		
		for (int i = 0; i < 6; i++) {
			list.removeFirst();
			list.removeLast();
		}
		
		System.out.println( list );
		
		
		
		//populate a list and create its clone 
		for (int i = 0; i < 5; i++) {
			list.addFirst( (char)('Z' - i) + "");
			list.addLast( (char)('A' + i) + "");
		}
				
		CircularDoublyLinkedList<String> list_clone = list.clone();
		
		if (!list.equals(list_clone)) {
			System.out.println("PROBLEM: list and clone not equal");
		}
		
		System.out.println( list );
		System.out.println( list_clone);

		list.removeFirst(); 
		list.addLast("K");
		list_clone.set(3, "M");


		if (list.equals(list_clone)) {
			System.out.println("PROBLEM: list and its clone should no longer be equal");
		}
		
		System.out.println( list );
		System.out.println( list_clone);
		
		//rotate the list in both directions 
		list.rotate(); 
		
		System.out.println( list );
		
		list.rotateBackward(); 
		list.rotateBackward(); 
		list.rotateBackward(); 
		list.rotateBackward(); 
		
		System.out.println( list );
		

	}

}