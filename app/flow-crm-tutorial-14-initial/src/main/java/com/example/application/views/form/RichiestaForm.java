package com.example.application.views.form;

import java.util.List;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Richiesta;
import com.example.application.data.entity.Richiesta.StatoRichiesta;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class RichiestaForm extends FormLayout{
    ComboBox<Richiesta> name = new ComboBox<>("Tipologia:");
    ComboBox<StatoRichiesta> statoRichiesta = new ComboBox<>("Stato:");

    
    Binder<Richiesta> binder= new BeanValidationBinder<>(Richiesta.class);
    private Contact contact;
    CrmService service;
  
    Button save = new Button("Save");
    Button close = new Button("Cancel");

    public RichiestaForm( List<Richiesta> list2,CrmService s) {
        addClassName("contact-form"); 
        this.service=s;
    
        binder.bindInstanceFields(this);
    
        name.setItems(list2);
        name.setItemLabelGenerator(Richiesta::getName);

        statoRichiesta.setItems(StatoRichiesta.Annullata,StatoRichiesta.Conclusa,StatoRichiesta.DaEsaminare,StatoRichiesta.Esaminata);
        //statoRichiesta.setItemLabelPath(Richiesta::getStatoRichiesta);
        add(name,
            createButtonsLayout());
      }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER); 
        close.addClickShortcut(Key.ESCAPE);

       // save.addClickListener(event -> validateAndSave()); 
        close.addClickListener(event -> fireEvent(new CloseEvent(this))); 
  
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    //Events
    public static abstract class RichiestaFormEvent extends ComponentEvent<RichiestaForm> {
        private Contact contact;
      
        protected RichiestaFormEvent(RichiestaForm source, Contact contact) { 
          super(source, false);
          this.contact = contact;
        }
      
        public Contact getContact() {
          return contact;
        }
      }
      
      public static class SaveEvent extends RichiestaFormEvent {
        SaveEvent(RichiestaForm source, Contact contact) {
          super(source, contact);
        }
      }
      
      public static class DeleteEvent extends RichiestaFormEvent {
        DeleteEvent(RichiestaForm source, Contact contact) {
          super(source, contact);
        }
      
      }
      
      public static class CloseEvent extends RichiestaFormEvent {
        CloseEvent(RichiestaForm source) {
          super(source, null);
        }
      }
      
      public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
          ComponentEventListener<T> listener) { 
        return getEventBus().addListener(eventType, listener);
      }
}
