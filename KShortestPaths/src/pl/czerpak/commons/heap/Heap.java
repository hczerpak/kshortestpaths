package pl.czerpak.commons.heap;

import pl.czerpak.commons.heap.fibonacci.FibonacciHeapElement;

public interface Heap<T> {

	T extractMinimumEntry();

	T getMinimum();

	FibonacciHeapElement<T> put(T entry, Double priority);

	boolean isEmpty();

	FibonacciHeapElement<T> update(T entry, Double priority);
}
