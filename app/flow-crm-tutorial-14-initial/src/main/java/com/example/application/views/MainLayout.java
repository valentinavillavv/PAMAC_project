package com.example.application.views;

import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout { 

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("PAMAC");
        logo.addClassNames("text-l", "m-m");
        Image img=new Image("images/logo.png", "logo");
        img.setMaxHeight("40px");

        HorizontalLayout header = new HorizontalLayout(
          new DrawerToggle(), 
          img,
          logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); 
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header); 

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Pazienti", ListView.class); 


        addToDrawer(new VerticalLayout( 
            listLink,
            new RouterLink("Statistiche", DashboardView.class),
            new RouterLink("Il tuo profilo", ProfiloCDSView.class)
        ));
    }
}