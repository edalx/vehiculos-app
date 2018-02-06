package ec.edu.uce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <> by edd_a on 12/11/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehiculo {
    public static final String TABLE_NAME = "Vehiculo";
    private Integer id;
    private String placa;
    private String marca;
    private String fecFab;
    private Double costo;
    private boolean matriculado;
    private String color;
    private String foto;
    private boolean estado;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String marca, String fecFab, Double costo, boolean matriculado, String color, String foto, boolean estado) {
        this.placa = placa;
        this.marca = marca;
        this.fecFab = fecFab;
        this.costo = costo;
        this.matriculado = matriculado;
        this.color = color;
        this.foto = foto;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFecFab() {
        return fecFab;
    }

    public void setFecFab(String fecFab) {
        this.fecFab = fecFab;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", fecFab='" + fecFab + '\'' +
                ", costo=" + costo +
                ", matriculado=" + matriculado +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Vehiculo && ((Vehiculo) obj).getPlaca().equals(placa);
    }

    public String toDisplay() {
        return placa + ", " + marca + " | " + costo;
    }
}
