package com.tecsup.recibo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "recibo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_cliente", "id_servicio", "periodo"}))
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // solo se guarda el id, el cliente vive en bd_cliente (cliente-service)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    // solo se guarda el id, el servicio vive en bd_servicio (servicio-service)
    @Column(name = "id_servicio", nullable = false)
    private Long idServicio;

    // mes que se cobra, formato AAAA-MM (ej. 2026-11)
    @Column(nullable = false, length = 7)
    private String periodo;

    @Column(nullable = false)
    private Double monto;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false, length = 10)
    private EstadoPago estadoPago;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    public Recibo() {
    }

    public Recibo(Long id, Long idCliente, Long idServicio, String periodo, Double monto, LocalDate fechaVencimiento) {
        this.id = id;
        this.idCliente = idCliente;
        this.idServicio = idServicio;
        this.periodo = periodo;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
}
