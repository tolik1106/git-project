package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];

    private int size;
    private  int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    private static int hash(Long k) {
        int h = k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private static int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    private final Entry getEntry(Long key) {
        int hash = (key == null)? 0: hash(key);
        for(Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    private void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == 1 << 30) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < src.length; i++) {
            Entry e = src[i];
            if (e != null) {
                src[i] = null;
                do {
                    Entry next = e.next;
                    int index = indexFor(e.hash, newCapacity);
                    e.next = newTable[index];
                    newTable[index] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key , value, e);
        if (size++ >= threshold)
            resize(2 * table.length);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null) {
            Entry[] t = table;
            for (int j = 0; j < t.length; j++)
                for (Entry el = t[j]; el != null; el = el.next)
                    if (el.value == null)
                        return true;
            return false;
        }
        Entry[] tab = table;
        for (int i = 0; i < tab.length; i++) {
            for (Entry e = tab[i]; e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        if (key == null) {
            for (Entry entry = table[0]; entry != null; entry = entry.next) {
                if (entry.key == null) {
                    entry.value = value;
                    return;
                }
            }
            addEntry(0, null, value, 0);
            return;
        }
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                String oldValue = e.value;
                e.value = value;
                return;
            }
        }
        addEntry(hash, key, value, i);
    }

    @Override
    public Long getKey(String value) {
        Entry[] tab = table;
        for (int i = 0; i < table.length; i++) {
            for (Entry e = tab[i]; e != null; e = e.next)
                if (e.getValue().equals(value))
                    return e.key;
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        if (key == null) {
            for (Entry e = table[0]; e != null; e = e.next) {
                if (e.key == null) return e.value;
            }
            return null;
        }
        int hash = hash(key);
        for (Entry e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
    }
}
