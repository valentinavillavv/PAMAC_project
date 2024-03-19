package com.example.application.views;

import java.time.LocalDate;

import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Richiesta;
import com.example.application.data.entity.Visita;
import com.example.application.data.entity.Visita.StatoVisita;
import com.example.application.data.service.CrmService;
import com.example.application.views.form.ModifyForm;
import com.example.application.views.questionario.QuestionarioForm;
import com.example.application.views.utili.BorderRadius;
import com.example.application.views.utili.Bottom;
import com.example.application.views.utili.Horizontal;
import com.example.application.views.utili.ListItem;
import com.example.application.views.utili.MyFlexLayout;
import com.example.application.views.utili.Top;
import com.example.application.views.utili.Vertical;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.TickmarkPlacement;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.ContentAlignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Transactional
@Route(value = "InfoUtente",layout=MainLayout.class) 
@PageTitle("InfoUtente")
public class InfoUtente extends VerticalLayout implements HasUrlParameter<Integer> ,BeforeEnterObserver {
    private Contact contatto;
    private Integer idContatto;
    private CrmService service;
    private Tab paziente;
	private Tab questionario;
	private Tab altro;
	private VerticalLayout content=new VerticalLayout();
    private ModifyForm form;
    public int VISIBLE_RECENT_TRANSACTIONS = 4;

    ListItem nome;
    ListItem stat;
    ListItem updated;
    ListItem indirizzo;
    ListItem email;
    String ora;


    public InfoUtente(CrmService s){
        
        this.service=s;
        
    }

    //form per la modifica dei dati del contatto
    private ModifyForm configureForm() {
        form = new ModifyForm( service.findAllStatuses(), service); 
        form.setContact(contatto);
        form.setWidth("25em");
        return form;
        
    }

    //sezione info contatto
    private Component configureInfo(Contact c) {
        MyFlexLayout Vl=new MyFlexLayout(
            createImageSection(),
            createRecentRequestsHeader(),
            createRecentRequestsList(),
            createDiarioHeader(),
            createDiarioSection(),
            createIndiceHeader(),
            createIndiciSection()
            );
        Vl.setFlexDirection(FlexDirection.COLUMN);
        Vl.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        Vl.setMaxWidth("840px");
        return Vl;
    }


   private Component createRecentRequestsList() {
        Div items = new Div();
		items.addClassNames("bsb-b", "padding-b-l");

		for (int i = 0; i < VISIBLE_RECENT_TRANSACTIONS; i++) {
			Richiesta request = contatto.getRichieste().get(i);
			Label label = new Label(request.getStatoRichiesta());
            label.addClassName("h5");

			if (request.getStatoRichiesta()=="Conclusa") {
                label.getElement().getStyle().set("color","green");
			} if(request.getStatoRichiesta()=="Esaminata") {
                label.getElement().getStyle().set("color","orange");}
            else {
				label.getElement().getStyle().set("color", "red");
			}
			ListItem item = new ListItem(
					request.getName(),
					formatDate(LocalDate.now().minusDays(i)),
					label
			);
			// Dividers for all but the last item
			item.setDividerVisible(i < VISIBLE_RECENT_TRANSACTIONS - 1);
            item.getContent().setAlignItems(Alignment.CENTER);
            item.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
            item.expand(item.getComponentAt(0));
			items.add(item);
        }
       
        return items;
    }

