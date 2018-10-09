package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/* 
РћСЃРІР°РёРІР°РµРј ClassLoader Рё Reflection
Аргументом для класса Solution является абсолютный путь к пакету. Имя пакета может содержать File.separator. В этом пакете кроме скомпилированных классов (.class) могут находиться и другие файлы (например: .java). Известно, что каждый класс имеет конструктор без параметров и реализует интерфейс HiddenClass. Считай все классы с файловой системы, создай фабрику - реализуй метод getHiddenClassObjectByKey. 
Примечание: в пакете может быть только один класс, простое имя которого начинается с String key без учета регистра.   

Требования: 
1.?Реализуй метод scanFileSystem, он должен добавлять в поле hiddenClasses найденные классы. 
2.?Реализуй метод getHiddenClassObjectByKey, он должен создавать объект класса согласно условию задачи. 
3.?Метод main не изменяй. 
4.?Метод getHiddenClassObjectByKey не должен кидать исключений.
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File[] files = new File(packageName).listFiles();

        for (File f: files) {
            if (f.getName().endsWith(".class")) {
                byte[] bytes = new byte[(int) f.length()];
                try {
                    bytes = Files.readAllBytes(f.toPath());
                    Class clazz = new ClassLoader(){
                        public Class findClass(byte[] bytes) {
                            return super.defineClass(null, bytes, 0, bytes.length);
                        }
                    }.findClass(bytes);
                    hiddenClasses.add(clazz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        for (Class clazz: hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                Constructor c = null;
                try {
                    c = clazz.getDeclaredConstructor();
                    c.setAccessible(true);
                    return (HiddenClass) c.newInstance();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

