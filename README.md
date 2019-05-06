# Component Factory Autocomplete for Vaadin 10+

This is server-side component of [&lt;vcf-autocomplete&gt;](https://github.com/vaadin-component-factory/vcf-autocomplete) Web Component.
Autocomplete is a text input with a panel of suggested options. When user change value of text input, panel with found options will be shown, so user can select one of the suggested options. Once user selected his option, it appears in text input. 


[Live Demo ↗](https://incubator.app.fi/autocomplete-demo/autocomplete)

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


# Vaadin Prime
This component is available in Vaadin Prime subscription. It is still open source, but you need to have a valid CVAL license in order to use it. Read more at: https://vaadin.com/pricing

# License & Author
This Add-on is distributed under [Commercial Vaadin Add-on License version 3](http://vaadin.com/license/cval-3) (CVALv3). For license terms, see LICENSE.txt.

Autocomplete is written by Vaadin Ltd.