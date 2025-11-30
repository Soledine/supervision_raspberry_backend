package fr.cnam.serverMonitor.entite.DTO;

import fr.cnam.serverMonitor.Utils;
import fr.cnam.serverMonitor.entite.CpuMesure;

import java.math.BigDecimal;

public record CpuMesureDto(String time,
                           BigDecimal cpuLoad) {

    public CpuMesureDto(CpuMesure mesure){
        this(mesure.getTime().format(Utils.dateFormatter()),mesure.getCpuLoad());
    }
}
