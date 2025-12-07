package fr.cnam.serverMonitor.service;


import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.entite.MemoryMesure;
import fr.cnam.serverMonitor.entite.NetworkInterfaceMesure;
import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import fr.cnam.serverMonitor.repository.MesureMemoryRepository;
import fr.cnam.serverMonitor.repository.MesureNetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MesureService {

    @Autowired
    final private MesureCpuRepository mesureCpuRepository;

    @Autowired
    final private MesureMemoryRepository mesureMemoryRepository;

    @Autowired
    final private MesureNetworkRepository mesureNetworkRepository;

    public MesureService(MesureCpuRepository mesureCpuRepository,MesureMemoryRepository mesureMemoryRepository,MesureNetworkRepository mesureNetworkRepository) {
        this.mesureCpuRepository = mesureCpuRepository;
        this.mesureMemoryRepository=mesureMemoryRepository;
        this.mesureNetworkRepository=mesureNetworkRepository;
    }

    @Scheduled(fixedRate = 3000)
    public void collectAndSaveMetrics() throws InterruptedException{


        SystemInfo si = new SystemInfo();


        // donnees cpu
        CpuMesure mesureCpu=new CpuMesure();
        CentralProcessor processor = si.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        TimeUnit.SECONDS.sleep(1);
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        mesureCpu.setCpuLoad(new BigDecimal(cpuLoad));
        mesureCpu.setTime(LocalDateTime.now());
        mesureCpuRepository.save(mesureCpu);
        System.out.println("nouvelle mesure cpu en bdd : "+mesureCpu);

        //donnees memoire
        MemoryMesure mesureMemoire=new MemoryMesure();
        BigDecimal dispo = new BigDecimal(si.getHardware().getMemory().getAvailable()*100);
        BigDecimal total = new BigDecimal(si.getHardware().getMemory().getTotal());
        BigDecimal percent = dispo.divide(total,2, RoundingMode.CEILING);
        mesureMemoire.setMemory(percent);
        mesureMemoire.setTime(LocalDateTime.now());
        mesureMemoryRepository.save(mesureMemoire);
        System.out.println("nouvelle mesure memoire en bdd : "+mesureMemoire);

        List<NetworkIF> networkInterfaces =si.getHardware().getNetworkIFs();


        try {
        NetworkInterfaceMesure mesureReseau = networkInterfaces.stream().filter(iF -> iF.getBytesRecv()!=0 && iF.getBytesSent()!=0)
                .map(iF -> new NetworkInterfaceMesure(iF.getName(),new BigDecimal(iF.getBytesRecv()),new BigDecimal(iF.getBytesSent())))
                .distinct().toList().get(0);

        System.out.printf("Network Interface: %s - Received: %s, Sent: %s%n",mesureReseau.getName(), mesureReseau.getBytesReceived().toString(), mesureReseau.getBytesSent().toString());

        mesureNetworkRepository.save(mesureReseau);

        }

        catch(IndexOutOfBoundsException e) {System.out.printf("aucune interface reseau active");}

    }
}
