package pl.czerpak.commons.heap.fibonacci.fast;

import java.util.HashMap;
import java.util.Map;

import pl.czerpak.commons.heap.Heap;
import pl.czerpak.commons.heap.fibonacci.FibonacciHeap;
import pl.czerpak.commons.heap.fibonacci.FibonacciHeapElement;

/**
 * Sterta Fibonacciego z przyspieszonym uaktualnianiem priorytetów elementów.
 * Szybkoœæ dzia³ania jest okupiona wiêkszym zu¿yciem pamiêci.
 */
public class FastUpdateHeap<T> extends FibonacciHeap<T> implements Heap<T> {

	private Map<T, FibonacciHeapElement<T>> objectsMap = new HashMap<T, FibonacciHeapElement<T>>();

	public T extractMinimumEntry() {
		objectsMap.remove(minimum.getValue());
		return super.extractMinimumEntry();
	}

	public FibonacciHeapElement<T> put(T entry, Double priority) {
		FibonacciHeapElement<T> element = super.put(entry, priority);
		objectsMap.put(entry, element);

		return element;
	}

	protected T extractEntry(T entry) {
		FibonacciHeapElement<T> heapElement = objectsMap.get(entry);
		if (heapElement == null)
			return null;

		if (heapElement.getParent() != null)
			heapElement.getParent().getChildren().remove(heapElement);

		return heapElement.getValue();
	}

}
