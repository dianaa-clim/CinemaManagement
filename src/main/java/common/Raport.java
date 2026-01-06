package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Raport implements Serializable {
    private int idRaport;
    private int idAngajat;
    private String raportType;
    private String period;
    private LocalDate raportDate;

    public Raport(String raportType, String period, LocalDate raportDate) {
        this.raportType = raportType;
        this.period = period;
        this.raportDate = raportDate;
    }
    public Raport(int idAngajat, String raportType, String period, LocalDate raportDate) {
        this.idAngajat = idAngajat;
        this.raportType = raportType;
        this.period = period;
        this.raportDate = raportDate;
    }

    public int getIdAngajat() {
        return idAngajat;
    }
    public void setIdAngajat(int idAngajat) {
        this.idAngajat = idAngajat;
    }
    public String getRaportType() {
        return raportType;
    }
    public void setRaportType(String raportType) {
        this.raportType = raportType;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public LocalDate getRaportDate() {
        return raportDate;
    }
    public void setRaportDate(LocalDate raportDate) {
        this.raportDate = raportDate;
    }
    public int getIdRaport() {
        return idRaport;
    }
    public void setIdRaport(int idRaport) {
        this.idRaport = idRaport;
    }
}
