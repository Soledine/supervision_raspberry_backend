package fr.cnam.serverMonitor.service;


import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.entite.MemoryMesure;
import fr.cnam.serverMonitor.entite.NetworkInterfaceMesure;
import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import fr.cnam.serverMonitor.repository.MesureMemoryRepository;
import fr.cnam.serverMonitor.repository.MesureNetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.NetworkIF;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MesureService {

    @Value("${seuilAlerteEspaceDisque}")
    private long seuilAlerteDisque;

    @Value("${nomEnvironnement}")
    private String nomEnvironnement;


    private final JavaMailSender mailSender;

    @Autowired
    final private MesureCpuRepository mesureCpuRepository;

    @Autowired
    final private MesureMemoryRepository mesureMemoryRepository;

    @Autowired
    final private MesureNetworkRepository mesureNetworkRepository;

    public MesureService(MesureCpuRepository mesureCpuRepository,MesureMemoryRepository mesureMemoryRepository,MesureNetworkRepository mesureNetworkRepository,JavaMailSender mailSender) {
        this.mesureCpuRepository = mesureCpuRepository;
        this.mesureMemoryRepository=mesureMemoryRepository;
        this.mesureNetworkRepository=mesureNetworkRepository;
        this.mailSender=mailSender;
    }

    @Scheduled(fixedRate = 3000)
    public void collecteMesure() throws InterruptedException{

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

        //reseau
        try {
        NetworkInterfaceMesure mesureReseau = networkInterfaces.stream().filter(iF -> iF.getBytesRecv()!=0 && iF.getBytesSent()!=0)
                .map(iF -> new NetworkInterfaceMesure(iF.getName(),new BigDecimal(iF.getBytesRecv()),new BigDecimal(iF.getBytesSent()),LocalDateTime.now()))
                .distinct().toList().get(0);

        System.out.printf("Network Interface: %s - Received: %s, Sent: %s%n",mesureReseau.getName(), mesureReseau.getBytesReceived().toString(), mesureReseau.getBytesSent().toString());

        mesureNetworkRepository.save(mesureReseau);

        }

        catch(IndexOutOfBoundsException e) {System.out.printf("aucune interface reseau active");}


    }

    public Iterable<FileStore> getFilestoreState(){



        return FileSystems.getDefault().getFileStores();
    }

    @Scheduled(fixedRate = 3600000)
    public List<FileStore> watchFileStore() throws InterruptedException{
        Iterator<FileStore> iterator = FileSystems.getDefault().getFileStores().iterator();
        List<FileStore> liste = new ArrayList<>();
        while(iterator.hasNext()) {
            FileStore store=null;
            try {
                store = iterator.next();
                long totalSpace = store.getTotalSpace();
                long freeSpace = store.getUsableSpace();
                long percentageOccupation;
                try {
                    percentageOccupation = 100 - freeSpace * 100 / totalSpace;
                    System.out.println("espace total sur " + store.toString() + " = " + totalSpace);
                    System.out.println("espace disponible sur " + store.toString() + " = " + freeSpace);
                    System.out.println("pourcentage occupation = " + percentageOccupation);
                }
                catch(ArithmeticException e){
                    System.out.println("erreur de calcul sur le disque " + store.toString());
                    continue;
                }

                if(percentageOccupation>seuilAlerteDisque) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo("soledine22@gmail.com");
                    message.setSubject("⚠️ Alerte espace disque Raspberry Pi");
                    message.setText("Alerte : l'utilisation du disque "+store.toString()+" a atteint " + percentageOccupation + "% sur l'environnement "+nomEnvironnement);
                    mailSender.send(message);

                }

                // on se limite aux disques reellement utilisés
                if (percentageOccupation>10)liste.add(store);

            }
            catch(IOException e){
                System.out.println("acces impossible sur le filestore "+store.name());
            }
        }

        System.out.println("surveillance des disques - nb de disque trouvés = "+liste.size());
        return liste;

    }
}
