import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) throws IOException, InvocationTargetException, ClassNotFoundException {

        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);

    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) throws IOException, InvocationTargetException, ClassNotFoundException {
        Set<Animal> setAnimals = new HashSet<>();
        File[] files = new File(URLDecoder.decode(pathToAnimals, "UTF-8")).listFiles((p1, p2) -> p2.toLowerCase().endsWith(".class"));
        for (File file: files) {

            byte[] buffer = Files.readAllBytes(file.toPath());
            Class<?> clazz = new ClassLoader() {

                public Class<?> findClass(byte[] clazz) {
                    return super.defineClass(null, clazz, 0, clazz.length);
                }
            }.findClass(buffer);
            Class<?>[] interfaces = clazz.getInterfaces();
            boolean isAnimal = false;
            for (Class<?> i: interfaces) {
                if (i.equals(Animal.class)) {
                    isAnimal = true;
                    break;
                }
            }
            try {
//                Constructor<?>[] constructors = clazz.getConstructors();
//                for (Constructor c: constructors) {
//                    System.out.println(c + " " + clazz);
//                    if (c.getParameterCount() != 0) {
//                        Animal a = (Animal) c.newInstance(new Object[]{new Integer(10)});
//                    }
//                }

//                Constructor<?> constructor = clazz.getConstructor(Class.forName("java.lang.Integer")); // get constructor with parametr Integer
                Constructor<?> constructor = clazz.getConstructor();
                if (isAnimal) {
//                    setAnimals.add((Animal) clazz.getConstructor(Class.forName("java.lang.Integer")).newInstance(new Object[]{new Integer(10)})); // add new Sheep
                    setAnimals.add((Animal) clazz.newInstance()); // add only Cat
                }

            } catch (NoSuchMethodException e) {
            } catch (SecurityException e) {
            } catch (IllegalAccessException e) {
            } catch (InstantiationException e) {
            }
        }
        return setAnimals;
    }

}
