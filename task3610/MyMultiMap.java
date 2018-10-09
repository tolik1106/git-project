package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        //напишите тут ваш код
        int size = 0;
        for(Map.Entry<K, List<V>> set: map.entrySet()) {
            size += set.getValue().size();
        }
        return size;
    }

    @Override
    public V put(K key, V value) {
        //напишите тут ваш код
        boolean hasKey = map.containsKey(key);

        if (hasKey) {
            List<V> values = map.get(key);
            if (values.size() == 0) {
                values.add(value);
                return null;
            }
            else if (values.size() < repeatCount){
                values.add(value);
            }
            else if (values.size() == repeatCount){
                values.remove(0);
                values.add(value);
            }
            return values.get(values.size() - 2);
        }
        else {
            List<V> vList = new LinkedList<V>();
            vList.add(value);
            map.put(key, vList);
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        //напишите тут ваш код
        boolean hasKey = map.containsKey(key);
        if (hasKey) {
            List<V> list = map.get(key);
            if (list.size() > 0) {
                V value = list.remove(0);
                if (list.size() == 0) {
                    map.remove(key);
                }
                return value;
            }
            else if (list == null || list.size() == 0) {
                map.remove(key);
                return null;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        //напишите тут ваш код
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        LinkedList<V> collection = new LinkedList<>();
        for (Map.Entry<K, List<V>> set: map.entrySet()) {
            List<V> list = set.getValue();
            collection.addAll(list);
        }
        return (Collection<V>) collection;
    }

    @Override
    public boolean containsKey(Object key) {
        //напишите тут ваш код
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //напишите тут ваш код
        for (Map.Entry<K, List<V>> set: map.entrySet()) {
            K key = set.getKey();
            List<V> values = set.getValue();
            for(V val: values) {
                if (val.equals(value))
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}