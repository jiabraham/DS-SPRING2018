package project2;
/*
 * @author Joseph Abraham
 * @date 3/3/18
 * A basic implementation of a circular doubly linked list
 */
public class CircularDoublyLinkedList<E> {

	private static class Node<E> {
		private E element; //reference to the element stored at this node
		private Node<E> prev; //reference to the element stored in the previous node
		private Node<E> next; //reference to the element stored in the subsequent node
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}
		public E getElement() {
			return element;
		}
		public void setElement(E e) {
			element = e;
		}
		public Node<E> getPrev() {
			return prev;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setPrev(Node<E> p) {
			prev = p;
		}
		public void setNext(Node<E> n) {
			next = n;
		}
	}
	private int size = 0;
	private Node<E> head;
	private Node<E> tail;
	
	/**Constructs an empty list. */
	public CircularDoublyLinkedList() {
		head = new Node<>(null, null, null);
		tail = new Node<>(null, null, null);
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return (0 == size);
	}
	public E first() {
		if(isEmpty()) {
			return null;
		}
		return head.getNext().getElement();
	}
	public E last() {
		if(isEmpty()) {
			return null;
		}
		return head.getPrev().getElement();
	}
	public Node<E> getHead() { 
		return head.getNext(); 
	}
	
	public Node<E> getTail() { 
		return tail.getNext();
	}
	
	// Add to the head of the list
	public void addFirst(E e) {
		Node<E> n = new Node<E>(e, null, null);
		if (0 == size) {
			head.setNext(n);
			tail.setNext(n);
			n.setNext(n);
			n.setPrev(n);
		}
		else {
			n.setNext(head.getNext());
			n.setPrev(head.getNext().getPrev());
			head.getNext().setPrev(n);
			head.setNext(n);
			tail.getNext().setNext(n);
		}
		size++;
	}
	
	// Add to the tail of the list
	public void addLast(E e) {
		Node<E> n = new Node<E>(e, null, null);
		if (0 == size) {
			head.setNext(n);
			tail.setNext(n);
			n.setNext(n);
			n.setPrev(n);
		}
		else {
			n.setNext(head.getNext());
			n.setPrev(tail.getNext());
			tail.getNext().setNext(n);
			tail.setNext(n);
			head.getNext().setPrev(n);
		}
		size++;
	}
	
	public E removeFirst() {
		if(isEmpty()) {
			return null;
		}
		E e = head.getNext().getElement();
		if (1 == size) {
			head.setNext(null);
			tail.setNext(null);
		}
		else {
			tail.getNext().setNext(head.getNext().getNext());
			head.getNext().getNext().setPrev(tail.getNext());
			head.setNext(head.getNext().getNext());
		}
		size--;
		return e;
	}
	public E removeLast() {
		if(isEmpty()) {
			return null;
		}
		E e = tail.getNext().getElement();
		if (1 == size) {
			head.setNext(null);
			tail.setNext(null);
		}
		else {
			tail.getNext().getPrev().setNext(tail.getNext().getNext());
			head.getNext().setPrev(tail.getNext().getPrev());
			tail.setNext(head.getNext().getPrev());
		}
		size--;
		return e;		
	}
	public boolean equals(Object o) {
		if (o instanceof CircularDoublyLinkedList<?>) {
			// same amount of elements
			if (((CircularDoublyLinkedList<E>) o).size() == size()) {
				Node<E> item = head.getNext();
				Node<E> other = ((CircularDoublyLinkedList) o).getHead();
				Node<E> othertail = ((CircularDoublyLinkedList) o).getTail();
				// same elements in the same order
				while ( item != tail.getNext() && other != othertail) {
					if (! item.equals(other)) {
						return false;
					}
					item = item.getNext();
					other = other.getNext();
				}
				return true;
			}
		}
		return false;


	}
	public CircularDoublyLinkedList<E> clone() throws CloneNotSupportedException  {
		CircularDoublyLinkedList<E> ret = new CircularDoublyLinkedList<E>();
		Node<E> item = head.getNext();
		while ( item != tail.getNext()) {
			if (item == head.getNext() ) {
				ret.addFirst(item.getElement());
			}
			if(item == tail.getNext()) {
				ret.addLast(item.getElement());
			}
			ret.addBetween(item.getElement(), item.getPrev(), item.getNext());
			item = item.getNext();		
			}
		
		return ret;
	}
	public E get(int index) {
		// throw IndexOutOfBoundsException if index is greater than size or if index is negative
		if (index >= size() && index < 0) {
			throw new IndexOutOfBoundsException("Index is not within the size of the List");
		}
		Node<E> item = head.getNext();
		while ( item != tail.getNext()) {
			// return element of specified index
			if (index == 0) {
				return item.getElement();
			}
			index--;
			item = item.getNext();
		}		
		return null;
	}
	public E set(int index, E element) {
		//at a particular index, set the next reference equal to the passed element and 
		//the following node set the previous to the passed element
		if (index >= size() && index < 0) {
			throw new IndexOutOfBoundsException("Index is not within the size of the List");
		}
		Node<E> item = head.getNext();
		while ( item != tail.getNext()) {
			// return element of specified index
			if (index == 0) {
				E e = item.getElement();
				item.setElement(element);
				return e;
			}
			index--;
			item = item.getNext();
		}		
		return null;
	}
	public void rotate() {
		//The method should move the first element to the end of the list
		//if list is empty or has only one element, it should remain unchanged
		tail.setNext(head.getNext());
		head.setNext(head.getNext().getNext());
		
	}
	public void rotateBackward() {
		head.setNext(tail.getNext());
		tail.setNext(tail.getNext().getPrev());
		//The method should move the last element to the beginning of the list
		//if list is empty or has only one element, it should remain unchanged
	}
	public String toString() {
		String ret = "[";
		if(isEmpty() ) {
			return "[]";
		}
		Node<E> item = head.getNext();
		while ( item != tail.getNext()) {
			if (item == tail.getNext()) {
				ret += item.getElement() + "]";
			}
			ret += item.getElement() + ", ";
			item = item.getNext();		
			}
		return ret;
		}

	
	//private update methods 
	private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> newest = new Node<>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
	}
	
	private E remove(Node<E> node) {
		if(isEmpty()) {
			return null;
		}
		Node<E> item = head.getNext();
		while ( item != tail.getNext()) {
			// return element of specified index
			if (item.equals(node)) {
				E e = item.getElement();
				item.getPrev().setNext(item.getNext());
				item.getNext().setPrev(item.getPrev());
				return e;
			}
			item = item.getNext();
		}
		return null;
	}
	
}

