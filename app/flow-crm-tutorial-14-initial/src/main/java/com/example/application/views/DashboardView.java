package com.example.application.views;

import com.example.application.data.entity.Richiesta;
import com.example.application.data.service.CrmService;
import com.example.application.views.utili.Bottom;

import com.example.application.views.utili.MyFlexLayout;
import com.example.application.views.utili.Right;
import com.example.application.views.utili.Top;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.Background;
import com.vaadin.flow.component.charts.model.BackgroundShape;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Pane;
import com.vaadin.flow.component.charts.model.PlotOptionsSolidgauge;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.ContentAlignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = MainLayout.class) 
@PageTitle("Dashboard ")
public class DashboardView extends VerticalLayout {
    private final CrmService service;

 public DashboardView(CrmService service) { 
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); 
        add(getContactStats(), getStatusChart(), getGenderChart(), createRequestsHeader(),createCharts());
    }   

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts"); 
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Chart getStatusChart() {
        Chart chart = new Chart(ChartType.PIE); 

        DataSeries dataSeries = new DataSeries();
        service.findAllStatuses().forEach(status ->
            dataSeries.add(new DataSeriesItem(status.getName(), status.getStatusCount()))); 
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Chart getGenderChart() {
        Chart chart = new Chart(ChartType.PIE); 

        DataSeries dataSeries = new DataSeries();
        service.findAllGender().forEach(genere-> dataSeries.add(new DataSeriesItem(genere.getName(), service.getGenereCount(genere))));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
    
    private Component createPaymentChart(Richiesta.StatoRichiesta status) {
		int value=0;

		switch (status) {
			case DaEsaminare:
				value = 42;
				break;

			case Annullata:
				value = 5;
				break;

			case Conclusa:
				value = 23;
				break;

			case Esaminata:
				value = 30;
				break;
		}

		MyFlexLayout textContainer = new MyFlexLayout(
				new Label(Integer.toString(value)),
				new Label( "%"));
		textContainer.setAlignItems(Alignment.BASELINE);
		textContainer.getStyle().set("position", "absolute");
		textContainer.setSpacing(Right.XS);

		Chart chart = createProgressChart(status, value);

		MyFlexLayout chartContainer = new MyFlexLayout(chart, textContainer);
		chartContainer.setAlignItems(Alignment.CENTER);
		chartContainer.setJustifyContentMode(JustifyContentMode.CENTER);
		chartContainer.getStyle().set("position", "relative");
		chartContainer.setHeight("120px");
		chartContainer.setWidth("120px");

		MyFlexLayout statusChart = new MyFlexLayout(
				new Label(status.toString()), chartContainer);
		statusChart.setAlignItems(Alignment.CENTER);
		statusChart.setFlexDirection(FlexDirection.ROW);
		statusChart.setPadding(Bottom.S, Top.M);
		return statusChart;
	}

    private Chart createProgressChart(Richiesta.StatoRichiesta status, int value) {
		Chart chart = new Chart();
		chart.addClassName(status.toString().toLowerCase());
		chart.setSizeFull();

		com.vaadin.flow.component.charts.model.Configuration configuration = chart.getConfiguration();
		configuration.getChart().setType(ChartType.SOLIDGAUGE);
		configuration.setTitle("");
		configuration.getTooltip().setEnabled(false);

		configuration.getyAxis().setMin(0);
		configuration.getyAxis().setMax(100);
		configuration.getyAxis().getLabels().setEnabled(false);

		PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
		opt.getDataLabels().setEnabled(false);
		configuration.setPlotOptions(opt);

		DataSeriesItem point = new DataSeriesItem();
		point.setY(value);
		configuration.setSeries(new DataSeries(point));

		Pane pane = configuration.getPane();
		pane.setStartAngle(0);
		pane.setEndAngle(360);

		Background background = new Background();
		background.setShape(BackgroundShape.ARC);
		background.setInnerRadius("100%");
		background.setOuterRadius("110%");
		pane.setBackground(background);

		return chart;
	}

	private Component createRequestsHeader() {
		Label titolo =new Label("Richieste più recenti:");
        titolo.addClassName("h2");


		MyFlexLayout header = new MyFlexLayout(titolo);
		header.setAlignItems(Alignment.START);
        header.setAlignContent(ContentAlignment.START);
		return header;
	}

    private Component createCharts() {
		Row charts = new Row();

		for (Richiesta.StatoRichiesta status : Richiesta.StatoRichiesta.values()) {
			charts.add(createPaymentChart(status));
		}
		return charts;
	}
}

