package hernanszel.junkMaker.utils;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DummyClass extends SuperDummyClass {

    private byte dummyByte;
    private short dummyShort;
    private int dummyInt;
    private long dummyLong;
    private float dummyFloat;
    private double dummyDouble;
    private char dummyChar;
    private String dummyString;
    private boolean dummyBoolean;
    private DummyInnerClass innerClass;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public DummyInnerClass getInnerClass() {
        return innerClass;
    }
}
