package pl.czerpak.commons.list.cdll;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AbstractNotImplementedList<E> implements List<E> {

	public boolean add(E arg0) {
		throw new CommingSoonException();
	}

	public void add(int arg0, E arg1) {
		throw new CommingSoonException();
	}

	public boolean addAll(Collection<? extends E> arg0) {
		throw new CommingSoonException();
	}

	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		throw new CommingSoonException();
	}

	public void clear() {
		throw new CommingSoonException();
	}

	public boolean contains(Object arg0) {
		throw new CommingSoonException();
	}

	public boolean containsAll(Collection<?> arg0) {
		throw new CommingSoonException();
	}

	public E get(int arg0) {
		throw new CommingSoonException();
	}

	public int indexOf(Object arg0) {
		throw new CommingSoonException();
	}

	public boolean isEmpty() {
		throw new CommingSoonException();
	}

	public Iterator<E> iterator() {
		throw new CommingSoonException();
	}

	public int lastIndexOf(Object arg0) {
		throw new CommingSoonException();
	}

	public ListIterator<E> listIterator() {
		throw new CommingSoonException();
	}

	public ListIterator<E> listIterator(int arg0) {
		throw new CommingSoonException();
	}

	public boolean remove(Object arg0) {
		throw new CommingSoonException();
	}

	public E remove(int arg0) {
		throw new CommingSoonException();
	}

	public boolean removeAll(Collection<?> arg0) {
		throw new CommingSoonException();
	}

	public boolean retainAll(Collection<?> arg0) {
		throw new CommingSoonException();
	}

	public E set(int arg0, E arg1) {
		throw new CommingSoonException();
	}

	public int size() {
		throw new CommingSoonException();
	}

	public List<E> subList(int arg0, int arg1) {
		throw new CommingSoonException();
	}

	public Object[] toArray() {
		throw new CommingSoonException();
	}

	public <T> T[] toArray(T[] arg0) {
		throw new CommingSoonException();
	}

}
