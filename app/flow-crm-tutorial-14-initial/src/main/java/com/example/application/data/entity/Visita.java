package com.example.application.data.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;

@Entity
public class Visita extends AbstractEntity{
    private String name;
    private LocalDate dataVisita;
    private String indirizzo;
    private LocalDate dataPrenotazione;
    private Boolean trasporto=false;
    private LocalTime ora;

    public enum StatoVisita{prenotata,schedulata,svolta}
    private StatoVisita statoVisita=StatoVisita.svolta;

    public Visita(){}
    public Visita(String name){this.name=name; this.dataVisita=LocalDate.now(); this.ora=LocalTime.now();
         if(dataVisita.isAfter(LocalDate.now())){
            if(Math.random()>0.5)this.statoVisita=StatoVisita.schedulata;
            else{statoVisita=StatoVisita.prenotata;}
        }}

    public Visita(String name, boolean servizioTrasporto){this.name=name;this.trasporto=servizioTrasporto;}
    public Visita(String name, LocalDate dataVisita ){
        this.name=name;
        this.dataVisita=dataVisita;
        this.ora=LocalTime.now();
        if(dataVisita.isBefore(LocalDate.now())){this.statoVisita=StatoVisita.svolta;}
        else{if(Math.random()>0.5)this.statoVisita=StatoVisita.schedulata;}
    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public StatoVisita getStatoVisita(){return statoVisita;}
    public void setStatoVisita(StatoVisita s){this.statoVisita=s;}

    public Boolean getTrasporto(){return trasporto;}
    public void setTrasporto(Boolean b){this.trasporto=b;}

    public LocalDate getDataVisita(){return dataVisita;}
    public void setName(LocalDate dataVisita){this.dataVisita=dataVisita;}

    public String getLuogo(){return indirizzo;}
    public void setLuogo(String luogo){this.indirizzo=luogo;}

    
    public LocalDate getDataPrenotazione(){return dataPrenotazione;}
    public void setDataPrenotazione(LocalDate dataPrenotazione){this.dataPrenotazione=dataPrenotazione;}

    public LocalTime getOrarioPrenotazione(){return ora;}
    public void setOrarioPrenotazione(LocalTime oraPrenotazione){this.ora=oraPrenotazione;}

    @Override
    public String toString(){
        return name;
    }
    
}
