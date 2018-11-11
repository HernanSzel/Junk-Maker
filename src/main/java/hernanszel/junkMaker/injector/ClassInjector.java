package hernanszel.junkMaker.injector;

import hernanszel.junkMaker.analyzer.FieldInfo;

import java.util.Collection;

public interface ClassInjector {

    <T> void injectValues(T target, Collection<FieldInfo> allFields);

}
