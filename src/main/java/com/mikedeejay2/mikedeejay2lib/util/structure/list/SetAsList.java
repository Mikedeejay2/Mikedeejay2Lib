package com.mikedeejay2.mikedeejay2lib.util.structure.list;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A utility data structure for representing a Set as a List.
 *
 * @param <E> The type maintained by the set
 * @author Mikedeejay2
 */
public class SetAsList<E> implements List<E> {
    private final Set<E> set;

    public SetAsList(Set<E> collection) {
        this.set = collection;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(E kvEntry) {
        set.add(kvEntry);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return set.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        return set.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public E get(int index) {
        return toList().get(index);
    }

    @Override
    public E set(int index, E element) {
        if(index >= set.size()) {
            add(element);
            return null;
        }
        final List<E> list = toList();
        clear();
        E replaced = null;
        for(int i = 0; i < list.size(); ++i) {
            final E entry = list.get(i);
            if(i != index) {
                set.add(entry);
                continue;
            }
            set.add(entry);
            replaced = entry;
        }
        return replaced;
    }

    @Override
    public void add(int index, E element) {
        if(index >= set.size()) {
            add(element);
            return;
        }
        final List<E> list = toList();
        clear();
        for(int i = 0; i < list.size(); ++i) {
            final E entry = list.get(i);
            if(i != index) {
                set.add(entry);
                continue;
            }
            set.add(element);
            set.add(entry);
        }
    }

    @Override
    public E remove(int index) {
        if(index >= set.size()) return null;
        final List<E> list = toList();
        clear();
        E removed = null;
        for(int i = 0; i < list.size(); ++i) {
            final E entry = list.get(i);
            if(i == index) {
                removed = entry;
                continue;
            }
            set.add(entry);
        }
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return toList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return toList().lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator() {
        return toList().listIterator();
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return toList().listIterator(index);
    }

    @NotNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return toList().subList(fromIndex, toIndex);
    }

    private List<E> toList() {
        return new ArrayList<>(set);
    }
}
