package dsf19Exam2Version2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedList2Wrapper {

	public interface SortedList<E> extends Iterable<E> {

		// valid position in the list
		// [0, size() - 1]

		public boolean add(E obj);

		public int size();

		public boolean remove(E obj);

		public boolean remove(int index);

		public int removeAll(E obj);

		public E first();

		public E last();

		public E get(int index);

		public void clear();

		public boolean contains(E e);

		public boolean isEmpty();

		public Iterator<E> iterator(int index);

		public int firstIndex(E e);

		public int lastIndex(E e);

		public Iterator<E> reverseIterator();

		public Iterator<E> reverseIterator(int index);

		public SortedList<E> nearestSuccessors(E e);

	}

	public static class CircularSortedDoublyLinkedList<E> implements SortedList<E> {
		private class Node {
			private E data;
			private Node next;
			private Node prev;

			public Node() {
				this.data = null;
				this.next = null;
				this.prev = null;
			}

			public E getData() {
				return data;
			}

			public void setData(E data) {
				this.data = data;
			}

			public Node getNext() {
				return next;
			}

			public void setNext(Node next) {
				this.next = next;
			}

			public Node getPrev() {
				return prev;
			}

			public void setPrev(Node prev) {
				this.prev = prev;
			}
		}

		private class SCDLLIterator implements Iterator<E> {

			private Node currentNode;

			private SCDLLIterator() {
				this.currentNode = header.getNext();
			}

			public SCDLLIterator(int index) {
				if (index < 0 || index >= size()) {
					throw new IndexOutOfBoundsException();
				} else {
					int counter = 0;
					Node temp;
					for (temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
						if (counter == index) {
							break;
						}
					}
					this.currentNode = temp;
				}
			}

			@Override
			public boolean hasNext() {
				return this.currentNode != header;
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					E result = this.currentNode.getData();
					this.currentNode = this.currentNode.getNext();
					return result;
				} else {
					throw new NoSuchElementException();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		}

		private class SCDLLReverseIterator implements Iterator<E> {

			private Node currentPosition;

			private SCDLLReverseIterator() {
				this.currentPosition = header.getPrev();
			}

			private SCDLLReverseIterator(int index) {
				if (index < 0 || index >= size()) {
					throw new IndexOutOfBoundsException();
				} else {

					int counter = size() - 1;
					Node temp;
					for (temp = header.getPrev(); temp != header; temp = temp.getPrev(), counter--) {
						if (counter == index) {
							break;
						}
					}
					this.currentPosition = temp;
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean hasNext() {
				return this.currentPosition != header;
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					E result = this.currentPosition.getData();
					this.currentPosition = this.currentPosition.getPrev();
					return result;
				} else {
					throw new NoSuchElementException();
				}
			}

		}

		private Node header;
		private int currentSize;
		private Comparator<E> comparator;

		public CircularSortedDoublyLinkedList(Comparator<E> comparator) {
			this.header = new Node();
			this.header.setNext(header);
			this.header.setPrev(header);
			this.currentSize = 0;
			this.comparator = comparator;
		}

		public CircularSortedDoublyLinkedList() {
			this.header = new Node();
			this.header.setNext(header);
			this.header.setPrev(header);
			this.currentSize = 0;
		}

		@Override
		public Iterator<E> iterator() {
			return new SCDLLIterator();
		}

		@Override
		public boolean add(E obj) {
			Node newNode = new Node();
			newNode.setData(obj);
			this.currentSize++;
			Node temp = null;
			for (temp = header.getNext(); temp != header; temp = temp.getNext()) {
				// if (temp.getData().compareTo(obj) >= 0){
				// if (this.comparator.compare(temp.getData(), obj) >=0) {
				// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
				// we reach the place
				newNode.setNext(temp);
				newNode.setPrev(temp.getPrev());
				temp.getPrev().setNext(newNode);
				temp.setPrev(newNode);
				return true;
				// }
			}
			// obj is the largest so it goes at the
			newNode.setNext(header);
			newNode.setPrev(header.getPrev());
			header.getPrev().setNext(newNode);
			header.setPrev(newNode);
			return true;
		}

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean remove(E obj) {
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext()) {
				if (this.comparator.compare(temp.getData(), obj) == 0) {
					temp.getNext().setPrev(temp.getPrev());
					temp.getPrev().setNext(temp.getNext());
					temp.setData(null);
					temp.setNext(null);
					temp.setPrev(null);
					this.currentSize--;
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean remove(int index) {
			if (index < 0 || index >= this.size()) {
				throw new IndexOutOfBoundsException();
			} else {
				int counter = 0;
				Node temp;
				for (temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
					if (counter == index) {
						break;
					}
				}
				temp.getNext().setPrev(temp.getPrev());
				temp.getPrev().setNext(temp.getNext());
				temp.setData(null);
				temp.setNext(null);
				temp.setPrev(null);
				this.currentSize--;
				return true;
			}
		}

		@Override
		public int removeAll(E obj) {
			int count = 0;
			while (this.firstIndex(obj) >= 0) {
				this.remove(obj);
				count++;
			}
			return count;
		}

		@Override
		public E first() {
			if (this.isEmpty()) {
				return null;
			} else {
				return this.header.getNext().getData();
			}
		}

		@Override
		public E last() {
			if (this.isEmpty()) {
				return null;
			} else {
				return this.header.getPrev().getData();
			}
		}

		@Override
		public E get(int index) {
			if (index < 0 || index >= this.currentSize) {
				throw new IndexOutOfBoundsException();
			} else {
				int counter = 0;
				E result = null;
				for (Node temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
					if (counter == index) {
						result = temp.getData();
					}
				}
				return result;
			}
		}

		@Override
		public void clear() {
			while (!this.isEmpty()) {
				this.remove(0);
			}
		}

		@Override
		public boolean contains(E e) {
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext()) {
				if (this.comparator.compare(temp.getData(), e) == 0) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public Iterator<E> iterator(int index) {
			return new SCDLLIterator(index);
		}

		@Override
		public int firstIndex(E e) {
			int counter = 0;
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
				if (this.comparator.compare(temp.getData(), e) == 0) {
					return counter;
				}
			}
			return -1;
		}

		@Override
		public int lastIndex(E e) {
			int counter = this.size() - 1;
			for (Node temp = header.getPrev(); temp != header; temp = temp.getPrev(), counter--) {
				if (this.comparator.compare(temp.getData(), e) == 0) {
					return counter;
				}
			}
			return -1;
		}

		@Override
		public Iterator<E> reverseIterator() {
			return new SCDLLReverseIterator();
		}

		@Override
		public Iterator<E> reverseIterator(int index) {
			return new SCDLLReverseIterator(index);
		}

		/*
		 * Extend the functionality of the SortedCircularDoublyLinkedList by adding a
		 * method name nearestSuccessors(). This method receives an object e, and find
		 * the nearest 3 successors of e. The nearest successors are defined as: up to
		 * three elements that go immediately after e. The successors are returned as a
		 * sorted list. If the element e is not present, the method returns an empty
		 * list.
		 * 
		 * For example, if L = {Apu, Bob, Cal, Dan, Ed, Ed, Jim, Kim, Ned}, then a call
		 * to L.nearestSuccessors(Ed) returns R = {Ed, Jim, Kim}.
		 * 
		 * Likewise, a call to L. nearestSuccessors(Kim) returns R = {Ned}.
		 */
		@Override
		public SortedList<E> nearestSuccessors(E e) {
			SortedList<E> newList = new CircularSortedDoublyLinkedList<E>();
			Node curNode = header;
			Node stillNode = header;

			while (curNode.next != header) {
				if (stillNode.next.data == e) {
					return newList;
				} else if (stillNode.next.next.data == e) {
					newList.add(stillNode.next.data);
					return newList;
				} else if (stillNode.prev.data == e) {
					return newList;
				} else if (stillNode.prev.prev.data == e) {
					newList.add(stillNode.prev.data);
					return newList;
				} else {
					if (curNode.next.data == e) {
						newList.add(curNode.next.next.data);
						newList.add(curNode.next.next.next.data);
						newList.add(curNode.next.next.next.next.data);
						break;
					}
				}
				curNode = curNode.next;
			}
			return newList;
		}

		public static void main(String[] args) {
			SortedList<Object> okay = new CircularSortedDoublyLinkedList<Object>();
			okay.add("Ned");
			okay.add("Kim");
			okay.add("Jim");
			okay.add("Ed");
			okay.add("Ed");
			okay.add("Dan");
			okay.add("Cal");
			okay.add("Bob");
			okay.add("Apu");
			for (Object kek : okay) {
				System.out.println(kek);
			}
			SortedList<Object> kek = okay.nearestSuccessors("Dan");
			System.out.println("===========");
			for (Object zek : kek) {
				System.out.println(zek);
			}
		}
	}
}