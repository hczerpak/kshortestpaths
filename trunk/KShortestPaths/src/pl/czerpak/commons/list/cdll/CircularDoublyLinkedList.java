package pl.czerpak.commons.list.cdll;

import java.util.List;

public class CircularDoublyLinkedList<E> extends AbstractNotImplementedList<E> implements List<E> {
	private CircularDoublyLinkedListElement<E> first = null;

	public int size() {
		int counter = 0;
		CircularDoublyLinkedListElement<E> iterator = null;
		if (first != null) {
			while (iterator != first) {
				if (iterator == null)
					iterator = first;
				iterator = iterator.getNext();
				counter++;
			}
		}
		return counter;
	}

	public boolean add(E item) {
		CircularDoublyLinkedListElement<E> element = new CircularDoublyLinkedListElement<E>(item);

		if (size() == 0) {
			first = element;
			first.setPrev(element);
			first.setNext(element);
		} else {
			first.getPrev().setNext(element);
			element.setPrev(first.getPrev());
			first.setPrev(element);
			element.setNext(first);
		}

		return true;
	}

	public void add(int index, E item) {
		validateIndex(index);

		CircularDoublyLinkedListElement<E> element = getElement(index);
		CircularDoublyLinkedListElement<E> elementToSet = new CircularDoublyLinkedListElement<E>(item);

		elementToSet.setPrev(element.getPrev());
		elementToSet.setNext(element);
		element.getPrev().setNext(elementToSet);
		element.setPrev(elementToSet);
		if (index == 0)
			first = elementToSet;
	};

	private CircularDoublyLinkedListElement<E> getElement(int index) {

		CircularDoublyLinkedListElement<E> element = first;
		for (int i = 0; i < index; i++)
			element = element.getNext();

		return element;
	}

	private void validateIndex(int index) {
		if (index > size())
			throw new IndexOutOfBoundsException("Index (" + index + ") out of bounds (" + size() + ")");
		if (index < 0)
			throw new IndexOutOfBoundsException("Negative index is not acceptable.");
	}

	public E get(int index) {
		return getElement(index).getValue();
	}

	public int indexOf(Object item) {
		CircularDoublyLinkedListElement<E> element = first;
		for (int i = 0; i < size(); i++)
			if (item == element.getValue())
				return i;
			else
				element = element.getNext();

		return -1;
	}

	public void clear() {
		first = null;
	}

	public E remove(int index) {
		validateIndex(index);

		CircularDoublyLinkedListElement<E> element = getElement(index);

		if (size() == 1)
			first = null;
		else {
			element.getPrev().setNext(element.getNext());
			element.getNext().setPrev(element.getPrev());
			if (first == element)
				first = element.getNext();
			element.setNext(null);
			element.setPrev(null);
		}

		return element.getValue();
	}

	public E set(int index, E item) {
		validateIndex(index);

		CircularDoublyLinkedListElement<E> element = getElement(index);
		CircularDoublyLinkedListElement<E> elementToSet = new CircularDoublyLinkedListElement<E>(item);

		elementToSet.setPrev(element.getPrev());
		elementToSet.setNext(element);
		element.getPrev().setNext(elementToSet);
		element.setPrev(elementToSet);

		if (index == 0)
			first = elementToSet;

		return element.getValue();
	}

	public boolean isEmpty() {
		return first == null;
	}

	public boolean remove(Object entry) {
		E e = remove(indexOf(entry));
		return e != null;
	}

}
