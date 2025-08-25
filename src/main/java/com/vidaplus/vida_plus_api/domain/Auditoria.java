package com.vidaplus.vida_plus_api.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria", indexes = {
        @Index(name = "idx_aud_data", columnList = "dataHora"),
        @Index(name = "idx_aud_rota", columnList = "rota")
})
public class Auditoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String metodo;       // GET/POST/PUT/DELETE
    @Column(nullable = false) private String rota;         // /pacientes, /profissionais/1 ...
    @Column(nullable = false) private int status;          // 200, 201, 400, 401, 404, 409...
    private String usuario;                                // e-mail/username (ou "anon")
    private String ip;                                     // remoto
    @Column(nullable = false) private LocalDateTime dataHora;

    // getters/setters
    public Long getId() { return id; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public String getRota() { return rota; }
    public void setRota(String rota) { this.rota = rota; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
