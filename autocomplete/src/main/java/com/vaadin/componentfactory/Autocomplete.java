package com.vaadin.componentfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonArray;
import elemental.json.JsonFactory;
import elemental.json.impl.JreJsonFactory;

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
@NpmPackage(value = "@vaadin-component-factory/vcf-autocomplete", version = "24.1.1")
@JsModule("@vaadin-component-factory/vcf-autocomplete/src/vcf-autocomplete.js")
public class Autocomplete extends Component implements HasTheme, HasSize,
        HasValue<Autocomplete.AutocompleteValueAppliedEvent, String>,
        Focusable<Autocomplete>, HasValidation {

    // PROPERTIES
    private static final String OPTIONS = "options";
    private static final String TEXTFIELD_SELECTOR = "this._textField";
    private static final String LIMIT_PROP = "limit";
    private static final String LOADING_PROP = "loading";
    private static final String LABEL_PROP = "label";
    private static final String PLACEHOLDER_PROP = "placeholder";
    private static final String VALUE_PROP = "value";
    private static final String CASESENSITIVE_PROP = "caseSensitive";

    // Component state attributes
    private boolean readOnly;
    private boolean requiredIndicatorVisible;
    private boolean invalid;
    private int tabIndex;
    private String errorMessage;

    /**
     * Default constructor.
     */
    public Autocomplete() {}

    /**
     * Constructor that sets the maximum number of displayed options.
     *
     * @param limit maximum number of displayed options
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
     * @param options Hints/options to the user
     */

    public void setOptions(List<String> options) {
        JsonFactory jsonFactory = new JreJsonFactory();
        JsonArray jsonArray = jsonFactory.createArray();
        for (int i = 0; i < options.size(); i++) {
            jsonArray.set(i, options.get(i));
        }
        getElement().setPropertyJson(OPTIONS, jsonArray);
    }

    /**
     * Gets the options that are currently available to the user.
     *
     * @return options list of options/hints/suggestions
     */
    public List<String> getOptions() {
        List<String> optionsList = new ArrayList<>();
        JsonArray optionsArray = (JsonArray) getElement().getPropertyRaw(OPTIONS);
        for (int i = 0; i < optionsArray.length(); i++) {
            optionsList.add(optionsArray.getString(i));
        }
        return optionsList;
    }

    /**
     * Sets the limit to the maximum number of displayed options.
     * <p>
     * The limit should be bigger than 0.
     *
     * @param limit maximum number of displayed options
     */
    public void setLimit(int limit) {
        getElement().setProperty(LIMIT_PROP, limit);
    }

    /**
     * Gets the the maximum number of displayed options.
     *
     * @return limit maximum number of displayed options. -1 is returned if
     * there is not a limit set.
     */
    public int getLimit() {
        return getElement().getProperty(LIMIT_PROP, -1);
    }

    /**
     * Sets the highlight to be case sensitive or not
     *
     * @param caseSensitive true = case sensitive highlighting
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
     * @param loading <code>true</code> show loading icon <code>false</code>,
     *                otherwise
     */
    public void setLoading(boolean loading) {
        getElement().setProperty(LOADING_PROP, loading);
    }

    /**
     * Checks if there is loading icon in the Autocomplete panel.
     *
     * @return loading <code>true</code> loading icon is visible
     * <code>false</code>, otherwise
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
     * @param placeholder the String value to set
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
     * @param label the String value to set
     */
    public void setLabel(String label) {
        getElement().setProperty(LABEL_PROP, label == null ? "" : label);
    }

    /**
     * Adds a listener for {@code ValueChangeEvent} events fired by the
     * webcomponent.
     *
     * @param listener the listener
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
     * @param listener the listener
     * @return a {@link Registration} for removing the event listener
     */
    public Registration addAutocompleteValueAppliedListener(
            ComponentEventListener<AutocompleteValueAppliedEvent> listener) {
        return addListener(AutocompleteValueAppliedEvent.class, listener);
    }

    /**
     * Adds a listener for {@code ValueClearEvent}.
     *
     * @param listener the listener
     * @return a {@link Registration} for removing the event listener
     */
    public Registration addValueClearListener(
            ComponentEventListener<ValueClearEvent> listener) {
        return addListener(ValueClearEvent.class, listener);
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

    public void setValue(String value) {
        getElement().executeJs("this._applyValue(\"" + value + "\");");
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        getElement().getStyle().set("pointer-events", readOnly ? "none" : "auto");
        getElement().executeJs(String.format("%s.readonly=$0", TEXTFIELD_SELECTOR), readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return this.readOnly;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        this.requiredIndicatorVisible = requiredIndicatorVisible;
        getElement().executeJs(String.format("%s.required=$0", TEXTFIELD_SELECTOR), requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return this.requiredIndicatorVisible;
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<? super AutocompleteValueAppliedEvent> listener) {
        return addAutocompleteValueAppliedListener(listener::valueChanged);
    }

    @Override
    public String getErrorMessage() {
        return Objects.nonNull(this.errorMessage) ? this.errorMessage : "";
    }

    @Override
    public boolean isInvalid() {
        return this.invalid;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        getElement().executeJs(String.format("%s.errorMessage=$0", TEXTFIELD_SELECTOR), errorMessage);
    }

    @Override
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
        getElement().executeJs(String.format("%s.invalid=$0", TEXTFIELD_SELECTOR), invalid);
    }

    @Override
    public void focus() {
        getElement().executeJs(String.format("%s.focus();", TEXTFIELD_SELECTOR));
    }

    @Override
    public void blur() {
        getElement().executeJs(String.format("%s.blur();", TEXTFIELD_SELECTOR));
    }

    @Override
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        getElement().executeJs(String.format("%s.tabindex=$0", TEXTFIELD_SELECTOR), tabIndex);
    }

    @Override
    public int getTabIndex() {
        return this.tabIndex;
    }

    @Override
    public ShortcutRegistration addFocusShortcut(Key key,
                                                 KeyModifier... keyModifiers) {
        return UI.getCurrent().addShortcutListener(
                () -> getElement().executeJs(String.format("%s.focus();",
                        TEXTFIELD_SELECTOR)), key, keyModifiers);
    }

}
