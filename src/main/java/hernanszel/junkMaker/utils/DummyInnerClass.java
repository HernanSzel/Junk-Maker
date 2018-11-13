package hernanszel.junkMaker.utils;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DummyInnerClass {

    private int innerInt;
    private String innerString;
    private RequestContextDTO wtfmen;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
