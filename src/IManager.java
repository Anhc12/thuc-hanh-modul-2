import java.util.List;

public interface IManager<T> {
    void add(T t);
    void delete(String id);
    List<T> getAll();
    T findById(String id);
    void search(String keyword);
}