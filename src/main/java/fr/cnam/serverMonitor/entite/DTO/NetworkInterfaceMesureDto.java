package fr.cnam.serverMonitor.entite.DTO;

import fr.cnam.serverMonitor.Utils;
import fr.cnam.serverMonitor.entite.NetworkInterfaceMesure;

import java.math.BigDecimal;

public record NetworkInterfaceMesureDto(String name,BigDecimal bytesReceived, BigDecimal bytesSent,String time) {


    public NetworkInterfaceMesureDto(NetworkInterfaceMesure ifMesure){
        this(ifMesure.getName(),ifMesure.getBytesReceived(),ifMesure.getBytesSent(),ifMesure.getTime().format(Utils.dateFormatter()));
    }


}
