package adl.utils;

public final class RBGUtil {
    public static int RGB(int red, int green, int blue) {
        return red << 16 | green << 8 | blue;
    }
}
