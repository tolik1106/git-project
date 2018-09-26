package com.javarush.task.task37.task3702.male;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;

public class MaleFactory implements AbstractFactory {

    public Human getPerson(int age) {
        if (age > 19) {
            return new Man();
        } else if (age > 12 && age < 20) {
            return new TeenBoy();
        } else if (age < 13)
            return new KidBoy();
        return null;
    }

}
