package ec.edu.uce.model;

/**
 * <> by dacop on 03/02/2018.
 */

public class Usuario {

    public static final String KEY_USUARIO = "USUARIO";
    public static final String KEY_CLAVE = "CLAVE";
    public static final String TABLE_NAME = "USUARIO";

    private String usuario;
    private String clave;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario() {

    }

    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
}
