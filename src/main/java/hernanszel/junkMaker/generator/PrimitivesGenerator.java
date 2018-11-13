package hernanszel.junkMaker.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static hernanszel.junkMaker.utils.Constants.Numbers.ONE;
import static hernanszel.junkMaker.utils.Constants.Numbers.ZERO;
import static org.apache.commons.lang.math.RandomUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


public class PrimitivesGenerator implements ValueGenerator {

    private final Map<Class<?>, Supplier<?>> generators;

    public PrimitivesGenerator() {
        generators = new HashMap<>();

        generators.put(byte.class, this::generateRandomByte);
        generators.put(short.class, this::generateRandomShort);
        generators.put(int.class, this::generateRandomInt);
        generators.put(long.class, this::generateRandomLong);
        generators.put(float.class, this::generateRandomFloat);
        generators.put(double.class, this::generateRandomDouble);
        generators.put(char.class, this::generateRandomChar);
        generators.put(String.class, this::generateRandomStr);
        generators.put(boolean.class, this::generateRandomBoolean);
    }

    @Override
    public <T> T generateValue(Class<T> typeOfTarget) {
        return (T) generators.get(typeOfTarget).get();
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
