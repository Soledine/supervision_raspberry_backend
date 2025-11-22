package fr.cnam.serverMonitor.repository;

import fr.cnam.serverMonitor.entite.CpuMesure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesureCpuRepository extends JpaRepository<CpuMesure,Long> {

    CpuMesure findFirstByOrderByIdDesc();
}
