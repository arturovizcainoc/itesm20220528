package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.inventarioTipo;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Inventario.
 */
@Document(collection = "inventario")
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("producto")
    private String producto;

    @Field("fecha_ingreso")
    private LocalDate fechaIngreso;

    @Field("fecha_salida")
    private LocalDate fechaSalida;

    @Field("tipo")
    private inventarioTipo tipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Inventario id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducto() {
        return this.producto;
    }

    public Inventario producto(String producto) {
        this.setProducto(producto);
        return this;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public LocalDate getFechaIngreso() {
        return this.fechaIngreso;
    }

    public Inventario fechaIngreso(LocalDate fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return this.fechaSalida;
    }

    public Inventario fechaSalida(LocalDate fechaSalida) {
        this.setFechaSalida(fechaSalida);
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public inventarioTipo getTipo() {
        return this.tipo;
    }

    public Inventario tipo(inventarioTipo tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(inventarioTipo tipo) {
        this.tipo = tipo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventario)) {
            return false;
        }
        return id != null && id.equals(((Inventario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventario{" +
            "id=" + getId() +
            ", producto='" + getProducto() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
