package hernanszel.junkMaker.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.apache.commons.lang.RandomStringUtils.random;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.math.RandomUtils.*;


public class PrimitivesGenerator implements ValueGenerator {

    private final Map<Class<?>, Supplier<?>> generators;

    private final int ONE = 1;
    private final int ZERO = 0;

    public PrimitivesGenerator() {
        generators = new HashMap<>();

        generators.put(byte.class, () -> generateRandomByte());
        generators.put(short.class, () -> generateRandomShort());
        generators.put(int.class, () -> generateRandomInt());
        generators.put(long.class, () -> generateRandomLong());
        generators.put(float.class, () -> generateRandomFloat());
        generators.put(double.class, () -> generateRandomDouble());
        generators.put(char.class, () -> generateRandomChar());
        generators.put(String.class, () -> generateRandomStr());
        generators.put(boolean.class, () -> generateRandomBoolean());
    }

    @Override
    public <T> T generateValue(Class<T> type) {
        return (T) generators.get(type).get();
    }

    //GENERATORS
    private int generateRandomInt(){return nextInt();}

    private long generateRandomLong() { return nextLong();}

    private short generateRandomShort() {
        return (short) generateRandomInt();
    }

    private float generateRandomFloat() {
        return nextFloat();
    }

    private double generateRandomDouble() {
        return nextDouble();
    }

    private char generateRandomChar() {return random(ONE).charAt(ZERO);}

    private String generateRandomStr(){return randomAlphanumeric(15);}

    private byte generateRandomByte() {
        return (byte) generateRandomInt();
    }

    private boolean generateRandomBoolean() {
        return isEven(generateRandomShort());
    }

    private boolean isEven(short val) {
        return val % 2 == 0;
    }

}
