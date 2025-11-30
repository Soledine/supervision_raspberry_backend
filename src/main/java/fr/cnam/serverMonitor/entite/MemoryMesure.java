package fr.cnam.serverMonitor.entite;


import fr.cnam.serverMonitor.enumerations.TypeMesure;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mesure_memoire")
public class MemoryMesure{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="time",nullable = false)
    private LocalDateTime time;

    @Column(name="memory_available",precision = 3, scale = 2, nullable = false)
    private BigDecimal memory;

    public MemoryMesure() {
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public BigDecimal getMemory() {
        return memory;
    }

    public void setMemory(BigDecimal memory) {
        this.memory = memory;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MemoryMesure{" + "id=" + id + ", time=" + time + ", memoire=" + memory + '}';
    }
}
