package com.tecsup.servicio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false, length = 80)
    private String empresa;

    @Column(name = "tarifa_mensual", nullable = false)
    private Double tarifaMensual;

    @Column(nullable = false)
    private Boolean activo;

    public Servicio() {
    }

    public Servicio(Long id, String nombre, String descripcion, String empresa, Double tarifaMensual, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empresa = empresa;
        this.tarifaMensual = tarifaMensual;
        this.activo = activo;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Double getTarifaMensual() {
        return tarifaMensual;
    }

    public void setTarifaMensual(Double tarifaMensual) {
        this.tarifaMensual = tarifaMensual;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