    private Component createIndiciSection() {
        ListItem indiceF=new ListItem(" "+contatto.getIndiceFragilitaFisica(),"fragilità fisica:");
        indiceF.addClassName("h2");
        indiceF.getElement().setAttribute("with-divider", true);
        indiceF.setId("fisica");
        indiceF.setFlexDirection(FlexDirection.ROW);

        ListItem indiceP=new ListItem(" "+contatto.getIndiceFragilitaPsico(),"fragilità psicologica:");
        indiceP.addClassName("h2");
        indiceP.getElement().setAttribute("with-divider", true);
        indiceP.setId("psicologica");
        indiceP.setFlexDirection(FlexDirection.ROW);

        ListItem indiceS=new ListItem(" "+contatto.getIndiceFragilitaSociale(),"fragilità sociale:");
        indiceS.addClassName("h2");
        indiceF.getElement().setAttribute("with-divider", true);
        indiceS.setId("sociale");
        indiceS.setFlexDirection(FlexDirection.ROW);

        MyFlexLayout listaIndici = new MyFlexLayout(indiceF, indiceP, indiceS);
		listaIndici.setFlexDirection(FlexDirection.COLUMN);
        listaIndici.getElement().setAttribute("with-divider", true);

        Chart chart = new Chart(ChartType.LINE);

        com.vaadin.flow.component.charts.model.Configuration conf = chart.getConfiguration();
        conf.getChart().setPolar(true);
        // Create the range series
        ListSeries series = new ListSeries("indici di fragilità:",
        contatto.getIndiceFragilitaFisica(),contatto.getIndiceFragilitaPsico(),contatto.getIndiceFragilitaSociale());
        conf.addSeries(series);

        // Set the category labels on the X axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("fragilità fisica","fragilità psicologica","fragilità sociale");
        xaxis.setTickmarkPlacement(TickmarkPlacement.ON);
        //xaxis.setLineWidth(0);
        conf.addxAxis(xaxis);

        // Configure the Y axis
        YAxis yaxis = new YAxis();
        yaxis.setGridLineInterpolation("polygon"); // Webby look
        yaxis.setMin(0);
        yaxis.setTickInterval(10);
        yaxis.getLabels().setStep(3);
        conf.addyAxis(yaxis);

        HorizontalLayout h=new HorizontalLayout(listaIndici, chart);
        h.setAlignItems(Alignment.CENTER);
        h.setJustifyContentMode(JustifyContentMode.CENTER);
        h.getElement().setAttribute("with-divider", true);
        return h;
    }

    private Component createIndiceHeader() {
        Label header = new Label("Indici di fragilità:");
		header.addClassNames("margin-r-v-l", "margin-r-h-l");
		return header;
    }

    public static String formatDate(LocalDate date) {
        return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear();
	}

    private Component createImageSection() {
        Image image = new Image(contatto.getImageUrl(),"Immagine profilo");
        image.addClassName("margin-h-l");
        setBorderRadius(BorderRadius._50, image);
		image.setHeight("250px");
		image.setWidth("250px");

        nome=new ListItem(createTertiaryIcon(VaadinIcon.MALE)," "+contatto.getFirstName()+" "+contatto.getLastName()," nome");
        nome.addClassName("h2");
        nome.getElement().setAttribute("with-divider", true);
        nome.setId("nome");
        nome.setFlexDirection(FlexDirection.ROW);

        stat=new ListItem(createTertiaryIcon(VaadinIcon.INFO)," "+contatto.getStatus().toString()," stato");
        stat.getElement().setAttribute("with-divider", true);
        stat.setId("status");
        stat.getElement().setProperty("white.space", "pre-line");

		updated = new ListItem(createTertiaryIcon(VaadinIcon.CALENDAR),contatto.getQuestionario().getDataCompilazione()+"","  data ultimo questionario");
		updated.setFlexDirection(FlexDirection.ROW);

        indirizzo=new ListItem(createTertiaryIcon(VaadinIcon.LOCATION_ARROW)," "+contatto.getAddress(),"  indirizzo:");
        indirizzo.addClassName("h2");
        indirizzo.getElement().setAttribute("with-divider", true);
        indirizzo.setId("nome");
        indirizzo.setFlexDirection(FlexDirection.ROW);

        email=new ListItem(createTertiaryIcon(VaadinIcon.CHAT)," "+contatto.getEmail()," email:");
        email.addClassName("h2");
        email.getElement().setAttribute("with-divider", true);
        email.setId("nome");
        email.setFlexDirection(FlexDirection.ROW);



        MyFlexLayout listItems = new MyFlexLayout(nome, stat, updated, indirizzo,email);
		listItems.setFlexDirection(FlexDirection.COLUMN);
        listItems.getElement().setAttribute("with-divider", true);

		MyFlexLayout section = new MyFlexLayout(image, listItems);
		section.addClassName("bsb-b");
        section.setAlignItems(Alignment.CENTER);
        section.setFlexWrap(FlexWrap.WRAP);
        section.setJustifyContentMode(JustifyContentMode.CENTER);
        section.getElement().setAttribute("with-divider", true);

		return section;
    }

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

