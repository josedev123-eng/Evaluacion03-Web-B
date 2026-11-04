package com.tecsup.servicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServicioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "El nombre no debe superar los 60 caracteres")
    private String nombre;

    @Size(max = 200, message = "La descripcion no debe superar los 200 caracteres")
    private String descripcion;

    @NotBlank(message = "La empresa es obligatoria")
    @Size(max = 80, message = "La empresa no debe superar los 80 caracteres")
    private String empresa;

    @NotNull(message = "La tarifa mensual es obligatoria")
    @Positive(message = "La tarifa mensual debe ser mayor a 0")
    private Double tarifaMensual;

    // si no se envia, el servicio se registra como activo
    private Boolean activo;

    // getters y setters
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
