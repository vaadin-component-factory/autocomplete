# Autocomplete


[Live Demo ↗](https://incubator.app.fi/autocomplete-demo/autocomplete)

[&lt;vcf-autocomplete&gt;](https://vaadin.com/components/autocomplete) is a text input with a panel of suggested options.

&lt;autocomplete&gt; is built with Vaadin Component Factory. To use it, you need to have a access to [Vaadin Prime](https://vaadin.com/pricing).


# What does the component do?

Autocomplete is a Web Component providing an easy way to provide an autocomplete functionality.

# How is it used?

A simple use of the Autocomplete component would be the following.
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



# How to run the demo?

The Demo can be run going to the project autocomplete-demo and executing the maven goal:

```mvn jetty:run```


## License & Author

This Add-on is distributed under [Commercial Vaadin Add-on License version 3](http://vaadin.com/license/cval-3) (CVALv3). For license terms, see LICENSE.txt.

Autocomplete is written by Vaadin Ltd.


## Setting up for development:

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone git@github.com/vaadin-component-factory/autocomplete.git
```

To build and install the project into the local repository run 

```mvn install```