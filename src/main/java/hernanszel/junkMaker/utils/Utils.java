package hernanszel.junkMaker.utils;

public class Utils {

    public static <T> T generateNewInstance(Class<T> clazz) throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch (Throwable e) {
            throw new InstantiationException(String.format("Can not instantiate object %s", clazz.getSimpleName()));
        }
    }

}
