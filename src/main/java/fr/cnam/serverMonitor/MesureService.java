package fr.cnam.serverMonitor;


import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class MesureService {

    @Autowired
    private MesureCpuRepository mesureCpuRepository;

    public MesureService(MesureCpuRepository mesureCpuRepository) {
        this.mesureCpuRepository = mesureCpuRepository;
    }

    @Scheduled(fixedRate = 3000)
    public void collectAndSaveMetrics() throws InterruptedException{

        CpuMesure mesure=new CpuMesure();
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        TimeUnit.SECONDS.sleep(1);
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        mesure.setCpuLoad(new BigDecimal(cpuLoad));
        mesureCpuRepository.save(mesure);
        System.out.println("nouvelle mesure en bdd : "+mesure);
    }
}
