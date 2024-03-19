package com.example.application.views.list;


import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.views.InfoUtente;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("pazienti | PAMAC")
@Route(value = "", layout =  MainLayout.class)
public class ListView extends VerticalLayout {
    ListBox<Contact> show=new ListBox<>();
    TextField filterText = new TextField();
    ContactForm form;
    CrmService service;

    public ListView(CrmService service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        configureListBox();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(show, form);
        content.setFlexGrow(2, show); 
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ContactForm( service.findAllStatuses(),service); 
        form.setWidth("25em");

        form.addListener(ContactForm.SaveEvent.class, this::saveContact); 
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact); 
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
    }


    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("cerca");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); 
        filterText.addValueChangeListener(e-> updateList());

        Button addContactButton = new Button("aggiungi contatto");
        addContactButton.addClickListener(click-> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton); 
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureListBox(){
        show.setItems(service.findAllContacts(""));
        //show.addClassName("contact-listBox");
        show.setSizeFull();
        show.setRenderer(new ComponentRenderer<>(contact -> {
            HorizontalLayout row = new HorizontalLayout();
            row.setAlignItems(Alignment.CENTER);
        
            Avatar avatar = new Avatar();
            avatar.setName(contact.getFirstName()+" "+contact.getLastName());
            avatar.setImage(contact.getImageUrl());
        
            Span name = new Span(contact.getFirstName()+" "+contact.getLastName());
            Span stato = new Span(contact.getStatus().toString());
            stato.getStyle().set("color", "var(--lumo-secondary-text-color)").set("font-size", "var(--lumo-font-size-s)");
        
            VerticalLayout column = new VerticalLayout(name, stato);
            column.setPadding(true);
            column.setSpacing(false);
        
            row.add(avatar, column);
            row.getStyle().set("line-height", "var(--lumo-line-height-m)");
            return row;
        }));

        //show.addValueChangeListener(event->editContact(event.getValue()));

        //come vado alla view con le info sull'utente?
        show.addValueChangeListener(e->viewDetails(e.getValue()));

    }

    private void viewDetails(Contact contatto){
        UI.getCurrent().navigate(InfoUtente.class, contatto.getIDPamac());
        System.out.println(contatto.getIDPamac());
    }

    public void editContact(Contact contact) { 
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addContact() { 
        show.clear();
        editContact(new Contact());
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }
    
    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void updateList() { 
       show.setItems(service.findAllContacts(filterText.getValue()));
    }
}
