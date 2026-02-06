# Component Factory Autocomplete for Vaadin 10+

This is server-side component of [&lt;vcf-autocomplete&gt;](https://github.com/vaadin-component-factory/vcf-autocomplete) Web Component.
Autocomplete is a text input with a panel of suggested options. When user change value of text input, panel with found options will be shown, so user can select one of the suggested options. Once user selected his option, it appears in text input. 

[Live Demo ↗](https://incubator.app.fi/autocomplete-demo/autocomplete)

## Important information about versioning (since version 5.0.0)

Earlier releases used the Vaadin major version as the component major version (`23.x`, `24.x`), which does not follow Semantic Versioning guidelines.

As part of restoring a proper semantic versioning scheme:

- The existing `23.x` version for Vaadin 23 should be considered as version **3.x**.
- The existing `24.x` version for Vaadin 24 should be considered as version **4.x**.
- Any further fixes for Vaadin 23 or 24 will be released using versions 3.x or 4.x respectively.
- New development for Vaadin 25 continues in the **5.x** major line.

## Usage

A simple use of the Autocomplete component would be the following: create autocomplete, add change listener in which you will add 
options to autocomplete, according to users input.

```java
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
```

## Setting up for development:

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone git@github.com:vaadin-component-factory/autocomplete.git
```

to install project, to your maven repository run
 
```mvn install```


## How to run the demo?

The Demo can be run going to the project `autocomplete-demo` and executing the maven goal:

```mvn jetty:run```


# License & Author

Apache License 2
