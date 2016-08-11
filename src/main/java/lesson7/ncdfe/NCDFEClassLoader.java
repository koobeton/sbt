package lesson7.ncdfe;

import java.net.URL;
import java.net.URLClassLoader;

public class NCDFEClassLoader extends URLClassLoader {

    public NCDFEClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }
}
