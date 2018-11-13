package hernanszel.junkMaker;

import hernanszel.junkMaker.analyzer.BasicClassAnalyzer;
import hernanszel.junkMaker.analyzer.ClassAnalyzer;
import hernanszel.junkMaker.analyzer.FieldInfo;
import hernanszel.junkMaker.generator.BasicGenerator;
import hernanszel.junkMaker.generator.ValueGenerator;
import hernanszel.junkMaker.injector.BasicClassInjector;
import hernanszel.junkMaker.injector.ClassInjector;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Function;

import static hernanszel.junkMaker.utils.Utils.generateNewInstance;

public class JunkMaker {

    private final Function<Class<?>, ?> pipeline = injectJunk(generateJunk(extractFieldInfo()));

    private final ClassAnalyzer defaultClassAnalyzer = new BasicClassAnalyzer();
    private final ValueGenerator defaultPrimitivesGenerator = new BasicGenerator();
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

    public void setAnalyzer(ClassAnalyzer classAnalyzer) {
        this.classAnalyzer = classAnalyzer;
    }

    public void withGenerator(ValueGenerator valueGenerator) {
        this.valueGenerator = valueGenerator;
    }

    public void withInjector(ClassInjector injector) {
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
            fieldInfo.value = valueGenerator.generateValue(fieldInfo.classType);
        });

        return clazzAndFields;
    }

}
