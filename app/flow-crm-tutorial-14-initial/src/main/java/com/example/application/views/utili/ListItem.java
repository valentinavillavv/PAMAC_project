package com.example.application.views.utili;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

@CssImport("./styles/components/list-item.css")
public class ListItem extends MyFlexLayout {

	private final String CLASS_NAME = "list-item";

	private Div prefix;
	private Div suffix;

	private MyFlexLayout content;

	private Label primary;
	private Label secondary;

	public ListItem(String primary, String secondary) {
		addClassName(CLASS_NAME);

		setAlignItems(FlexComponent.Alignment.CENTER);
		setPadding(Wide.RESPONSIVE_L);
		setSpacing(Right.L);

		this.primary = new Label(primary);
		this.secondary = createLabel(FontSize.S, TextColor.SECONDARY,secondary);

		content = new MyFlexLayout(this.secondary, this.primary);
		content.setClassName(CLASS_NAME + "__content");
		content.setFlexDirection(FlexDirection.COLUMN);
		add(content);
	}

	

	public static Label createLabel(FontSize size, TextColor color,String text) {
		Label label = new Label(text);
		setFontSize(size, label);
		setTextColor(color, label);
		return label;
	}

	public static void setTextColor(TextColor textColor, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("color", textColor.getValue());
		}
	}

	public static void setFontSize(FontSize fontSize, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("font-size",fontSize.getValue());}
	}

public static Label createLabel(FontSize size, String text) {
return createLabel(size, TextColor.BODY, text);
}

public static Label createLabel(TextColor color, String text) {
return createLabel(FontSize.M, color, text);
}

	public ListItem(String primary) {
		this(primary, "");
	}

	/* === PREFIX === */

	public ListItem(Component prefix, String primary, String secondary) {
		this(primary, secondary);
		setPrefix(prefix);
	}

	public ListItem(Component prefix, String primary) {
		this(prefix, primary, "");
	}

	/* === SUFFIX === */

	public ListItem(String primary, String secondary, Component suffix) {
		this(primary, secondary);
		setSuffix(suffix);
	}

	public ListItem(String primary, Component suffix) {
		this(primary, null, suffix);
	}

	/* === PREFIX & SUFFIX === */

	public ListItem(Component prefix, String primary, String secondary,
	                Component suffix) {
		this(primary, secondary);
		setPrefix(prefix);
		setSuffix(suffix);
	}

	public ListItem(Component prefix, String primary, Component suffix) {
		this(prefix, primary, "", suffix);
	}

	/* === MISC === */

	public MyFlexLayout getContent() {
		return content;
	}

	public void setWhiteSpace(WhiteSpace whiteSpace) {
		setWhiteSpace(whiteSpace, this);
	}

	public static void setWhiteSpace(WhiteSpace whiteSpace,
	Component... components) {
		for (Component component : components) {
			component.getElement().setProperty("white-space",
			whiteSpace.getValue());
		}
	}

	public void setReverse(boolean reverse) {
		if (reverse) {
			content.setFlexDirection(FlexDirection.COLUMN_REVERSE);
		} else {
			content.setFlexDirection(FlexDirection.COLUMN);
		}
	}

	public void setHorizontalPadding(boolean horizontalPadding) {
		if (horizontalPadding) {
			getStyle().remove("padding-left");
			getStyle().remove("padding-right");
		} else {
			getStyle().set("padding-left", "0");
			getStyle().set("padding-right", "0");
		}
	}

	public void setPrimaryText(String text) {
		primary.setText(text);
	}

	public Label getPrimary() {
		return primary;
	}

	public void setSecondaryText(String text) {
		secondary.setText(text);
	}

	public void setPrefix(Component... components) {
		if (prefix == null) {
			prefix = new Div();
			prefix.setClassName(CLASS_NAME + "__prefix");
			getElement().insertChild(0, prefix.getElement());
			getElement().setAttribute("with-prefix", true);
		}
		prefix.removeAll();
		prefix.add(components);
	}

	public void setSuffix(Component... components) {
		if (suffix == null) {
			suffix = new Div();
			suffix.setClassName(CLASS_NAME + "__suffix");
			getElement().insertChild(getElement().getChildCount(),
					suffix.getElement());
			getElement().setAttribute("with-suffix", true);
		}
		suffix.removeAll();
		suffix.add(components);
	}

	public void setDividerVisible(boolean visible) {
		if (visible) {
			getElement().setAttribute("with-divider", true);
		} else {
			getElement().removeAttribute("with-divider");
		}
	}

}
