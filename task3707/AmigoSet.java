package com.javarush.task.task37.task3707;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

import java.io.*;
import java.util.*;


/*
AmigoSet (6)
Открой исходники HashSet (если у тебя нет исходников джавы, то скачай их и подключи), сравни со своим кодом.
Быстро это можно сделать сравнив через буфер. Скопируй код класса HashSet в буфер.
Зайди в класс AmigoSet, далее правая кнопка мыши -> Compare with Clipboard.

Ты только что реализовал сет, аналогичный HashSet. Теперь будешь знать, как внутри устроен HashSet.
Молодец, теперь коллекции тебе не страшны!


Требования:
1. Поздравляю, ты написал собственную реализацию множества и изучил HashSet во всех деталях!
AmigoSet (5)
Твое собственное множество AmigoSet реализует интерфейс Serializable. Однако, не сериализуется правильно.

1. Реализуй свою логику сериализации и десериализации.
Вспоминай, какие именно приватные методы нужно добавить, чтоб сериализация пошла по твоему сценарию.
Для сериализации:
* сериализуй сет
* сериализуй capacity и loadFactor у объекта map, они понадобятся для десериализации.
Т.к. эти данные ограничены пакетом, то воспользуйся утилитным классом HashMapReflectionHelper, чтобы достать их.

Для десериализации:
* вычитай все данные
* создай мапу используя конструктор с capacity и loadFactor

2. Помнишь, что такое transient?

AmigoSet (4)
Твое собственное множество AmigoSet реализует интерфейс Cloneable. Однако, не клонируется правильно.
Напиши свою реализацию метода Object clone(), сделай поверхностное клонирование.

* Клонируй множество, клонируй map.
* В случае возникновения исключений выбрось InternalError.
* Убери лишнее пробрасывание исключения.

Расширь модификатор доступа до public.

AmigoSet (3)
Напиши свою реализацию следующих методов при условии, что нужно работать с ключами мапы:
* Iterator<E> iterator() - очевидно, что это итератор ключей. Получи множество ключей в map, верни его итератор
* int size() - это количество ключей в map, равно количеству элементов в map
* boolean isEmpty()
* boolean contains(Object o)
* void clear()
* boolean remove(Object o)

Ничего своего писать не нужно, используй то, что уже реализовано для множества ключей map.
Используй Alt+Insert => Override methods

AmigoSet (2)
Изобретать механизм работы с хешем не будем, он уже реализован во многих коллекциях.

Мы возьмем коллекцию HashMap и воспользуемся ей.

1. Создай приватную константу Object PRESENT, которую инициализируй объектом Object, это будет наша заглушка.

2. Создай private transient поле HashMap<E,Object> map. Список ключей будет нашим сэтом, а вместо значений будем пихать в мапу заглушку PRESENT.

Напомню, нам нужны только ключи, а вместо значений для всех ключей будем вставлять PRESENT. Там же должно что-то быть :)

Посмотрим, что из этого получится :)

Коллекции обычно имеют несколько конструкторов, поэтому:

3. Создай конструктор без параметров, в котором инициализируй поле map.

4. Создай конструктор с одним параметром Collection<? extends E> collection.

Для инициализации поля map воспользуйся конструктором, в который передается Capacity.

Вычисли свою Capacity по такой формуле: максимальное из 16 и округленного в большую сторону значения (collection.size()/.75f)

Добавь все элементы из collection в нашу коллекцию.

Нужный метод добавления всех элементов у нас есть благодаря тому, что AbstractSet наследуется от AbstractCollection.

5. Напиши свою реализацию для метода метод add(E e): добавь в map элемент 'e' в качестве ключа и PRESENT в качестве значения.

Верни true, если был добавлен новый элемент, иначе верни false.

AmigoSet (1)
Давай напишем какую-нибудь коллекцию. Пусть это будет твой собственный Set.
Пусть этот класс позволяет вставку NULL.

1. Создай класс AmigoSet. Пусть этот класс наследуется от AbstractSet.
Этот сэт должен поддерживать интерфейсы Serializable и Cloneable (как же без этого??).
Также очевидно, что он должен реализовывать интерфейс Set.

2. Этот класс должен работать с любыми типами, поэтому сделай его дженериком: добавь тип, например, E.
Стандартные буквы, которые используют для дженериков - это E (element), T (type), K (key), V (value).
Названия не принципиальны, но облегчают чтение кода.

3. Воспользуйся горячими клавишами Идеи и реализуй необходимые методы, оставь реализацию по умолчанию.
 */

public class AmigoSet<E> extends AbstractSet<E> implements Set<E>, Serializable, Cloneable {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (int)Math.ceil(Math.max(16,Math.ceil(collection.size() / 0.75f)));
        map = new HashMap<>(capacity);
        for (E el: collection) {
            map.put(el, PRESENT);
        }
    }

    @Override
    public boolean add(E key) {
        return map.put(key, PRESENT) == null;
    }
    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
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

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() {
        AmigoSet<E> amigoSet = new AmigoSet<>();

        try {
            amigoSet.addAll(this);
            amigoSet.map.putAll((Map<? extends E, ?>) this.map.clone());
        }
        catch (Exception e){
            throw new InternalError(e);
        }
        return amigoSet;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(map.size());
        out.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        out.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        for (Map.Entry<E, Object> set: map.entrySet()) {
            out.writeObject(set.getKey());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int size = ((Integer)in.readObject()).intValue();
        float loadFactor = ((Float)in.readObject()).floatValue();
        int capacity = ((Integer)in.readObject()).intValue();
        map = new HashMap<>(capacity, loadFactor);
        for (int i = 0; i < size; i++) {
            E key = (E)in.readObject();
            map.put(key, PRESENT);
        }
    }
}
