package com.vaadin.componentfactory.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.componentfactory.Autocomplete;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@PageTitle("Basic Demo")
@Route(value = "basic")
@RouteAlias(value = "")
public class AutocompleteView extends HorizontalLayout {

    private List<String> allOptions = Arrays.asList("Mozzarella", "Rucula", "Garlic");;
    private boolean startsWith = true;
    private List<String> findOptions(final String text) {
        String matchText = text.trim().toLowerCase();

        return allOptions.stream().filter(option -> {
            if (option != null) {
                if (startsWith) {
                    return option.toLowerCase().startsWith(matchText);
                } else {
                    return option.toLowerCase().contains(matchText);
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    public AutocompleteView() {
        H4 inputH4 = new H4("Current input: ");
        H4 selectionH3 = new H4("Selection: ");
        Autocomplete autocomplete = new Autocomplete(5);
        autocomplete.setOptions(allOptions);

        autocomplete.setLabel("Find what you want:");
        autocomplete.setPlaceholder("search ...");

        autocomplete.setWidth("300px");
        autocomplete.setThemeName("my-autocomplete");

        autocomplete.setSuffixComponent(VaadinIcon.SEARCH.create());
        autocomplete.setPrefixComponent(new Span("Input: "));

        autocomplete.addChangeListener(event -> {
            String text = event.getValue();
            autocomplete.setOptions(findOptions(text));
            inputH4.setText("Current input: " + text);
        });

        autocomplete.addValueChangeListener(event -> {
            selectionH3.setText("Selection: " + event.getValue());
        });

        autocomplete.addValueClearListener(event -> {
            selectionH3.setText("Selection: " + "");
        });

        Checkbox startsWithBox = new Checkbox("startsWith / contains");
        startsWithBox.setValue(true);
        startsWithBox.addValueChangeListener(event -> {
            startsWith = event.getValue();
        });

        Checkbox readOnlyBox = new Checkbox("readOnly");
        readOnlyBox.setValue(false);
        readOnlyBox.addValueChangeListener(event -> {
            autocomplete.setReadOnly(event.getValue());
            autocomplete.focus();
        });

        add(new VerticalLayout(
                new H3("Basic Autocomplete"),
                new HorizontalLayout(inputH4,selectionH3),
                new HorizontalLayout(autocomplete),
                new HorizontalLayout(startsWithBox, readOnlyBox)
        ));
        autocomplete.focus();
    }

}
