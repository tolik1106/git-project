package com.javarush.task.task33.task3310;


import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.OurHashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
Shortener (8)
Добавь и реализуй класс OurHashMapStorageStrategy, используя класс Entry из предыдущей 
подзадачи. Класс OurHashMapStorageStrategy должен реализовывать интерфейс StorageStrategy.
8.1. Добавь в класс следующие поля:
8.1.1. static final int DEFAULT_INITIAL_CAPACITY = 16;
8.1.2. static final float DEFAULT_LOAD_FACTOR = 0.75f;
8.1.3. Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
8.1.4. int size;
8.1.5. int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
8.1.6. float loadFactor = DEFAULT_LOAD_FACTOR;
8.2. Реализуй в классе следующие вспомогательные методы:
8.2.1. int hash(Long k)
8.2.2. int indexFor(int hash, int length)
8.2.3. Entry getEntry(Long key)
8.2.4. void resize(int newCapacity)
8.2.5. void transfer(Entry[] newTable)
8.2.6. void addEntry(int hash, Long key, String value, int bucketIndex)
8.2.7. void createEntry(int hash, Long key, String value, int bucketIndex)
8.3. Добавь в класс публичные методы, которые требует интерфейс StorageStrategy.
Какие-либо дополнительные поля класса не использовать. Методы, не описанные в задании,
реализовывать не нужно. Если возникнут вопросы как реализовать какой-то метод или что 
он должен делать, то ты всегда можешь посмотреть, как работает похожий метод в HashMap.
Можешь добавить в метод main класса Solution тестирование новой стратегии. Запусти и 
сравни время работы двух стратегий на одинаковом количестве элементов.
*/

public class Solution {

    public static void main(String[] args) {

        StorageStrategy strategy = new HashMapStorageStrategy();
        testStrategy(strategy, 30000);
        StorageStrategy newStrategy = new OurHashMapStorageStrategy();
        testStrategy(newStrategy, 30000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {     // 6.2.1
        Set<Long> ids = new HashSet<>();
        for (String srt: strings) {
            ids.add(shortener.getId(srt));
        }
        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {    // 6.2.2
        Set<String> ids = new HashSet<>();
        for (Long id: keys) {
            ids.add(shortener.getString(id));
        }
        return ids;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {   // 6.2.3
        System.out.println(strategy.getClass().getSimpleName());    // 6.2.3.1.

        Set<String> set = new HashSet<>();                          // 6.2.3.2.
        for (long i = 0; i < elementsNumber; i++) {
            String str = Helper.generateRandomString();
            set.add(str);
        }

        Shortener shortener = new Shortener(strategy);

        long start = new Date().getTime();
        Set<Long> ids = getIds(shortener, set);
        long end = new Date().getTime();
        System.out.println(end - start);
        start = new Date().getTime();
        Set<String> strings = getStrings(shortener, ids);
        end = new Date().getTime();
        System.out.println(end - start);
        for (String str: set) {
            if (!strings.contains(str)) {
                System.out.println("Тест не пройден.");
                return;
            }
        }
        System.out.println("Тест пройден.");
    }
}
