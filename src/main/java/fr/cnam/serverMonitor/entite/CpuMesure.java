package fr.cnam.serverMonitor.entite;


import fr.cnam.serverMonitor.enumerations.TypeMesure;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "mesure_cpu")
public class CpuMesure{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Override
    public String toString() {
        return "CpuMesure{" + "id=" + id + ", cpuLoad=" + cpuLoad + '}';
    }
}
