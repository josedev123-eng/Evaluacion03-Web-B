package com.tecsup.recibo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ReciboDTO {

    @NotNull(message = "El idCliente es obligatorio")
    @Positive(message = "El idCliente debe ser mayor a 0")
    private Long idCliente;

    @NotNull(message = "El idServicio es obligatorio")
    @Positive(message = "El idServicio debe ser mayor a 0")
    private Long idServicio;

    @NotBlank(message = "El periodo es obligatorio")
    @Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])", message = "El periodo debe tener el formato AAAA-MM (ej. 2026-11)")
    private String periodo;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    // getters y setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
