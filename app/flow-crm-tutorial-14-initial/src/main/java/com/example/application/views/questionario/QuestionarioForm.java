package com.example.application.views.questionario;

import java.util.Date;

import com.example.application.data.entity.Genere;
import com.example.application.data.entity.Questionario;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class QuestionarioForm extends FormLayout {
    Date dataCompilazione;
    TextField firstName = new TextField("nome"); 
    TextField lastName = new TextField("cognome");

    RadioButtonGroup<Genere> genere=new RadioButtonGroup<>();
    RadioButtonGroup<String> luogoNascita=new RadioButtonGroup<>();
    TextField luogo=new TextField("provincia luogo di nascita (o stato se nato all'estero):");
   
    DatePicker dataNascita=new DatePicker("data di nascita:");

    RadioButtonGroup<String> titoloStudio=new RadioButtonGroup<>();

    TextField lavoro=new TextField("attività lavorativa prima del pensionamento: ");
 
    RadioButtonGroup<String> stileVita=new RadioButtonGroup<>();
    RadioButtonGroup<String> BoolMalattie=new RadioButtonGroup<>();
    TextField malattie=new TextField("Se ha malattie croniche indichi quali (es. diabete,ipertensione,artrite,osteoporosi,..)");
   
    
    RadioButtonGroup<String> morte=new RadioButtonGroup<>();
    RadioButtonGroup<String> malattiagrave=new RadioButtonGroup<>();
    RadioButtonGroup<String>  malattiafam=new RadioButtonGroup<>();
    RadioButtonGroup<String> divorzio=new RadioButtonGroup<>();
    RadioButtonGroup<String> incidente=new RadioButtonGroup<>();
    RadioButtonGroup<String> reato=new RadioButtonGroup<>();
    RadioButtonGroup<String> soddisfazione=new RadioButtonGroup<>();

    RadioButtonGroup<String> attivitaFisica=new RadioButtonGroup<>();
    RadioButtonGroup<String> peso=new RadioButtonGroup<>();

    RadioButtonGroup<String> deambu;
    RadioButtonGroup<String> equi;
    RadioButtonGroup<String> udito;
    RadioButtonGroup<String> vista;
    RadioButtonGroup<String> mani;
    RadioButtonGroup<String> stanco;

    RadioButtonGroup<String> memo;
    RadioButtonGroup<String> morale;
    RadioButtonGroup<String> ansia;
    RadioButtonGroup<String> problem;

    RadioButtonGroup<String> vive;
    RadioButtonGroup<String> mancanza;
    RadioButtonGroup<String> sostegno;


    Binder<Questionario> binder= new BeanValidationBinder<>(Questionario.class);
    private Questionario questionario;
    Button save = new Button("Save");

    public QuestionarioForm() {
        dataCompilazione=new Date();
        addClassName("questionario-form"); 

        binder.bindInstanceFields(this);

        H1 a=new H1("PARTE A, determinanti della fragilità");
        H1 b=new H1("PARTE B, componenti della fragilità");
        H1 b1=new H1("componenti fisiche");
        H1 b2=new H1("componenti psicologiche");
        H1 b3=new H1("componenti sociali");


        add(a,
            firstName, 
            lastName,
            getGenere(),
            getLuogoNascita(),
            luogo,
            getTitoloStudio(),
            lavoro,
            getStileVita(),
            getBoolMalattie(),
            malattie,
            new H2("ha avuto esperienza di uno o più dei seguenti eventi nel corso dell'ultimo anno?"),
            getInfoUltimoAnno(),
            b,
            b1,
            getBoolAttività(),
            getBoolPeso(),
            new H2("nella sua via quotidiana, riscontra problemi dovuti a :"),
            getInfoVitaQuotidiana(),
            b2,
            getInfoPsicologiche(),
            b3,
            getInfoSociali(),
            createButtonsLayout()
            );
      }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave()); 
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(questionario); 
            fireEvent(new SaveEvent(this, questionario)); 
          } catch (ValidationException e) {
            e.printStackTrace();
          }
    }

    public Date getDataCompilazione(){return dataCompilazione;}

    private Component getInfoSociali() {
        vive=new RadioButtonGroup<>();
        vive.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        vive.setLabel("vive da solo?");
        vive.setItems("si", "no");
        //vive.setSizeFull();

        mancanza=new RadioButtonGroup<>();
        mancanza.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        mancanza.setLabel("A volte sente la mancanza di persone intorno a lei?");
        mancanza.setItems("si","qualche volta", "no");
        //mancanza.setSizeFull();

        sostegno=new RadioButtonGroup<>();
        sostegno.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        sostegno.setLabel("Riceve sufficiente sostegno dalle altre persone?");
        sostegno.setItems("si", "no");
        //sostegno.setSizeFull();

        return new VerticalLayout(vive,mancanza,sostegno);
    }

    private Component getInfoPsicologiche() {
        memo=new RadioButtonGroup<>();
        memo.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        memo.setLabel("Ha problemi di memoria?");
        memo.setItems("si", "qualche volta","no");
       // memo.setSizeFull();

        morale=new RadioButtonGroup<>();
        morale.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        morale.setLabel("Si è sentito giù di morale durante l'ultimo mese?");
        morale.setItems("si","qualche volta", "no");
        //morale.setSizeFull();

        ansia=new RadioButtonGroup<>();
        ansia.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        ansia.setLabel("Si è sentito nervoso o ansioso durante l'ultimo mese?");
        ansia.setItems("si","qualche volta" ,"no");
        //ansia.setSizeFull();

        problem=new RadioButtonGroup<>();
        problem.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        problem.setLabel("Si è sentito giù di morale durante l'ultimo mese?");
        problem.setItems("si", "no");
        //problem.setSizeFull();

        return new VerticalLayout(memo,morale,ansia,problem);
    }

    private Component getInfoVitaQuotidiana() {
        deambu=new RadioButtonGroup<>();
        deambu.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        deambu.setLabel("Difficoltà nella deambulazione?");
        deambu.setItems("si", "no");

        equi=new RadioButtonGroup<>();
        equi.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        equi.setLabel("Difficoltà nel mantenere l'equilibrio?");
        equi.setItems("si", "no");

        udito=new RadioButtonGroup<>();
        udito.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        udito.setLabel("scarso udito?");
        udito.setItems("si", "no");

        vista=new RadioButtonGroup<>();
        vista.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        vista.setLabel("scarsa vista?");
        vista.setItems("si", "no");

        mani=new RadioButtonGroup<>();
        mani.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        mani.setLabel("mancanza di forza nelle mani?");
        mani.setItems("si", "no");

        stanco=new RadioButtonGroup<>();
        stanco.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        stanco.setLabel("Stanchezza fisica?");
        stanco.setItems("si", "no");
        return new VerticalLayout(deambu,equi,udito,vista,mani,stanco);
    }

    private Component getBoolPeso() {
        peso.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        peso.setLabel(" Recentemente ha perso molto peso non intenzionalmete (per molto si intende 6kg o più negli ultimi 6 mesi, o 3kg o più negli ultimi 3 mesi)");
        peso.setItems("si", "no");
        return peso;
    }

    private Component getBoolAttività() {
        attivitaFisica.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        attivitaFisica.setLabel("Pensa di essere sufficientemente attivo dal punto di vista fisico?");
        attivitaFisica.setItems("si", "no");
        return attivitaFisica;
    }

    private Component getInfoUltimoAnno(){
        morte.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        morte.setLabel("La morte di una persona cara");
        morte.setItems("si", "no");
        malattiagrave.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        malattiagrave.setLabel("Una grave malattia di cui ha sofferto");
        malattiagrave.setItems("si", "no");
        malattiafam.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        malattiafam.setLabel("Una grave malattia di cui ha sofferto una persona a lei cara");
        malattiafam.setItems("si", "no");
        divorzio.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        divorzio.setLabel("Un divorzio o la fine di una relazione sentimentale importante");
        divorzio.setItems("si", "no");
        incidente.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        incidente.setLabel("Un incidente stradale");
        incidente.setItems("si", "no");
        reato.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        reato.setLabel("Un reato (es. truffa, furto,..)");
        reato.setItems("si", "no");
        
        VerticalLayout v=new VerticalLayout(
            morte,malattiagrave,malattiafam,divorzio,incidente,reato
        );
        return v;
    }

    private Component getBoolMalattie() {
        BoolMalattie.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        BoolMalattie.setLabel("Ha una o più malattie croniche?");
        BoolMalattie.setItems("si", "no");
    
        return BoolMalattie;
    }

    private Component getStileVita() {
        stileVita.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        stileVita.setLabel("Quanto pensa il suo stile di vita sia salutare?");
        stileVita.setItems("salutare", "più o meno salutare","non salutare");
    
        return stileVita;
    }

    private Component getTitoloStudio() {
        titoloStudio.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        titoloStudio.setLabel("Titolo di studio più alto conseguito:");
        titoloStudio.setItems("nessun titolo di studio", "licenza elementare","licenza media inferiore/avviamento professionale","diploma scuola superiore", "laurea o diploma universitario");
    
        return titoloStudio;
    }

    private Component getLuogoNascita(){
        luogoNascita.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        luogoNascita.setLabel("Luogo di nascita:");
        luogoNascita.setItems("Italia", "estero");
    
        return luogoNascita;
    }

    private Component getGenere() {
        
        genere.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        genere.setLabel("Sesso:");
        genere.setItems(Genere.M, Genere.F);
    
        return genere;
    }

//Events
public static abstract class QuestionarioFormEvent extends ComponentEvent<QuestionarioForm> {
    private Questionario questionario;
    protected QuestionarioFormEvent(QuestionarioForm source, Questionario questionario) { 
        super(source, false);
        this.questionario = questionario;
      }
      public Questionario getQuestionario() {
        return questionario;
      }
}
public static class SaveEvent extends QuestionarioFormEvent {
    SaveEvent(QuestionarioForm source, Questionario q) {
      super(source, q);
    }
  }

public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
  ComponentEventListener<T> listener) { 
  return getEventBus().addListener(eventType, listener);
}

}