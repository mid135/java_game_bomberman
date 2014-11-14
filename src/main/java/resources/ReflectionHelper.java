package resources;

import java.lang.reflect.Field;
import java.util.Map;

public class ReflectionHelper {

    public static Object createIntance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, String value) {
        Map tempMap;
        if (object instanceof Map) {
            tempMap = (Map) object;
            tempMap.put(fieldName, value);
        } else {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                if (field.getType().equals(String.class)) {
                    field.set(object, value);
                } else if (field.getType().equals(int.class)) {
                    field.set(object, Integer.decode(value));
                }

                field.setAccessible(false);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
