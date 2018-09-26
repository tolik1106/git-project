/*
������� (1) 
������! ������ �� ������ ����������� � ��������� Factory Method. ������� ������ � ������. ����� ����������, � ������ ���� ��������� ������� � ����������� �� ��� ��������.  � ���� ������� ���������� ������, ������� ����� ������������ � �������.  
1. ��� ���� ����� ����� ������ ����� ��������� Human.  
2. ����� ������ Factory ��� ������. ��� ����� ������ ����� male, � ������� ������ ������ KidBoy, TeenBoy, Man. �� ���� ���� ������� �������� ��������� Human.  
3. � ������ ������ KidBoy, TeenBoy ������ ���� ��������� ��������� MAX_AGE � ���������������� ����������: 12, 19.  
4. ���������� �������������� ������ toString ��� ������� ������ KidBoy, TeenBoy, Man. ��������� "KidBoy{}" � �.�.  
5. ������ ����� Solution � ������� main.

������� (2) 
1. ������ ��������� ����� MaleFactory � ������ male � ��������� ������� getPerson, ������� ��������� ���� �������� int age (�������) � ���������� ������ ������ ������ �� �������, ����������� ��������� Human.  
2. ������ ������ getPerson: ��������� ��������� MAX_AGE ��������, ����� ����� ������������� ����������� ��������. ��������, age=4 ������������� �������� (KidBoy), age=15 - ��������� (TeenBoy), ��������� ������� - ��������� �������.  3. � ������ main ������ Solution ������ ������� � ������ � ��� ����� getPerson ��� ���� � ������ �����������: 99, 4, 15.  ������ ��������� � �������. ����� ������ ���� ���������: 
Man{} 
KidBoy{} 
TeenBoy{}  
�������, ������� ������! ��� ������� Factory Method.

������� (3) 
1. ������ ����� female, � ������� ������ KidGirl, TeenGirl � Woman ���������� ������� �� ������ male.  
2. ������ � ������ female ������� FemaleFactory ����������� MaleFactory � ��� �� ��������� ��������� ��������.  
3. �������, ��� ������ � ���� ������? ��� ����� �������, ����� ����� ����� ���� ������������� ����� ����?  ����� - � ��������� �������.

������� (4) 
1. � ����� ������ ������ ��������� AbstractFactory, � ������� ������ ����� � �������� �����.  
2. �������� ��������� AbstractFactory � ����� ��������.  
3. � ����� ������ ������ ����� FactoryProducer, � ������� ������ ��������� ����������� ���� HumanFactoryType �� ���������� MALE, FEMALE.  
4. � FactoryProducer ������ ��������� ����������� ����� getFactory � ���������� HumanFactoryType. ���� ����� ������ ���������� ���� �� ������: ��� ����� MALE - MaleFactory, ��� FEMALE - FemaleFactory.  �������, ������� ������ ������! ��� ������� Abstract Factory. � ���� ���� �� �����, ����� ������� ������������.  ���� ������: 
1. ������ ������� Factory Method. 
2. ������ ������� Abstract Factory. 
3. +2 �������� ��� ����������� �������������.  ������ ����� ������������� ����� ��� �����, ����� ���������� ����!   ����������: 
1.?����� MaleFactory ������ ������������ ��������� AbstractFactory. 
2.?����� FemaleFactory ������ ������������ ��������� AbstractFactory. 
3.?� ������ FactoryProducer ������ ���� ������ enum HumanFactoryType �� ���������� MALE, FEMALE. 
4.?����� getFactory � ������ FactoryProducer ������ ��������� ���� �������� ���� HumanFactoryType. 
5.?����� getFactory � ������ FactoryProducer ������ ���������� ���� �� ������ � ����������� �� ��������� ���������.
*/

package com.javarush.task.task37.task3702;

public class Solution {
    public static void main(String[] args) {
        AbstractFactory factory = FactoryProducer.getFactory(FactoryProducer.HumanFactoryType.FEMALE);
        useFactory(factory);

        factory = FactoryProducer.getFactory(FactoryProducer.HumanFactoryType.MALE);
        useFactory(factory);
    }

    public static void useFactory(AbstractFactory factory) {
        System.out.println(factory.getPerson(99));
        System.out.println(factory.getPerson(4));
        System.out.println(factory.getPerson(15));
    }

}
