package hernanszel.junkMaker.generator;

import hernanszel.junkMaker.JunkMaker;
import hernanszel.junkMaker.analyzer.BasicClassAnalyzer;
import hernanszel.junkMaker.analyzer.ClassAnalyzer;
import hernanszel.junkMaker.injector.BasicClassInjector;
import hernanszel.junkMaker.injector.ClassInjector;

import static hernanszel.junkMaker.utils.Constants.Numbers.ONE;
import static hernanszel.junkMaker.utils.Constants.Numbers.ZERO;
import static hernanszel.junkMaker.utils.Utils.generateNewInstance;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang.math.RandomUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;


public class BasicGenerator implements ValueGenerator {

    private final ValueGenerator primitiveGenerator = new PrimitivesGenerator();
    private final ClassAnalyzer classAnalyzer = new BasicClassAnalyzer();
    private final ClassInjector classInjector = new BasicClassInjector();

    @Override
    public <T> T generateValue(Class<T> typeOfTarget) {
        if(isStringOrPrimitive(typeOfTarget))
            return primitiveGenerator.generateValue(typeOfTarget);
        else {
            try {
                return handleNonPrimitiveType(typeOfTarget);
            } catch (InstantiationException e) {
                return null;
            }
        }
    }

    private <T> T handleNonPrimitiveType(Class<T> nonPrimitiveType) throws InstantiationException {
        final T target = generateNewInstance(nonPrimitiveType);

        classAnalyzer.getClassAndSuperClassesFieldInfo(nonPrimitiveType).stream().forEach(field -> {
            try {
                writeField(target, field.name, generateValue(field.classType), TRUE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return target;
    }

    private <T> boolean isStringOrPrimitive(Class<T> typeOfTarget) {
        return typeOfTarget.isPrimitive() || typeOfTarget.equals(String.class);
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
