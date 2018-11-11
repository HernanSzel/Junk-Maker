package hernanszel.junkMaker.injector;

import hernanszel.junkMaker.analyzer.FieldInfo;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.Boolean.TRUE;

public class BasicClassInjector implements ClassInjector {

    @Override
    public <T> void injectValues(T target, Collection<FieldInfo> allFields) {
        allFields.stream().forEach(fieldInfo -> {
            try {
                FieldUtils.writeField(target, fieldInfo.name, fieldInfo.value, TRUE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }



    private void injectValue(Object newObject) {
        Field[] fields = newObject.getClass().getDeclaredFields();

        Arrays.stream(fields).forEach(p-> setRandomValue(p, newObject));
    }

    private void setRandomValue(Field field, Object obj) {
        /*
        field.setAccessible(TRUE);
        try {
            field.set(obj, primitivesGenerator.generateValue(field.getType()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        */
    }
}
