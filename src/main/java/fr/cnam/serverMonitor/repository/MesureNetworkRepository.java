package fr.cnam.serverMonitor.repository;

import fr.cnam.serverMonitor.entite.MemoryMesure;
import fr.cnam.serverMonitor.entite.NetworkInterfaceMesure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesureNetworkRepository extends JpaRepository<NetworkInterfaceMesure,Long> {
    NetworkInterfaceMesure findFirstByOrderByIdDesc();
}
