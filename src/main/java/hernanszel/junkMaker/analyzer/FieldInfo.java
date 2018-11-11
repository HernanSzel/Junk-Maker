package hernanszel.junkMaker.analyzer;

public class FieldInfo {

    public String name;
    public Class classType;
    public Object value;

    public FieldInfo(String name, Class type, Object value){
        this.name = name;
        this.classType = type;
        this.value = value;
    }
}
