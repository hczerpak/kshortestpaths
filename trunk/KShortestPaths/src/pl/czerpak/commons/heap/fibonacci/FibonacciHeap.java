package pl.czerpak.commons.heap.fibonacci;

import java.util.List;

import pl.czerpak.commons.heap.Heap;
import pl.czerpak.commons.list.cdll.CircularDoublyLinkedList;

public class FibonacciHeap<T> implements Heap<T> {
	protected List<FibonacciHeapElement<T>> forest = new CircularDoublyLinkedList<FibonacciHeapElement<T>>();
	protected FibonacciHeapElement<T> minimum;
	private int size = 0;

	@SuppressWarnings("unchecked")
	public T extractMinimumEntry() {
		T extracted = minimum.getValue();
		forest.remove(minimum);
		for (int i = 0; i < minimum.getChildren().size(); i++) {
			FibonacciHeapElement<T> childElement = minimum.getChildren().get(i);
			childElement.setParent(null);
			forest.add(childElement);
		}

		// consolidate tree so that no two roots have the same degree
		boolean allDifferent = false;
		FibonacciHeapElement<T> current;
		Object[] array;
		while (!allDifferent) {
			allDifferent = true;
			current = null;
			array = new Object[size];
			for (int i = 0; i < forest.size(); i++) {
				current = forest.get(i);
				if (array[current.getDegree()] == null)
					array[current.getDegree()] = current;
				else {
					// je�li istnieje drzewo o takim samym degree() to scal oba
					boolean merged = false;
					while (!merged) {
						// conflicted to drzewo kt�re ju� znajduje si� w
						// rejestrze i ma taki sam degree jak aktualne drzewo
						FibonacciHeapElement<T> lesser, greather, conflicted = (FibonacciHeapElement<T>) array[current.getDegree()];

						// wybieramy drzewo o mniejszym priorytecie
						if (conflicted.getPriority() < current.getPriority()) {
							lesser = conflicted;
							greather = current;
						} else {
							lesser = current;
							greather = conflicted;
						}
						// wykasowujemy wpis w rejestrze bo powstanie nowe
						// drzewo
						array[current.getDegree()] = null;
						// z lasu znika drzewo kt�re ma by� do��czone jako
						// dziecko do drzewa 'lesser'
						forest.remove(greather);
						// do��cz drzewo
						lesser.getChildren().add(greather);
						greather.setParent(lesser);
						if (array[lesser.getDegree()] != null) {
							// kontynuuj scalanie je�li w wyniku scalania mamy
							// drzewo jest w konflikcie z innym
							merged = false;
							current = lesser;
						} else {
							merged = true;
							array[lesser.getDegree()] = lesser;
						}
					}
					// ��czenie drzew b�dzie kontynuowane
					allDifferent = false;

					// przerwanie p�tli for poniewa� rozmiar lasu si� zmieni�
					break;
				}
			}
		}

		minimum = null;
		for (int i = 0; i < forest.size(); i++) {
			current = forest.get(i);
			if (minimum == null || current.getPriority() < minimum.getPriority())
				minimum = current;
		}

		size--;
		return extracted;
	}

	public T getMinimum() {
		if (minimum == null)
			return null;
		else
			return minimum.getValue();
	}

	/***************************************************************************
	 * 1. create new tree 
	 * 2. add to left of minimum pointer 
	 * 3. update minimum pointer
	 **************************************************************************/
	public FibonacciHeapElement<T> put(T entry, Double priority) {
		if (entry == null)
			throw new NullPointerException("entry");
		// 1.
		FibonacciHeapElement<T> element = new FibonacciHeapElement<T>(priority, entry);
		// 2.
		forest.add(element);
		// 3.
		if (minimum == null || minimum.getPriority() > element.getPriority())
			minimum = element;

		size++;

		return element;
	}

	public boolean isEmpty() {
		return minimum == null;
	}

	public String toString() {
		return "FibonacciHeap";
	}

	/***************************************************************************
	 * Uaktualnia priorytet obiektu w stercie przez wyekstraktowanie elementu
	 * transportowego ze sterty (usunięcie) i dodanie z nowym priorytetem
	 **************************************************************************/
	public FibonacciHeapElement<T> update(T entry, Double priority) {
		T element = extractEntry(entry);
		return put(element, priority);
	}

	/**
	 * Funkcja ekstraktuj�ca element ze sterty
	 */
	protected T extractEntry(T entry) {
		return extractEntryFromForest(forest, entry);
	}

	private T extractEntryFromForest(List<FibonacciHeapElement<T>> list, T entry) {
		for (int i = 0; i < list.size(); i++) {
			FibonacciHeapElement<T> element = list.get(i);
			if (element.getValue() == entry) {
				list.remove(element);
				size--;
				return element.getValue();
			}
			T elementInChildren = extractEntryFromForest(element.getChildren(), entry);
			if (elementInChildren != null)
				return elementInChildren;
		}
		return null;
	}
}
