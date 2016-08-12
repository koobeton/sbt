package lesson7.ru.sbt.plugins.plugin;

import lesson7.ru.sbt.BrowserClass;
import lesson7.ru.sbt.Plugin;

public class CoolPlugin implements Plugin {

    @Override
    public String doUsefull() {
        return new BrowserClass().about();
    }
}
