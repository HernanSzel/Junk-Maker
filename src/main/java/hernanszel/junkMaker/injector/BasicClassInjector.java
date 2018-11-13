package hernanszel.junkMaker.injector;

import hernanszel.junkMaker.analyzer.FieldInfo;

import java.util.Collection;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

public class BasicClassInjector implements ClassInjector {

    @Override
    public <T> void injectValues(T target, Collection<FieldInfo> allFields) {
        allFields.stream().forEach(fieldInfo -> {
            try {
                writeField(target, fieldInfo.name, fieldInfo.value, TRUE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}
