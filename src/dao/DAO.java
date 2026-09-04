package dao;

import java.util.List;

/**
 *
 * @author Ian
 */
public interface DAO<T> {

    void guardar(List<T> entidades);

    List<T> leer();

}
