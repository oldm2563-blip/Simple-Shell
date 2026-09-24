package ma.youcode.lineperma.DAO;

interface Dao<T> {

    void save(T t);
    T findById(int id);
    void delete(int id);

}
