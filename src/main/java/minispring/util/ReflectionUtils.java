package minispring.util;

import minispring.beans.BeanException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author lihua
 * @since 2021/8/26
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException("Utility class!");
    }

    public static void setValue(Object bean, String fieldName, Object value) {
        Arrays.stream(getFields(bean))
                .filter(field -> field.getName().equals(fieldName))
                .peek(field -> field.setAccessible(true))
                .findAny()
                .ifPresent(field -> doSetValue(bean, field, value));
    }

    private static Field[] getFields(Object bean) {
        List<Field> fieldList = new ArrayList<>(128);
        Class realClass = bean.getClass();
        do {
            Field[] declaredFields = realClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            realClass = realClass.getSuperclass();
        } while (Objects.nonNull(realClass));
        return fieldList.toArray(new Field[0]);
    }

    private static void doSetValue(Object bean, Field field, Object value) {
        try {
            field.set(bean, value);
        } catch (IllegalAccessException e) {
            throw new BeanException("can't set value of field: ", e);
        }
    }
}
