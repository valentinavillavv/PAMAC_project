package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.example.application.views.utili.BorderRadius;
import com.example.application.views.utili.ListItem;
import com.example.application.views.utili.MyFlexLayout;
import com.vaadin.flow.component.Component;

import com.vaadin.flow.component.html.Image;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "profilo", layout = MainLayout.class) 
@PageTitle("Il tuo profilo")
public class ProfiloCDSView extends VerticalLayout {
    CrmService service;


    public ProfiloCDSView(CrmService service) { 
        this.service = service;
        addClassName("profilo-view");
        setSpacing(false);


        Image image = new Image("images/pic.jpg","Immagine profilo");
        image.addClassName("margin-h-l");
        setBorderRadius(BorderRadius._50, image);
		image.setHeight("300px");
		image.setWidth("300px");

        ListItem nome=new ListItem(createTertiaryIcon(VaadinIcon.MALE), "Valentina Villa"," nome");
        nome.addClassName("h2");
        nome.getElement().setAttribute("with-divider", true);
        nome.setId("nome");
        nome.setFlexDirection(FlexDirection.ROW);

        ListItem email=new ListItem(createTertiaryIcon(VaadinIcon.CHAT),"v.villa14@studenti.unibg.it"," email:");
        email.addClassName("h2");
        email.getElement().setAttribute("with-divider", true);
        email.setId("mail");
        email.setFlexDirection(FlexDirection.ROW);

        ListItem ruolo=new ListItem(createTertiaryIcon(VaadinIcon.DIPLOMA),"Consulente Della Salute","Ruolo:");
        ruolo.getElement().setAttribute("with-divider", true);
        ruolo.setId("CDS");
        ruolo.setFlexDirection(FlexDirection.ROW);

        ListItem ID=new ListItem(createTertiaryIcon(VaadinIcon.INFO),"001"," ID:");
        ID.getElement().setAttribute("with-divider", true);
        ID.setId("id");
        ID.setFlexDirection(FlexDirection.ROW);

        MyFlexLayout listItems = new MyFlexLayout(nome,email, ruolo,ID);
		listItems.setFlexDirection(FlexDirection.COLUMN);
        listItems.getElement().setAttribute("with-divider", true);

		MyFlexLayout section = new MyFlexLayout(image, listItems);
		section.addClassName("bsb-b");
        section.setAlignItems(Alignment.CENTER);
        section.setFlexWrap(FlexWrap.WRAP);
        section.setJustifyContentMode(JustifyContentMode.CENTER);
        section.getElement().setAttribute("with-divider", true);

        add(section);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    //metodo per avere icone 
    public static Icon createTertiaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
        i.setColor("var(--lumo-tertiary-text-color)");
		return i;
	}
    
    public static void setBorderRadius(BorderRadius borderRadius,
    Component... components) {
    for (Component component : components) {
        component.getElement().getStyle().set("border-radius",
        borderRadius.getValue());
        }
    }

    //si potra aggiungere il calendario personale del consulente della salute sotto i dati
    //private Component getCalendar(){ }
    }   
    
