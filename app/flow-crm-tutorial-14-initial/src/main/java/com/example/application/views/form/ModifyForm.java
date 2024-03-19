package com.example.application.views.form;

import java.util.List;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Genere;
import com.example.application.data.entity.Status;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ModifyForm extends FormLayout{
    TextField firstName = new TextField("Nome"); 
    TextField lastName = new TextField("Cognome");
    EmailField email = new EmailField("Email");
    ComboBox<Status> status = new ComboBox<>("Stato");
    TextField address= new TextField("Indirizzo"); 
    //ComboBox<Genere> sesso=new ComboBox<>("sesso");
    RadioButtonGroup<Genere> sesso=new RadioButtonGroup<>();
    
  
    Binder<Contact> binder= new BeanValidationBinder<>(Contact.class);
    private Contact contact;
    CrmService service;
  
    Button save = new Button("Save");
    Button delete = new Button("Delete");
      
  
    public ModifyForm( List<Status> list2,CrmService s) {
      addClassName("contact-form"); 
      this.service=s;
  
      binder.bindInstanceFields(this);
  
      status.setItems(list2);
      status.setItemLabelGenerator(Status::getName);
  
      sesso.setLabel("sesso");
      sesso.setItems(service.findAllGender());
      //sesso.setItemLabelGenerator(Genere::getName);
  
      add(firstName, 
          lastName,
          email,
          status,
          address,
          sesso,
          createButtonsLayout());
    }
  
      private HorizontalLayout createButtonsLayout() {
          save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
          delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
  
          save.addClickListener(event -> validateAndSave()); 
          delete.addClickListener(event -> fireEvent(new DeleteEvent(this, contact))); 
    
          binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
  
          return new HorizontalLayout(save, delete);
      }
  
      private void validateAndSave() {
          try {
            binder.writeBean(contact); 
            fireEvent(new SaveEvent(this, contact)); 
          } catch (ValidationException e) {
            e.printStackTrace();
          }
      }
  
      public void setContact(Contact contact) {
       this.contact = contact; 
       binder.readBean(contact); 
      }
  
  
  // Events
  public static abstract class ContactFormEvent extends ComponentEvent<ModifyForm> {
      private Contact contact;
    
      protected ContactFormEvent(ModifyForm source, Contact contact) { 
        super(source, false);
        this.contact = contact;
      }
    
      public Contact getContact() {
        return contact;
      }
    }
    
    public static class SaveEvent extends ContactFormEvent {
      SaveEvent(ModifyForm source, Contact contact) {
        super(source, contact);
      }
    }
    
    public static class DeleteEvent extends ContactFormEvent {
      DeleteEvent(ModifyForm source, Contact contact) {
        super(source, contact);
      }
    
    }
    
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
        ComponentEventListener<T> listener) { 
      return getEventBus().addListener(eventType, listener);
    }
  } 
    
