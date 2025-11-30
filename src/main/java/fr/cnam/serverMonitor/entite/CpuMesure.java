package fr.cnam.serverMonitor.entite;


import fr.cnam.serverMonitor.enumerations.TypeMesure;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mesure_cpu")
public class CpuMesure{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="time",nullable = false)
    private LocalDateTime time;

    @Column(name="cpu_load",precision = 3, scale = 2, nullable = false)
    private BigDecimal cpuLoad;

    public CpuMesure() {
    }

    public BigDecimal getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(BigDecimal cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CpuMesure{" + "id=" + id + ", time=" + time + ", cpuLoad=" + cpuLoad + '}';
    }
}
