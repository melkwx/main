package seedu.address.commons.core;

/**
 * Mode which the app is running in
 */
public class AppMode {

    public static int MODE;

    public static void init () {
        MODE = 1;
    }

    public static void setModeMember () {
        MODE = 1;
    }

    public static void setModeActivity () {
        MODE = 2;
    }

    public static int getMODE () {
        return MODE;
    }

}
