package pl.czerpak.commons.list.cdll;

public class CircularDoublyLinkedListElement<E> {
	private CircularDoublyLinkedListElement<E> prev = null;
	private CircularDoublyLinkedListElement<E> next = null;
	private E value = null;

	public CircularDoublyLinkedListElement<E> getPrev() {
		return prev;
	}

	public void setPrev(CircularDoublyLinkedListElement<E> prev) {
		this.prev = prev;
	}

	public CircularDoublyLinkedListElement<E> getNext() {
		return next;
	}

	public void setNext(CircularDoublyLinkedListElement<E> next) {
		this.next = next;
	}

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public CircularDoublyLinkedListElement(E value) {
		this.value = value;
	}
}
