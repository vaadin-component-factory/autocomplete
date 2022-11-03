package com.vaadin.componentfactory;

import com.vaadin.componentfactory.Autocomplete.AutocompleteValueAppliedEvent;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;

/*
 * #%L
 * VCF Autocomplete for Vaadin 10
 * %%
 * Copyright (C) 2021 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Server-side component for the <code>vcf-autocomplete</code> element.
 * <p>
 * Note: isOpened,setOpened and setValue are not supported. The current
 * implementation of the polymer-side component does not allow it.
 *
 * @author Vaadin Ltd
 */
@Tag("vcf-autocomplete")
@NpmPackage(value = "@vaadin-component-factory/vcf-autocomplete", version = "1.2.10")
@JsModule("@vaadin-component-factory/vcf-autocomplete/src/vcf-autocomplete.js")
public class Autocomplete extends
        PolymerTemplate<Autocomplete.AutocompleteTemplateModel> implements
        HasTheme, HasSize, HasValue<AutocompleteValueAppliedEvent, String>,
        Focusable<Autocomplete>, HasValidation {

    // PROPERTIES
    private static final String VALUE_PROP = "value";
    private static final String LIMIT_PROP = "limit";
    private static final String LOADING_PROP = "loading";
    private static final String LABEL_PROP = "label";
    private static final String PLACEHOLDER_PROP = "placeholder";
    private static final String CASESENSITIVE_PROP = "caseSensitive";

    @Id
    private TextField textField;

    /**
     * Default constructor.
     */
    public Autocomplete() {
        textField.setSizeFull();
    }

    /**
     * Constructor that sets the maximum number of displayed options.
     *
     * @param limit
     *            maximum number of displayed options
     */
    public Autocomplete(int limit) {
        this();
        setLimit(limit);
    }

    /**
     * Gets the current imputed text from the Autocomplete.
     *
     * @return value text
     */
    @Synchronize(property = VALUE_PROP, value = "value-changed")
    public String getValue() {
        return getElement().getProperty(VALUE_PROP, "");
    }

    /**
     * Sets the current options that will be shown under the text-field in the
     * Autocomplete component.
     * <p>
     * The maximum displayed options will be lower-equal to the set limit.
     *
     * @param options
     *            Hints/options to the user
     */
    public void setOptions(List<String> options) {
        getModel().setOptions(options);
    }

    /**
     * Gets the options that are currently available to the user.
     *
     * @return options list of options/hints/suggestions
     */
    public List<String> getOptions() {
        return getModel().getOptions();
    }

    /**
     * Sets the limit to the maximum number of displayed options.
     * <p>
     * The limit should be bigger than 0.
     *
     * @param limit
     *            maximum number of displayed options
     */
    public void setLimit(int limit) {
        getElement().setProperty(LIMIT_PROP, limit);
    }

    /**
     * Gets the the maximum number of displayed options.
     *
     * @return limit maximum number of displayed options. -1 is returned if
     *         there is not a limit set.
     */
    public int getLimit() {
        return getElement().getProperty(LIMIT_PROP, -1);
    }

    /**
     * Sets the highlight to be case sensitive or not
     *
     * @param caseSensitive
     *            true = case sensitive highlighting
     */
    public void setCaseSensitive(boolean caseSensitive) {
        getElement().setProperty(CASESENSITIVE_PROP, caseSensitive);
    }

    /**
     * Gets the current mode of highlighting
     *
     * @return boolean value
     */
    public boolean isCaseSensitive() {
        return getElement().getProperty(CASESENSITIVE_PROP, false);
    }

    /**
     * Show a loading icon in the Autocomplete panel.
     * <p>
     * It is useful if the loading of data is slow.
     *
     * @param loading
     *            <code>true</code> show loading icon <code>false</code>,
     *            otherwise
     */
    public void setLoading(boolean loading) {
        getElement().setProperty(LOADING_PROP, loading);
    }

    /**
     * Checks if there is loading icon in the Autocomplete panel.
     *
     * @return loading <code>true</code> loading icon is visible
     *         <code>false</code>, otherwise
     */
    public boolean isLoading() {
        return getElement().getProperty(LOADING_PROP, false);
    }

    /**
     * Gets the placeholder.
     * <p>
     * A placeholder string in addition to the label.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     *
     * @return the {@code placeholder} property from the webcomponent
     */
    public String getPlaceholderString() {
        return getElement().getProperty(PLACEHOLDER_PROP);
    }

    /**
     * Sets the placeholder.
     * <p>
     * Description copied from corresponding location in WebComponent:
     * <p>
     * A placeholder string in addition to the label.
     *
     * @param placeholder
     *            the String value to set
     */
    public void setPlaceholder(String placeholder) {
        getElement().setProperty(PLACEHOLDER_PROP,
                placeholder == null ? "" : placeholder);
    }

    /**
     * Gets the label.
     * <p>
     * String used for the label element.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     *
     * @return the {@code label} property from the webcomponent
     */
    public String getLabel() {
        return getElement().getProperty(LABEL_PROP);
    }

    /**
     * Sets the label.
     * <p>
     * String used for the label element.
     *
     * @param label
     *            the String value to set
     */
    public void setLabel(String label) {
        getElement().setProperty(LABEL_PROP, label == null ? "" : label);
    }

    /**
     * Adds a listener for {@code ValueChangeEvent} events fired by the
     * webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    public Registration addChangeListener(
            ComponentEventListener<AucompleteChangeEvent> listener) {
        return addListener(AucompleteChangeEvent.class, listener);
    }

    /**
     * Adds a listener for {@code AutocompleteValueAppliedEvent} events fired by
     * the webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    public Registration addAutocompleteValueAppliedListener(
            ComponentEventListener<AutocompleteValueAppliedEvent> listener) {
        return addListener(AutocompleteValueAppliedEvent.class, listener);
    }

    /**
     * Adds a listener for {@code ValueClearEvent}.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    public Registration addValueClearListener(
            ComponentEventListener<ValueClearEvent> listener) {
        return addListener(ValueClearEvent.class, listener);
    }

    @EventHandler
    public void clear() {
        fireEvent(new ValueClearEvent(this, true));
    }

    /**
     * ValueChangeEvent is created when the value of the TextField changes.
     */
    @DomEvent("value-changed")
    public static class AucompleteChangeEvent
            extends ComponentEvent<Autocomplete> {

        private final String value;

        public AucompleteChangeEvent(Autocomplete source, boolean fromClient,
                @EventData("event.detail.value") String value) {
            super(source, fromClient);
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * AutocompleteValueAppliedEvent is created when the user clicks on a option
     * of the Autocompleter.
     */
    @DomEvent("vcf-autocomplete-value-applied")
    public static class AutocompleteValueAppliedEvent
            extends ComponentEvent<Autocomplete>
            implements HasValue.ValueChangeEvent<String> {

        private final String value;

        public AutocompleteValueAppliedEvent(Autocomplete source,
                boolean fromClient,
                @EventData("event.detail.value") String value) {
            super(source, fromClient);
            this.value = value;
            this.source = source;
        }

        public String getValue() {
            return value;
        }

        @Override
        public HasValue getHasValue() {
            // TODO Auto-generated method stub
            return (HasValue) source;
        }

        @Override
        public String getOldValue() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    /**
     * ValueClearEvent is created when the user clicks on the clean button.
     */
    @DomEvent("clear")
    public static class ValueClearEvent extends ComponentEvent<Autocomplete> {

        public ValueClearEvent(Autocomplete source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    /**
     * This model binds properties {@link Autocomplete} and
     * vcf-autocomplete.html
     */
    public interface AutocompleteTemplateModel extends TemplateModel {
        List<String> getOptions();

        void setOptions(List<String> options);
    }

    public void setValue(String value) {
        getElement().executeJs("this._applyValue(\"" + value + "\");");
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        if (readOnly) {
            getElement().getStyle().set("pointer-events", "none");
        } else {
            getElement().getStyle().set("pointer-events", "auto");
        }
        textField.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return textField.isReadOnly();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        textField.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return textField.isRequiredIndicatorVisible();
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<? super AutocompleteValueAppliedEvent> listener) {
        return addAutocompleteValueAppliedListener(event -> {
            listener.valueChanged(event);
        });
    }

    @Override
    public String getErrorMessage() {
        if (textField != null) {
            return textField.getErrorMessage();
        } else {
            return null;
        }
    }

    @Override
    public boolean isInvalid() {
        if (textField != null) {
            return textField.isInvalid();
        } else {
            return false;
        }
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        if (textField != null) {
            textField.setErrorMessage(errorMessage);
        }
    }

    @Override
    public void setInvalid(boolean invalid) {
        if (textField != null) {
            textField.setInvalid(invalid);
        }
    }

    @Override
    public void focus() {
        if (textField != null) {
            textField.focus();
        }
    }

    @Override
    public void blur() {
        if (textField != null) {
            textField.blur();
        }
    }

    @Override
    public void setTabIndex(int tabIndex) {
        if (textField != null) {
            textField.setTabIndex(tabIndex);
        }
    }

    @Override
    public int getTabIndex() {
        return textField.getTabIndex();
    }

    @Override
    public ShortcutRegistration addFocusShortcut(Key key,
            KeyModifier... keyModifiers) {
        return textField.addFocusShortcut(key, keyModifiers);
    }
}