package hernanszel.junkMaker.generator;

public interface ValueGenerator {

    <T> T generateValue(Class<T> type);

}
