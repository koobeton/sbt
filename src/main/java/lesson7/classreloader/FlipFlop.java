package lesson7.classreloader;

public class FlipFlop implements Reloadable {

    @Override
    public void doSomething() {
        System.out.println("FLIP");
    }
}
