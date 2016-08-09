package lesson7.classreloader;

public class Authorizer implements Reloadable {

    @Override
    public boolean check(String accessCode) {
        return "123456".equals(accessCode);
        //return true;
    }
}
