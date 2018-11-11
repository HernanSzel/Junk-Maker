package hernanszel.junkMaker;

import hernanszel.junkMaker.utils.DummyClass;

public class Main {

    public static void main(String[] args) {
        JunkMaker junkMaker = new JunkMaker();

        DummyClass junk = junkMaker.generateJunkFor(DummyClass.class);

        System.out.println(junk.toString());

        return;
    }

}
