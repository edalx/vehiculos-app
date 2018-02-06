package ec.edu.uce.servicios;

import java.util.List;

import ec.edu.uce.model.Usuario;
import ec.edu.uce.storage.db.UsuarioStorage;
import ec.edu.uce.util.CustomException;

/**
 * <> by dacop on 03/02/2018.
 */

public class UsuarioService {

    private final UsuarioStorage dao = UsuarioStorage.getInstance();

    public List<Usuario> getData() {
        return dao.listar();
    }

    public String registar(String user, String pass) throws CustomException {
        Usuario u = dao.buscarPorParametro(user);

        if (u != null) {
            throw new CustomException("Usuario ya existe");
        }
        return dao.crear(new Usuario(user, pass));
    }

    public void login(String user, String pass) throws CustomException {
        Usuario u = dao.buscarPorParametro(user);
        if (u == null || !u.getClave().equals(pass)) {
            throw new CustomException("Usuario y/p Contraseña incorrecta");
        }
    }

    public void clearData() {
        dao.clearData(Usuario.TABLE_NAME);
    }
}
