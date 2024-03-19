package com.example.application.views.utili;

import java.util.ArrayList;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class MyFlexLayout extends FlexLayout{
	private ArrayList<Size> spacings;

	public MyFlexLayout(){
		super();
		spacings=new ArrayList<>();
	}



	public MyFlexLayout(Component... components ){
		super(components);
		spacings= new ArrayList<>();
	}

    public void setMargin(Size... sizes) {
		for (Size size : sizes) {
			for (String attribute : size.getMarginAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}
	public void setPadding(Size... sizes) {
		removePadding();
		for (Size size : sizes) {
			for (String attribute : size.getPaddingAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void removePadding() {
		getStyle().remove("padding");
		getStyle().remove("padding-bottom");
		getStyle().remove("padding-left");
		getStyle().remove("padding-right");
		getStyle().remove("padding-top");
	}

	public void setSpacing(Size... sizes) {
		// Remove old styles (if applicable)
		for (Size spacing : spacings) {
			removeClassName(spacing.getSpacingClassName());
		}
		spacings.clear();

		// Add new
		for (Size size : sizes) {
			addClassName(size.getSpacingClassName());
			spacings.add(size);
		}
	}
}