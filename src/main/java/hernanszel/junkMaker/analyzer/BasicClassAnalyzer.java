package hernanszel.junkMaker.analyzer;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

public class BasicClassAnalyzer implements ClassAnalyzer {

    @Override
    public <T> List<FieldInfo> getClassAndSuperClassesFieldInfo(Class<T> clazz) {
        List<FieldInfo> attributes = new ArrayList<>();

        while (!isObjectClass(clazz)){
            attributes.addAll(getClassAttributes(clazz));

            clazz = (Class<T>) clazz.getSuperclass();
        }

        return attributes;
    }

    private List<FieldInfo> getClassAttributes(Class targetClazz) {
        Field[] fields = targetClazz.getDeclaredFields();

        return Arrays.stream(fields)
                .map(this::extractFieldInfo)
                .collect(Collectors.toList());
    }

    private FieldInfo extractFieldInfo(Field field){
        field.setAccessible(TRUE);

        String name = field.getName();
        Object value = FieldUtils.getField(field.getDeclaringClass(), field.getName(), TRUE);

        return new FieldInfo(name, field.getType(), value);
    }

    private boolean isObjectClass(Class superclass) {
        return superclass != null && superclass == Object.class;
    }
}
