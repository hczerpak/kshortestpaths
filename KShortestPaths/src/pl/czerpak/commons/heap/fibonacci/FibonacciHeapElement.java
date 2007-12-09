package pl.czerpak.commons.heap.fibonacci;

import java.util.List;

import pl.czerpak.commons.list.cdll.CircularDoublyLinkedList;

public class FibonacciHeapElement<T> {
	private Double priority;
	private FibonacciHeapElement<T> parent;
	private List<FibonacciHeapElement<T>> children = new CircularDoublyLinkedList<FibonacciHeapElement<T>>();
	private boolean marked = false;
	private T value = null;

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public FibonacciHeapElement<T> getParent() {
		return parent;
	}

	public void setParent(FibonacciHeapElement<T> parent) {
		this.parent = parent;
	}

	public List<FibonacciHeapElement<T>> getChildren() {
		return children;
	}

	public void setChildren(List<FibonacciHeapElement<T>> children) {
		this.children = children;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public FibonacciHeapElement(Double priority, T value, FibonacciHeapElement<T> parent) {
		this.value = value;
		this.parent = parent;
		this.priority = priority;
	}

	public FibonacciHeapElement(Double priority, T value) {
		this.value = value;
		this.parent = null;
		this.priority = priority;
	}

	public int getDegree() {
		return children.size();
	}
}
