package fr.cnam.serverMonitor.entite;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "mesure_network")
public class NetworkInterfaceMesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="bytes_received",nullable = false)
    private BigDecimal bytesReceived;

    @Column(name="bytes_sent",nullable = false)
    private BigDecimal bytesSent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(BigDecimal bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public BigDecimal getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(BigDecimal bytesSent) {
        this.bytesSent = bytesSent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        NetworkInterfaceMesure that = (NetworkInterfaceMesure) o;
        return Objects.equals(bytesReceived, that.bytesReceived) && Objects.equals(bytesSent, that.bytesSent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bytesReceived, bytesSent);
    }

    public NetworkInterfaceMesure(String name, BigDecimal bytesReceived, BigDecimal bytesSent) {
        this.name = name;
        this.bytesReceived = bytesReceived;
        this.bytesSent = bytesSent;
    }

    public NetworkInterfaceMesure() {
    }
}
