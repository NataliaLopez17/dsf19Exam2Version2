package dsf19Exam2Version2;

import java.io.PrintStream;

public class ProblemStack2Wrapper {
	public interface Stack<E> {

		public int size();

		public boolean isEmpty();

		public E top();

		public E pop();

		public void push(E e);

		public void clear();

		public void print(PrintStream out);

	}

	public static class SingleLinkedStack<E> implements Stack<E> {

		// node class
		@SuppressWarnings("hiding")
		private class Node<E> {
			private E element;
			private Node<E> next;

			@SuppressWarnings("unused")
			public Node(E element, Node<E> next) {
				super();
				this.element = element;
				this.next = next;
			}

			public Node() {
				super();
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

		}

		private Node<E> header;
		private int currentSize;

		public SingleLinkedStack() {
			this.header = new Node<>();
			this.currentSize = 0;
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
		public E top() {
			if (this.isEmpty()) {
				return null;
			} else {
				return this.header.getNext().getElement();
			}
		}

		@Override
		public E pop() {
			if (this.isEmpty()) {
				return null;
			} else {
				E result = this.header.getNext().getElement();
				Node<E> temp = this.header.getNext();
				this.header.setNext(temp.getNext());
				temp.setNext(null);
				temp.setElement(null);
				this.currentSize--;
				return result;
			}
		}

		@Override
		public void push(E e) {
			Node<E> newNode = new Node<>();
			newNode.setElement(e);
			newNode.setNext(this.header.getNext());
			this.header.setNext(newNode);
			this.currentSize++;
			return;
		}

		@Override
		public void clear() {
			while (this.pop() != null)
				;

		}

		@Override
		public void print(PrintStream out) {
			Node<E> temp = this.header.getNext();
			while (temp != null) {
				out.println(temp.getElement());
				temp = temp.getNext();
			}

		}
	}

	/*
	 * A palindrome is a word, phrase, number or sequence of words that reads the
	 * same backward as forward. For example, the following words are palindromes:
	 * civic, kayak, rotator. Phrases might have blank spaces. For example, the
	 * following phrases are palindromes: top spot, my gym. Write a non-member
	 * method named isPalindrome() which receives a string as parameter. The method
	 * return true if the string is a palindrome, or false otherwise. For
	 * convenience assume that strings are lower case.
	 * 
	 * Note 1: In Java, a string s can be stripped of white spaces with the
	 * following function call s.replaceAll("\\s+", "");
	 * 
	 * Note 2: In Java, you can access the character at position i on a string s
	 * with the function call s.charAt(i).
	 * 
	 * Note 3: In Java, the length of string s is given by s.length();
	 */
	public static boolean isPalindrome(String s) {
		int length = s.length();
		s = s.replaceAll("\\s+", "");
		if (s.length() == 0) {
			return true;
		} else {
			for (int i = 0; i < length; i++) {
				if (s.charAt(i) == s.charAt(length - 1)) {
					length--;
					continue;

				}
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String s1 = "civic";
		String s2 = "kayak";
		String s3 = "rotator";
		String s4 = "hi";
		String s5 = "kawaii";
		String s6 = "lol";
		String s7 = "no";

		System.out.println(isPalindrome(s1));
		System.out.println(isPalindrome(s2));
		System.out.println(isPalindrome(s3));
		System.out.println(isPalindrome(s4));
		System.out.println(isPalindrome(s5));
		System.out.println(isPalindrome(s6));
		System.out.println(isPalindrome(s7));

	}
}