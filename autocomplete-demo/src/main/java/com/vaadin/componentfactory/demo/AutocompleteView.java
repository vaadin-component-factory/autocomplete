package com.vaadin.componentfactory.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.componentfactory.Autocomplete;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route("autocomplete")
public class AutocompleteView extends DemoView {

    private final Fairy fairy = Fairy.create();

    private final List<Person> people = createPeople(100);
    private Grid<Person> peopleGrid;

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

        autocomplete.addAutocompleteValueAppliedListener(event -> {
            selectionH3.setText("Selection: " + event.getValue());
        });

        autocomplete.addValueClearListener(event -> {
            selectionH3.setText("Selection: " + "");
        });

        autocomplete.setLabel("Find what you want:");
        autocomplete.setPlaceholder("search ...");

        autocomplete.setWidth("300px");
        autocomplete.setValue("sa");
        addCard("Basic Autocomplete",inputH3,selectionH3,autocomplete);
    }

    private void createExampleAutocompleteAndGrid(){

        initGrid();

        Autocomplete autocomplete = new Autocomplete();

        autocomplete.setLimit(5);

        autocomplete.addChangeListener(event -> {
            String text = event.getValue();
            autocomplete.setOptions(findOptions(text));
        });

        autocomplete.addAutocompleteValueAppliedListener(event -> {
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

                return name.startsWith(matchText);
            }
            return false;
        }).collect(Collectors.toList());
    }
}
