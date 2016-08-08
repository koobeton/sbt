package lesson6.testmodel;

public class Setter {

    public static Number staticInteger;
    public static String staticString;
    public static Object staticObject;

    public int instanceInteger;
    public String instanceString;
    public Object instanceObject;

    public Integer mismatchType;
    public String nonPublic;
    public String getterless;

    public String simple;

    public static void setStaticInteger(Number staticInteger) {
        Setter.staticInteger = staticInteger;
    }

    public static void setStaticString(String staticString) {
        Setter.staticString = staticString;
    }

    public static void setStaticObject(Object staticObject) {
        Setter.staticObject = staticObject;
    }

    public void setInstanceInteger(int instanceInteger) {
        this.instanceInteger = instanceInteger;
    }

    public void setInstanceString(String instanceString) {
        this.instanceString = instanceString;
    }

    public void setInstanceObject(Object instanceObject) {
        this.instanceObject = instanceObject;
    }

    public void setMismatchType(Integer mismatchType) {
        this.mismatchType = mismatchType;
    }

    protected void setNonPublic(String nonPublic) {
        this.nonPublic = nonPublic;
    }

    public void setGetterless(String getterless) {
        this.getterless = getterless;
    }

    public void set(String simple) {
        this.simple = simple;
    }
}
