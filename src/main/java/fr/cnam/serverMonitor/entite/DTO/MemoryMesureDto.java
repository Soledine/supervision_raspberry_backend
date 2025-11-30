package fr.cnam.serverMonitor.entite.DTO;

import com.sun.jna.Memory;
import fr.cnam.serverMonitor.Utils;
import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.entite.MemoryMesure;

import java.math.BigDecimal;

public record MemoryMesureDto(String time,
                              BigDecimal memory) {

    public MemoryMesureDto(MemoryMesure mesure){
        this(mesure.getTime().format(Utils.dateFormatter()),mesure.getMemory());
    }
}
