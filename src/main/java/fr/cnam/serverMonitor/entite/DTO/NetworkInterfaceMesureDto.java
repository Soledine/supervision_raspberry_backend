package fr.cnam.serverMonitor.entite.DTO;

import fr.cnam.serverMonitor.entite.NetworkInterfaceMesure;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record NetworkInterfaceMesureDto(String name,BigDecimal megaBytesReceived, BigDecimal megaBytesSent) {


    public NetworkInterfaceMesureDto(NetworkInterfaceMesure ifMesure){
        this(ifMesure.getName(),ifMesure.getBytesReceived().divide( new BigDecimal(1048576),0, RoundingMode.CEILING),ifMesure.getBytesSent().divide(new BigDecimal(1048576),0, RoundingMode.CEILING));
    }


}
