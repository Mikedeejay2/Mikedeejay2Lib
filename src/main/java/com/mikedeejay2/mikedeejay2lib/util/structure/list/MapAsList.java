package com.mikedeejay2.mikedeejay2lib.util.structure.list;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A utility data structure for representing a Map as a List.
 *
 * @param <K> The type of keys maintained by the map
 * @param <V> The type of mapped values
 * @author Mikedeejay2
 */
public class MapAsList<K, V> implements List<Map.Entry<K, V>> {
    private final Map<K, V> map;

    public MapAsList(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return map.entrySet().iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return map.entrySet().toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return map.entrySet().toArray(a);
    }

    @Override
    public boolean add(Map.Entry<K, V> kvEntry) {
        map.put(kvEntry.getKey(), kvEntry.getValue());
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for(Object obj : c) {
            if(!map.containsKey(obj)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Map.Entry<K, V>> c) {
        for(Map.Entry<K, V> e : c) add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends Map.Entry<K, V>> c) {
        for(Map.Entry<K, V> e : c) add(index++, e);
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        for(Object e : c) remove(e);
        return true;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        boolean changed = false;
        for(Object e : map.keySet().toArray()) {
            if(!c.contains(e)) {
                map.remove(e);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map.Entry<K, V> get(int index) {
        return toListRef().get(index);
    }

    @Override
    public Map.Entry<K, V> set(int index, Map.Entry<K, V> element) {
        System.out.println("set");
        if(index >= map.size()) {
            add(element);
            return null;
        }
        final List<Map.Entry<K, V>> list = toListFull();
        clear();
        Map.Entry<K, V> replaced = null;
        for(int i = 0; i < list.size(); ++i) {
            final Map.Entry<K, V> entry = list.get(i);
            if(i != index) {
                map.put(entry.getKey(), entry.getValue());
                continue;
            }
            map.put(element.getKey(), element.getValue());
            replaced = entry;
        }
        return replaced;
    }

    @Override
    public void add(int index, Map.Entry<K, V> element) {
        System.out.println("add");
        new Exception().printStackTrace();
        if(index >= map.size()) {
            add(element);
            return;
        }
        final List<Map.Entry<K, V>> list = toListFull();
        clear();
        for(int i = 0; i < list.size(); ++i) {
            final Map.Entry<K, V> entry = list.get(i);
            if(i != index) {
                map.put(entry.getKey(), entry.getValue());
                continue;
            }
            map.put(element.getKey(), element.getValue());
            map.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map.Entry<K, V> remove(int index) {
        System.out.println("remove");
        if(index >= map.size()) return null;
        final List<Map.Entry<K, V>> list = toListFull();
        clear();
        Map.Entry<K, V> removed = null;
        for(int i = 0; i < list.size(); ++i) {
            final Map.Entry<K, V> entry = list.get(i);
            if(i == index) {
                removed = entry;
                continue;
            }
            map.put(entry.getKey(), entry.getValue());
        }
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return toListRef().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return toListRef().lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<Map.Entry<K, V>> listIterator() {
        return toListRef().listIterator();
    }

    @NotNull
    @Override
    public ListIterator<Map.Entry<K, V>> listIterator(int index) {
        return toListRef().listIterator(index);
    }

    @NotNull
    @Override
    public List<Map.Entry<K, V>> subList(int fromIndex, int toIndex) {
        return toListRef().subList(fromIndex, toIndex);
    }

    private List<Map.Entry<K, V>> toListFull() {
        final List<Map.Entry<K, V>> list = new ArrayList<>();
        for(Map.Entry<K, V> entry : map.entrySet()) {
            list.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    private List<Map.Entry<K, V>> toListRef() {
        return new ArrayList<>(map.entrySet());
    }
}
