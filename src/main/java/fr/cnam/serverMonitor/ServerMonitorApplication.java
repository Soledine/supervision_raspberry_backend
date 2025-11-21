package fr.cnam.serverMonitor;

import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.enumerations.TypeMesure;
import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class ServerMonitorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServerMonitorApplication.class, args);
	}

    @Autowired
    private MesureCpuRepository mesureCpuRepository;

    @Override
    public void run(String... args) throws Exception {
        CpuMesure mesure=new CpuMesure();

        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        TimeUnit.SECONDS.sleep(1);
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        mesure.setCpuLoad(new BigDecimal(cpuLoad));
        mesureCpuRepository.save(mesure);
    }

}
