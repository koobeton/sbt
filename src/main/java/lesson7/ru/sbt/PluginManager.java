package lesson7.ru.sbt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {

    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        try {
            URLClassLoader loader = new PluginClassLoader(getUrls(pluginName));
            return (Plugin) loader.loadClass(pluginClassName).newInstance();
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException("Exception at runtime: " + e.getMessage(), e);
        }
    }

    private URL[] getUrls(String pluginName) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("file://");
        String rootDir = new File(pluginRootDirectory).getCanonicalPath().replaceAll("\\\\", "/");
        sb.append(rootDir.endsWith("/") ? rootDir : rootDir + "/");
        sb.append(pluginName.endsWith("/") ? pluginName : pluginName + "/");
        return new URL[]{new URL(sb.toString())};
    }
}
