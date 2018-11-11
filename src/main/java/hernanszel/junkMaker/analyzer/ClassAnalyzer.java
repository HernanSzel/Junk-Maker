package hernanszel.junkMaker.analyzer;

import java.util.List;

public interface ClassAnalyzer {

    <T> List<FieldInfo> getClassAndSuperClassesFieldInfo(Class<T> clazz);

}
