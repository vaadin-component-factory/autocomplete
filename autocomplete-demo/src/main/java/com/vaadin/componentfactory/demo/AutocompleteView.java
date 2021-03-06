package com.vaadin.componentfactory.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.componentfactory.Autocomplete;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route("autocomplete")
@CssImport( value = "./styles/test.css", themeFor = "vaadin-text-field")
public class AutocompleteView extends DemoView {

    private final Fairy fairy = Fairy.create();

    private final List<Person> people = createPeople(100);
    private Grid<Person> peopleGrid;

	private boolean startsWith = true;

    public AutocompleteView() {

    }

    @Override
    protected void initView() {
        createBasicExample();
        createExampleAutocompleteAndGrid();
    }

    private void createBasicExample(){
        H3 inputH3 = new H3("Current input: ");
        H3 selectionH3 = new H3("Selection: ");
        Autocomplete autocomplete = new Autocomplete(5);

        autocomplete.addChangeListener(event -> {
            String text = event.getValue();
            autocomplete.setOptions(findOptions(text));
            inputH3.setText("Current input: " + text);
        });

        autocomplete.addValueChangeListener(event -> {
            selectionH3.setText("Selection: " + event.getValue());
        });

        autocomplete.addValueClearListener(event -> {
            selectionH3.setText("Selection: " + "");
        });

        autocomplete.setLabel("Find what you want:");
        autocomplete.setPlaceholder("search ...");

        autocomplete.setWidth("300px");
        autocomplete.setThemeName("my-autocomplete");

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
        		
        addCard("Basic Autocomplete",inputH3,selectionH3,autocomplete,startsWithBox,readOnlyBox);
        autocomplete.focus();
    }

    private void createExampleAutocompleteAndGrid(){

        initGrid();

        Autocomplete autocomplete = new Autocomplete();

        autocomplete.setLimit(5);

        autocomplete.addChangeListener(event -> {
            String text = event.getValue();
            autocomplete.setOptions(findOptions(text));
        });

        autocomplete.addChangeListener(event -> {
            refreshContent(event.getValue());
        });

        autocomplete.addValueClearListener(event -> {
            peopleGrid.setItems(Collections.EMPTY_LIST);
        });

        autocomplete.setLabel("Find what you want:");
        autocomplete.setPlaceholder("search ...");

        addCard("Autocomplete and grid",autocomplete, peopleGrid);
    }



    public void initGrid() {
        peopleGrid = new Grid<>();
        peopleGrid.addColumn(Person::firstName).setHeader("First name");
        peopleGrid.addColumn(Person::lastName).setHeader("Last name");
        peopleGrid.addColumn(Person::email).setHeader("email");
    }


    public void refreshContent(String text) {
        peopleGrid.setItems(findPeople(text));
    }

    private List<Person> createPeople(int n) {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            people.add(fairy.person());
        }
        return people;
    }

    private List<String> findOptions(final String text) {
        return findPeople(text).stream().map(person -> person.firstName()).collect(Collectors.toList());
    }

    private List<Person> findPeople(final String text) {
        String matchText = text.trim().toLowerCase();

        return people.stream().filter(person -> {
            if (person != null) {
                String name = person.firstName();
                name = (name == null) ? "" : name.toLowerCase();
				if (startsWith) {
                	return name.startsWith(matchText);
                } else {
                	return name.contains(matchText);
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}
