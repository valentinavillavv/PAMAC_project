package com.example.application.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class Questionario extends AbstractEntity {
    LocalDate dataCompilazione;

    private String firstName ;
    private String lastName ;
    private Genere genere;
    private String luogoNascita;
    private String luogo;
    private LocalDate dataNascita;
    private String titoloStudio;
    private String lavoro;
    private String stileVita;
    private String BoolMalattie; //o boolean
    private String malattie;

    //bool?
    private String morte;
    private String malattiagrave;
    private String malattiafam;
    private String divorzio;
    private String incidente;
    private String reato;
    private String soddisfazione;
    private String attivitaFisica;
    private String peso;
    private String deambu;
    private String equi;
    private String udito;
    private String vista;
    private String mani;
    private String stanco;

    private String memo;
    private String morale;
    private String ansia;
    private String problem;

    private String vive;
    private String mancanza;
    private String sostegno;

    public Questionario(){this.dataCompilazione=LocalDate.now();}

    public String getFirstName(){return firstName;}
    public void setFirstName(String s){this.firstName=s;}

    public String getLastName(){return lastName;}
    public void setLastName(String s){this.lastName=s;}

    public Genere getGenere(){return genere; }
    public void setGenere(Genere g){this.genere=g;}

    public String getLuogoNascita(){return luogoNascita;}
    public void setLuogoNascita(String s){this.luogoNascita=s;}

    public String getLuogo(){return luogo; }
    public void setLuogo(String s){this.luogo=s;}

    public String getLavoro(){return lavoro; }
    public void setLavoro(String s){this.lavoro=s;}

    public LocalDate getDataCompilazione(){return dataCompilazione; }
    public void setDataCompilazione(LocalDate d){this.dataCompilazione=d;}

    public LocalDate getDataNascita(){return dataNascita; }
    public void setDataNascita(LocalDate d){this.dataNascita=d;}

    public String getTitoloStudio(){return titoloStudio;}
    public void setTitoloStudio(String s){this.titoloStudio=s;}

    public String getStileVita(){return stileVita;}
    public void setStileVita(String s){this.stileVita=s;}

    public String getBoolMalattie(){return BoolMalattie;}
    public void setBoolMalattie(String s){this.BoolMalattie=s;}

    public String getMalattie(){return malattie; }
    public void setMalattie(String s){this.malattie=s;}

    public String getMorte(){return morte; }
    public void setMorte(String s){this.morte=s;}

    public String getMalattiaGrave(){return malattiagrave; }
    public void setMalattiaGrave(String s){this.malattiagrave=s;}

    public String getMalattiaFamiliare(){return malattiafam; }
    public void setMalattiaFamiliare(String s){this.malattiafam=s;}

    public String getDivorzio(){return divorzio; }
    public void setDivorzio(String s){this.divorzio=s;}

    public String getIncidente(){return incidente; }
    public void setIncidente(String s){this.incidente=s;}

    public String getReato(){return reato; }
    public void setReato(String s){this.reato=s;}

    public String getSoddisfazione(){return soddisfazione; }
    public void setSoddisfazione(String s){this.soddisfazione=s;}

    public String getAttivitaFisica(){return attivitaFisica; }
    public void setAttivitaFisica(String s){this.attivitaFisica=s;}

    public String getPeso(){return peso; }
    public void setPeso(String s){this.peso=s;}

    public String getDeambulazione(){return deambu; }
    public void setDeambulazione(String s){this.deambu=s;}

    public String getEquilibrio(){return equi; }
    public void setEquilibrio(String s){this.equi=s;}

    public String getUdito(){return udito; }
    public void setUdito(String s){this.udito=s;}

    public String getVista(){return vista; }
    public void setVista(String s){this.vista=s;}

    public String getMani(){return mani; }
    public void setMani(String s){this.mani=s;}

    public String getStanchezza(){return stanco; }
    public void setStanchezza(String s){this.stanco=s;}

    public String getMemoria(){return memo; }
    public void setMemoria(String s){this.memo=s;}

    public String getMorale(){return morale; }
    public void setMorale(String s){this.morale=s;}

    public String getAnsia(){return ansia; }
    public void setAnsia(String s){this.ansia=s;}

    public String getProblem(){return problem; }
    public void setProblem(String s){this.problem=s;}

    public String getViveSolo(){return vive; }
    public void setViveSolo(String s){this.vive=s;}

    public String getMancanza(){return mancanza; }
    public void setMancanza(String s){this.mancanza=s;}

    public String getSostegno(){return sostegno; }
    public void setSostegno(String s){this.sostegno=s;}
}