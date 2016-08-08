package lesson6.testmodel;

public class Getter {

    public static Integer staticInteger;
    public static String staticString;
    public static Object staticObject;

    public int instanceInteger;
    public String instanceString;
    public Object instanceObject;

    public Number mismatchType;
    public String nonPublic;

    public String simple;

    public static Integer getStaticInteger() {
        return staticInteger;
    }

    public static String getStaticString() {
        return staticString;
    }

    public static Object getStaticObject() {
        return staticObject;
    }

    public int getInstanceInteger() {
        return instanceInteger;
    }

    public String getInstanceString() {
        return instanceString;
    }

    public Object getInstanceObject() {
        return instanceObject;
    }

    public Number getMismatchType() {
        return mismatchType;
    }

    protected String getNonPublic() {
        return nonPublic;
    }

    public String get() {
        return simple;
    }
}
