package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class Richiesta extends AbstractEntity{
    private String name;

    public enum StatoRichiesta{
        DaEsaminare,
        Annullata,
        Conclusa,
        Esaminata
    }

    private StatoRichiesta statoRichiesta;

    public Richiesta(){}
    public Richiesta(String name){
        this.name=name; 
        Double i=Math.random();
        if(i<0.2){this.statoRichiesta=StatoRichiesta.Annullata;}
        if(i<0.4 && i>0.2){this.statoRichiesta=StatoRichiesta.Conclusa;}
        if(i<0.8 && i>0.6){this.statoRichiesta=StatoRichiesta.DaEsaminare;}
        else{this.statoRichiesta=StatoRichiesta.Esaminata;}
    }

    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getStatoRichiesta(){return statoRichiesta.toString();}
    public void setStatoRichiesta(StatoRichiesta s){this.statoRichiesta=s;}

   // @Formula("(select count(c.id) from Contact c where c.richiesta_id = id)") 
    //private int requestsCount;

   // public int getRequestCount(){
    //    return requestsCount;
    //}

    @Override
    public String toString(){
        return name;
    }
}
