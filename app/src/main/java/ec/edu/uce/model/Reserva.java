package ec.edu.uce.model;

/**
 * <> by dacop on 03/02/2018.
 */

public class Reserva {
    public static final String TABLE_NAME = "Reserva";
    private Integer id;
    private String email;
    private String celular;
    private String fechaPrestamo;
    private String fechaEntrega;
    private Double valor;
    private Integer vehiculo;
    private String vehiculoTxt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Integer vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getVehiculoTxt() {
        return vehiculoTxt;
    }

    public void setVehiculoTxt(String vehiculoTxt) {
        this.vehiculoTxt = vehiculoTxt;
    }
}
