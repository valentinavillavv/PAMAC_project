package com.example.application.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Contact extends AbstractEntity {

    private Integer IDPamac;
    private String indirizzo;

    @NotNull
    @ManyToMany  
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Richiesta> richieste;

    @NotNull
    @ManyToMany 
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Visita> visite;

    @Transient
    @ManyToOne
    private Questionario questionario;

    private Integer indiceFragilitàFisica=0;
    private Integer indiceFragilitàPsicologica=0;
    private Integer indiceFragilitàSociale=0;

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @NotNull
    @ManyToOne
    private Status status;

    @Email
    @NotEmpty
    private String email = "";

    private Genere sesso=Genere.M;

    public void setGenere(Genere s){
        this.sesso=s;
    }
    
    public Genere getGenere(){
        return sesso;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public Integer getIDPamac(){return IDPamac;}
    public void setIDPamac(Integer newID){this.IDPamac=newID;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        if(sesso==Genere.M){return "images/tipo.jpg";}
        if (sesso==Genere.F) {return "images/tipa.jpg";}
        return "images/pic.jpg";
    }

    public Questionario getQuestionario() {
        if(questionario==null){questionario=new Questionario();}
        return questionario;
    }
    public void setQuestionario(Questionario q){this.questionario=q;}

    
    public void setRichiesta(Richiesta richiesta, int posizione){
        if(this.richieste==null){this.richieste=new ArrayList<>();}
        richieste.add(posizione,richiesta);
    }

    public List<Richiesta> getRichieste(){
        return richieste;
    }

    public void setVisite(Visita visita, int posizione){
        if(this.visite==null){this.visite=new ArrayList<>();}
        visite.add(posizione,visita);
    }

    public List<Visita> getVisite(){
        return visite;
    }

    public void setIndiceFragilitàFisica(int i){this.indiceFragilitàFisica=i;}
    public void setIndiceFragilitàPsicologica(int i){this.indiceFragilitàPsicologica=i;}
    public void setIndiceFragilitàSociale(int i){this.indiceFragilitàSociale=i;}
    public int getIndiceFragilitaFisica(){return indiceFragilitàFisica;}
    public int getIndiceFragilitaPsico(){return indiceFragilitàPsicologica;}
    public int getIndiceFragilitaSociale(){return indiceFragilitàSociale;}

    public void setAddress(String address){this.indirizzo=address;}
    public String getAddress(){return indirizzo;}

 }

