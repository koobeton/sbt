package lesson5.store;

import java.util.List;

public interface Store {

    void save(String t);

    List<String> getAll();
}
