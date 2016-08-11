package lesson7.ncdfe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URL;
import java.net.URLClassLoader;

public class NCDFEClassLoaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void noClassDefFoundError() throws Exception {

        thrown.expect(NoClassDefFoundError.class);

        URL url = NCDFESource.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();

        URLClassLoader loader = new NCDFEClassLoader(new URL[]{url});
        loader.loadClass(NCDFESource.class.getName());
    }
}