    private Component createRecentRequestsHeader() {
		Label titolo =new Label("Richieste più recenti:");
        titolo.addClassName("h2");

		Button viewAll = new Button("Vedi tutte");
        viewAll.addThemeVariants(ButtonVariant.LUMO_SMALL);
        Button add= new Button("Nuova Rirchiesta");
        add.addThemeVariants(ButtonVariant.LUMO_SMALL);
    
        viewAll.getElement().setAttribute("aria-label", "Vedi tutte");
		viewAll.addClickListener(
				e -> Notification.show("da implementare", 2000,Notification.Position.BOTTOM_CENTER));
		viewAll.addClassName("margin-l-a");

		MyFlexLayout header = new MyFlexLayout(titolo, viewAll,add);
		header.setAlignItems(Alignment.CENTER);
        header.setAlignContent(ContentAlignment.SPACE_BETWEEN);
        header.expand(titolo);
		header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
		return header;
	}

    private Component createDiarioHeader() {
		Label titolo =new Label("Diario:");
        titolo.addClassName("h2");

		Button viewAll = new Button("Vedi tutto");
        viewAll.addThemeVariants(ButtonVariant.LUMO_SMALL);
    
        viewAll.getElement().setAttribute("aria-label", "Vedi tutte");
		viewAll.addClickListener(
				e -> Notification.show("da implementare", 2000,Notification.Position.BOTTOM_CENTER));
		viewAll.addClassName("margin-l-a");

		MyFlexLayout header = new MyFlexLayout(titolo, viewAll);
		header.setAlignItems(Alignment.CENTER);
        header.setAlignContent(ContentAlignment.SPACE_BETWEEN);
        header.expand(titolo);
		header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
		return header;
	}

    
   private Component createDiarioSection() {
    Div items = new Div();
    items.addClassNames("bsb-b", "padding-b-l");

    for (int i = 0; i < VISIBLE_RECENT_TRANSACTIONS; i++) {
        Visita visit = contatto.getVisite().get(i);
        Label label = new Label(visit.getStatoVisita().toString());
        label.addClassName("h5");
        double q=Math.random();
        ora="14:30";
        if(q<0.2){ora="08:30";}if(q>=0.2 && q<0.4){ora="10:00";}if(q>=0.4 && q<0.5){ora="11:30";}
        if(q>=0.5 && q<0.7){ora="12:45";}if(q>=0.7 && q<0.9){ora="13:00";}

        if (visit.getStatoVisita()==StatoVisita.svolta) {
            label.getElement().getStyle().set("color","green");}
        if (visit.getStatoVisita()==StatoVisita.prenotata) {
            label.getElement().getStyle().set("color","orange");}
        else {
            label.getElement().getStyle().set("color", "red");
        }
        ListItem item = new ListItem(
                visit.getName(),
                formatDate(LocalDate.now().minusDays(i+2))+" orario: "+ora,
                label
        );
        // Dividers for all but the last item
        item.setDividerVisible(i < VISIBLE_RECENT_TRANSACTIONS - 1);;
        item.getContent().setAlignItems(Alignment.CENTER);
        item.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
        item.expand(item.getComponentAt(0));
        items.add(item);
        }
        return items;
    }

    private Tabs getTabs() {
        paziente = new Tab("Paziente");
        questionario= new Tab("Questionario");
        altro= new Tab("Modifica");

        Tabs tabs = new Tabs(paziente, questionario, altro);
        tabs.setWidthFull();
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabs.addSelectedChangeListener(event ->
	        setContent(event.getSelectedTab())
        );

        
		content = new VerticalLayout();
		content.setSpacing(false);
		setContent(tabs.getSelectedTab());

		return tabs;
	}


     private void setContent(Tab tab) {
		content.removeAll();
       

		if (tab.equals(paziente)) {
            content.add(configureInfo(contatto));
		} else if (tab.equals(questionario)) {
            QuestionarioForm q=new QuestionarioForm();
            q.setWidth("25em");
            //q.addListener(QuestionarioForm.SaveEvent.class, this::saveContact);
			content.add(q);
            content.setAlignItems(Alignment.CENTER);
		} else {
		    content.add(configureForm());
		}

	}

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        this.idContatto=parameter;
        contatto=service.getContact(parameter);
        if (contatto==null){
            VerticalLayout v=new VerticalLayout();
            v.add("UTENTE NON TROVATO");
            add(v);}
        else{
            add(getTabs(),content);}
    }

    
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }
    

}

