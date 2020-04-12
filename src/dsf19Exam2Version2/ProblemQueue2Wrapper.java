package dsf19Exam2Version2;

import java.io.PrintStream;

public class ProblemQueue2Wrapper {
	public interface Queue<E> {

		public int size();

		public boolean isEmpty();

		public E front();

		public void enqueue(E e);

		public E dequeue();

		public void makeEmpty();

		public void print(PrintStream P);

	}

	public static class DoublyLinkedQueue<E> implements Queue<E> {

		private static class Node<E> {
			private E element;
			private Node<E> next;
			private Node<E> prev;

			public Node() {
				this.element = null;
				this.next = this.prev = null;

			}

			public E getElement() {
				return element;
			}

			public void setElement(E element) {
				this.element = element;
			}

			public Node<E> getNext() {
				return next;
			}

			public void setNext(Node<E> next) {
				this.next = next;
			}

			public Node<E> getPrev() {
				return prev;
			}

			public void setPrev(Node<E> prev) {
				this.prev = prev;
			}

		}

		private Node<E> header;
		private Node<E> tail;
		private int currentSize;

		public DoublyLinkedQueue() {
			this.currentSize = 0;
			this.header = new Node<>();
			this.tail = new Node<>();

			this.header.setNext(this.tail);
			this.tail.setPrev(this.header);
		}

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public E front() {
			return (this.isEmpty() ? null : this.header.getNext().getElement());
		}

		@Override
		public E dequeue() {
			if (this.isEmpty()) {
				return null;
			} else {
				Node<E> target = null;
				target = this.header.getNext();
				E result = target.getElement();
				this.header.setNext(target.getNext());
				target.getNext().setPrev(this.header);
				target.setNext(null);
				target.setPrev(null);
				target.setElement(null);
				this.currentSize--;
				return result;
			}
		}

		@Override
		public void enqueue(E e) {
			Node<E> newNode = new Node<E>();
			newNode.setElement(e);
			newNode.setNext(this.tail);
			newNode.setPrev(this.tail.getPrev());
			this.tail.setPrev(newNode);
			newNode.getPrev().setNext(newNode);
			this.currentSize++;
		}

		@Override
		public void makeEmpty() {
			while (this.dequeue() != null)
				;

		}

		@Override
		public void print(PrintStream P) {
			// TODO Auto-generated method stub
			Node<E> temp = this.header.getNext();
			while (temp != this.tail) {
				P.println(temp.getElement());
				temp = temp.getNext();
			}
		}
	}

	/*
	 * Write a non-member method named bottomQueue() that receives as parameters a
	 * queue Q and a integer N. The method returns last N elements in the queue Q.
	 * After completion, the queue Q must be left as it was at the beginning of the
	 * function. The method returns an empty queue if Q has less than N elements.
	 * 
	 * For example if Q = {Joe, Ned, Amy, Bob, Kim, Ron}, with Joe at the head, then
	 * bottomQueue(Q, 3), returns the queue {Bob, Kim, Ron}.
	 */
	public static Queue<String> bottomQueue(Queue<String> Q, int N) {
		Queue<String> newQ = new DoublyLinkedQueue<String>();
		Queue<String> target = new DoublyLinkedQueue<String>();
		Queue<String> copyQ = new DoublyLinkedQueue<String>();
		Queue<String> result = new DoublyLinkedQueue<String>();

		// ADD CODE HERE
		if (Q.size() < N) {
			return newQ;
		}
		if (Q.isEmpty()) {
			return newQ;
		}
		int count = Q.size();
		while (!Q.isEmpty()) {
			if (count > N) {
				newQ.enqueue(Q.front());
				count--;
			} else if (count <= N) {
				target.enqueue(Q.front());
			}
			copyQ.enqueue(Q.dequeue());
		}

		while (!newQ.isEmpty()) {
			result.enqueue(newQ.dequeue());
		}
		Q = copyQ;
		return target;
	}

	public static void main(String[] args) {
		Queue<String> newQ = new DoublyLinkedQueue<String>();

		newQ.enqueue("Joe");
		newQ.enqueue("Ned");
		newQ.enqueue("Amy");
		newQ.enqueue("Bob");
		newQ.enqueue("Kim");
		newQ.enqueue("Ron");

		Queue<String> newNewQ = bottomQueue(newQ, 3);
		while (!newNewQ.isEmpty()) {
			System.out.println(newNewQ.dequeue());
		}
	}
}