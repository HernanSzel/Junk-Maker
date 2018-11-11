package hernanszel.junkMaker;

import hernanszel.junkMaker.analyzer.ClassAnalyzer;
import hernanszel.junkMaker.analyzer.FieldInfo;
import hernanszel.junkMaker.analyzer.PrimitiveClassAnalyzer;
import hernanszel.junkMaker.generator.PrimitivesGenerator;
import hernanszel.junkMaker.generator.ValueGenerator;
import hernanszel.junkMaker.injector.BasicClassInjector;
import hernanszel.junkMaker.injector.ClassInjector;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Function;

public class JunkMaker {

    private final Function<Class<?>, ?> pipeline = injectJunk(generateJunk(extractFieldInfo()));

    private final ClassAnalyzer defaultClassAnalyzer = new PrimitiveClassAnalyzer();
    private final ValueGenerator defaultPrimitivesGenerator = new PrimitivesGenerator();
    private final ClassInjector defaultInjector = new BasicClassInjector();

    private ClassAnalyzer classAnalyzer = defaultClassAnalyzer;
    private ValueGenerator valueGenerator = defaultPrimitivesGenerator;
    private ClassInjector valueInjector = defaultInjector;

    /*******************
    *        API
     *******************/
    public <T> T generateJunkFor(Class<T> clazz) {
        return this._generateJunkFor(clazz);
    }

    public void with(ClassAnalyzer classAnalyzer) {
        this.classAnalyzer = classAnalyzer;
    }

    public void with(ValueGenerator valueGenerator) {
        this.valueGenerator = valueGenerator;
    }

    public void with(ClassInjector injector) {
        this.valueInjector = injector;
    }


    private <T> T _generateJunkFor(Class<T> clazz) {
        return (T) pipeline.apply(clazz);
    }

    /*******************
     *     PIPELINE
     *******************/
    private Function<Class<?>, ?> injectJunk(Function<Class<?>, Pair<Class<?>, List<FieldInfo>>> junkGenerator) {
        return clazz -> _injectJunk(junkGenerator, clazz);
    }

    private Function<Class<?>, Pair<Class<?>, List<FieldInfo>>> generateJunk(Function<Class<?>, Pair<Class<?>, List<FieldInfo>>> clazzAnalyzer) {
        return (Class<?> clazz) -> _generateJunk(clazzAnalyzer, clazz);
    }

    private Function<Class<?>, Pair<Class<?>, List<FieldInfo>>> extractFieldInfo() {
        return (Class<?> clazz) -> Pair.of(clazz, _extractFieldInfo(clazz));
    }

    /*******************
     *     IMPL
     *******************/
    private List<FieldInfo> _extractFieldInfo(Class<?> clazz) {
        return classAnalyzer.getClassAndSuperClassesFieldInfo(clazz);
    }

    private Object _injectJunk(Function<Class<?>, Pair<Class<?>, List<FieldInfo>>> junkGenerator, Class<?> clazz) {
        Pair<Class<?>, List<FieldInfo>> clazzAndFields = junkGenerator.apply(clazz);

        Object target = null;
        try {
            target = generateNewInstance(clazzAndFields.getLeft());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        valueInjector.injectValues(target, clazzAndFields.getRight());

        return target;
    }

    private Pair<Class<?>, List<FieldInfo>> _generateJunk(Function<Class<?>, Pair<Class<?>, List<FieldInfo>>> clazzAnalyzer, Class<?> clazz) {
        Pair<Class<?>, List<FieldInfo>> clazzAndFields = clazzAnalyzer.apply(clazz);

        clazzAndFields.getRight().stream().forEach(fieldInfo -> {
            Object generatedValue = valueGenerator.generateValue(fieldInfo.classType);
            System.out.println("Generated: "+generatedValue.toString());
            fieldInfo.value = generatedValue;
        });

        return clazzAndFields;
    }

    /*******************
     *     HELPER
     *******************/
    private <T> T generateNewInstance(Class<T> clazz) throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch (Throwable e) {
            throw new InstantiationException(String.format("Can not instantiate object %s", clazz.getSimpleName()));
        }
    }

}
