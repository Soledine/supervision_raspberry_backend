package fr.cnam.serverMonitor.repository;

import fr.cnam.serverMonitor.entite.MemoryMesure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesureMemoryRepository  extends JpaRepository<MemoryMesure,Long> {
    MemoryMesure findFirstByOrderByIdDesc();
}
