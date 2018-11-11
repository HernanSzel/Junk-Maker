package hernanszel.junkMaker.analyzer;

import java.util.Map;

public class ClassInfo {

    private String name;
    private Map<String, Object> attributes;


    public ClassInfo(String name, Map<String, Object> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

}
