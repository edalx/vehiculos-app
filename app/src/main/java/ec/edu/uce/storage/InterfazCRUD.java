package ec.edu.uce.storage;

import java.util.Collection;

/**
 * Esta interface nos permite manejar las operaciones básicas al momento de almacenar los datos del programa.
 * (Insertar, Editar, Eliminar, Buscar y Listar)
 *
 * @author Ing. Giovanny Moncayo Unda. MSc.
 * @version 2018-01-31
 */

public interface InterfazCRUD<T> {

    /**
     * Metodo que permite crear un nuevo Objeto de cualquier clase.
     *
     * @param obj Es el nuevo Objeto que se va a crear
     * @return Un mensaje para alertar al usuario
     */
    public String crear(T obj);

    /**
     * Metodo que permite actualizar el estado de un Objeto.
     *
     * @param id El id del Objeto a ser actualizado
     * @return Un mensaje para alertar al usuario
     */
    public String actualizar(T id);

    /**
     * Metodo que permite eliminar un Objeto
     *
     * @param id El id del Objeto a ser eliminado
     * @return Un mensaje para alertar al usuario
     */
    public boolean borrar(Object id);

    /**
     * Metodo que permite buscar un Objeto
     *
     * @param parametro El parametro del Objeto que se está buscando
     * @return El Objeto encontrado, si el objeto no existe, retorna null
     */
    public T buscarPorParametro(Object parametro);

    /**
     * Metodo que permita listar los Objetos
     *
     * @return La colección con el resultado
     */
    public Collection<T> listar();

}
