package dsf19Exam2Version2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Map2Wrapper {

	public interface List<E> extends Iterable<E> {

		public int size();

		public boolean isEmpty();

		public boolean isMember(E e);

		public void add(E e);

		public void add(E e, int index);

		public E first();

		public E last();

		public int firstIndex(E e);

		public int lastIndex(E e);

		public E get(int index);

		public E replace(E e, int index);

		public E remove(int index);

		public boolean remove(E e);

		public int removeAll(E e);

		public void clear();

		public Object[] toArray();

	}

	public static class SinglyLinkedList<E> implements List<E> {

		@SuppressWarnings("hiding")
		private class SinglyLinkedListIterator<E> implements Iterator<E> {

			private Node<E> nextNode;

			@SuppressWarnings("unchecked")
			public SinglyLinkedListIterator() {
				super();
				this.nextNode = (Node<E>) header.getNext();
			}

			@Override
			public boolean hasNext() {
				return this.nextNode != null;
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					E result = this.nextNode.getElement();
					this.nextNode = this.nextNode.getNext();
					return result;
				} else {
					throw new NoSuchElementException();
				}
			}

		}

		// node class
		private static class Node<E> {
			private E element;
			private Node<E> next;

			public Node(E element, Node<E> next) {
				super();
				this.element = element;
				this.next = next;
			}

			public Node() {
				super();
				this.element = null;
				this.next = null;

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

		// private fields
		private Node<E> header;
		private int currentSize;

		public SinglyLinkedList() {
			this.header = new Node<>();
			this.currentSize = 0;

		}

		@Override
		public Iterator<E> iterator() {
			return new SinglyLinkedListIterator<E>();
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
		public boolean isMember(E e) {
			return this.firstIndex(e) >= 0;
		}

		@Override
		public void add(E e) {

			Node<E> temp = this.header;
			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			temp.setNext(new Node<E>(e, null));
			this.currentSize++;

			// cool
			// this.getPosition(this.size() -1).setNext(new Node<E>(e, null));
			// this.currentSize++;
		}

		@Override
		public void add(E e, int index) {
			if ((index < 0) || (index > this.size())) {
				throw new IndexOutOfBoundsException();
			}
			if (index == this.size()) {
				this.add(e);
			} else {
				Node<E> temp = null;
				if (index == 0) {
					temp = this.header;
				} else {
					temp = this.getPosition(index - 1);
				}
				// add new node
				Node<E> newNode = new Node<>();
				newNode.setElement(e);
				newNode.setNext(temp.getNext());
				temp.setNext(newNode);
				this.currentSize++;
			}
		}

		@Override
		public E first() {
			if (this.isEmpty()) {
				return null;
			} else {
				return this.header.getNext().getElement();
			}
		}

		@Override
		public E last() {
			if (this.isEmpty()) {
				return null;
			} else {
				Node<E> temp = this.header.getNext();
				while (temp.getNext() != null) {
					temp = temp.getNext();
				}

//				Cool code!
//				Node<E> temp; 
//				for (temp = this.header.getNext();
//						temp.getNext() != null;
//						temp = temp.getNext());

				return temp.getElement();
			}
		}

		@Override
		public int firstIndex(E e) {
			int currentPosition = 0;
			Node<E> temp = this.header.getNext();

			while (temp != null) {
				if (temp.getElement().equals(e)) {
					return currentPosition;
				} else {
					temp = temp.getNext();
					currentPosition++;
				}
			}
			return -1;

		}

		@Override
		public int lastIndex(E e) {
			int i = 0;
			int lastIndex = -1;
			Node<E> temp = this.header.getNext();
			while (temp != null) {
				if (temp.getElement().equals(e)) {
					lastIndex = i;
				}
				i++;
				temp = temp.getNext();
			}
			return lastIndex;
		}

		private Node<E> getPosition(int index) {
			int currentPosition = 0;
			Node<E> temp = this.header.getNext();

			while (currentPosition != index) {
				temp = temp.getNext();
				currentPosition++;
			}
			return temp;
		}

		@Override
		public E get(int index) {
			if ((index < 0) || (index >= this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			return this.getPosition(index).getElement();
		}

		@Override
		public E replace(E e, int index) {
			if ((index < 0) || (index >= this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			Node<E> temp = this.getPosition(index);
			E result = temp.getElement();
			temp.setElement(e);
			return result;
		}

		@Override
		public E remove(int index) {
			if ((index < 0) || (index >= this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			int currentPosition = 0;
			Node<E> temp = this.header;
			while (currentPosition != index) {
				temp = temp.getNext();
				currentPosition++;
			}
			Node<E> target = temp.getNext();
			E result = target.getElement();
			temp.setNext(target.getNext());
			target.setElement(null);
			target.setNext(null);
			this.currentSize--;
			return result;

		}

		@Override
		public boolean remove(E e) {
			int target = this.firstIndex(e);
			if (target < 0) {
				return false;
			} else {
				this.remove(target);
				return true;
			}
			// cool!
			// return (this.firstIndex(e) < 0 ? false :
			// (this.remove(this.firstIndex(e)) != null));
		}

		@Override
		public int removeAll(E e) {
			int count = 0;
			while (this.remove(e)) {
				count++;
			}
			return count;
		}

		@Override
		public void clear() {
			while (!this.isEmpty()) {
				this.remove(0);
			}

		}

		@Override
		public Object[] toArray() {
			Object[] result = new Object[this.size()];
			for (int i = 0; i < this.size(); ++i) {
				result[i] = this.get(i);
			}
			return result;
		}

	}

	public interface Map<K, V> {

		public int size();

		public boolean isEmpty();

		public V get(K key);

		public V put(K key, V value);

		public V remove(K key);

		public boolean contains(K key);

		public List<K> getKeys();

		public List<V> getValues();

		public List<K> bucketPals(K key);

	}

	@SuppressWarnings("unchecked")

	public static class HashtableSC<K, V> implements Map<K, V> {

		public static class MapEntry<K, V> {
			private K key;
			private V value;

			public K getKey() {
				return key;
			}

			public void setKey(K key) {
				this.key = key;
			}

			public V getValue() {
				return value;
			}

			public void setValue(V value) {
				this.value = value;
			}

			public MapEntry(K key, V value) {
				super();
				this.key = key;
				this.value = value;
			}

		}

		private int currentSize;
		private Object[] buckets;
		private static final int DEFAULT_BUCKETS = 11;

		private int hashFunction(K key) {
			return key.hashCode() % this.buckets.length;
		}

		public HashtableSC(int numBuckets) {
			this.currentSize = 0;
			this.buckets = new Object[numBuckets];
			for (int i = 0; i < numBuckets; ++i) {
				this.buckets[i] = new SinglyLinkedList<MapEntry<K, V>>();
			}
		}

		public HashtableSC() {
			this(DEFAULT_BUCKETS);
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
		public V get(K key) {
			int targetBucket = this.hashFunction(key);
			List<MapEntry<K, V>> L = (List<MapEntry<K, V>>) this.buckets[targetBucket];

			for (MapEntry<K, V> M : L) {
				if (M.getKey().equals(key)) {
					return M.getValue();
				}
			}
			return null;

		}

		@Override
		public V put(K key, V value) {
			V oldValue = this.get(key);
			if (oldValue != null) {
				this.remove(key);
			}
			int targetBucket = this.hashFunction(key);
			List<MapEntry<K, V>> L = (List<MapEntry<K, V>>) this.buckets[targetBucket];
			MapEntry<K, V> M = new MapEntry<K, V>(key, value);
			L.add(M, 0);
			this.currentSize++;

			return oldValue;
		}

		@Override
		public V remove(K key) {
			int targetBucket = this.hashFunction(key);
			List<MapEntry<K, V>> L = (List<MapEntry<K, V>>) this.buckets[targetBucket];
			int i = 0;

			for (MapEntry<K, V> M : L) {
				if (M.getKey().equals(key)) {
					// borrar
					V result = M.getValue();
					L.remove(i);
					this.currentSize--;
					return result;
				} else {
					i++;
				}
			}
			return null;
		}

		@Override
		public boolean contains(K key) {
			return this.get(key) != null;
		}

		@Override
		public List<K> getKeys() {
			List<K> result = new SinglyLinkedList<>();

			for (Object o : this.buckets) {
				List<MapEntry<K, V>> L = (List<MapEntry<K, V>>) o;
				for (MapEntry<K, V> M : L) {
					result.add(M.getKey(), 0);
				}
			}
			return result;
		}

		@Override
		public List<V> getValues() {
			List<V> result = new SinglyLinkedList<>();

			for (Object o : this.buckets) {
				List<MapEntry<K, V>> L = (List<MapEntry<K, V>>) o;
				for (MapEntry<K, V> M : L) {
					result.add(M.getValue(), 0);
				}
			}
			return result;
		}

		/*
		 * Write a member method named bucketPals(), which returns a list with all the
		 * keys currently located in the same bucket as a value with a given key
		 * (excluding the target key). If no value with the target key is found in the
		 * hash table, then the method returns an empty List.
		 */
		public List<K> bucketPals(K key) {

			List<K> ok = (List<K>) this.getValues();
			List<K> ok2 = ok;
			ok2.clear();
			if (!this.contains(key))
				return ok2;
			if (this.isEmpty())
				return ok2;

			for (int i = 0; i < ok.size(); i++) {
				if (this.get(ok.get(i)) == key) {
					ok2.add(key);
				}
			}
			return ok2;
		}

	}

}