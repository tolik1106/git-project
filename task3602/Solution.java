package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/* 
Найти класс по описанию

Поиск класса по описанию
Замени следующие слова на нужные:
1. ClassNameToBeReplaced - имя класса, потокобезопасный аналог ArrayList, в котором все операции изменения (mutative operations) используют новую копию основного массива.

2. methodNameToBeReplaced - имя метода, который в текущий список 'list' добавляет те элементы переданной коллекции, которые не содержатся в 'list'.

Не оставляй закомментированный код.


Требования:
1. Метод main должен создавать объект потокобезопасного аналога ArrayList, соответствующего заданию.
2. Метод main, после создания списка правильного типа, должен вызвать метод, который добавит в текущий список 'list' только те элементы переданной коллекции, которые не содержатся в 'list'.
3. Программа должна вывести 4 строки: "A C B D".
4. Программа не должна содержать слова "ClassNameToBeReplaced" и "methodNameToBeReplaced".
*/

public class Solution {
    public static void main(String[] args){
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {

        Class[] classes = Collections.class.getDeclaredClasses();
        for (Class c: classes) {
            Method m;
            try {
                m = c.getDeclaredMethod("get", int.class);
            } catch (NoSuchMethodException e) {
                continue;
            }
            if (Modifier.isPrivate(Modifier.PRIVATE) && Modifier.isStatic(Modifier.STATIC) && m != null) {
                try {
                    Constructor constructor = c.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    List list = (List) constructor.newInstance();
                    list.get(0);
                } catch (InstantiationException e) {
                } catch (NoSuchMethodException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                } catch (IndexOutOfBoundsException e) {
                    return c;
                }
            }
        }
        return null;
    }
}
