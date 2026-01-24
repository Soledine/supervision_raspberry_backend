package fr.cnam.serverMonitor;

import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class ServerMonitorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServerMonitorApplication.class, args);
	}
    @Autowired
    private MesureCpuRepository mesureCpuRepository;

    @Override
    public void run(String... args) throws Exception {

    }

}
