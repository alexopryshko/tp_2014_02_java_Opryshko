package resourcesSystem;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Object createInstance(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return clazz != null ? clazz.newInstance() : null;
        }
        catch (InstantiationException |
               IllegalAccessException |
               SecurityException e
              ) {
            e.printStackTrace();
            return null;
        }
    }
    public static void setFieldValue(Object object, String element, String value) {
        try {
            Field field = object.getClass().getDeclaredField(element);
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                field.set(object, value);
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.decode(value));
            }

            field.setAccessible(false);
        } catch (SecurityException |
                 NoSuchFieldException |
                 IllegalArgumentException |
                 IllegalAccessException e
                ) {
            e.printStackTrace();
        }
    }
}
