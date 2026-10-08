package com.vaadin.componentfactory;

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
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.shared.HasPrefix;
import com.vaadin.flow.component.shared.HasSuffix;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonArray;
import elemental.json.JsonFactory;
import elemental.json.impl.JreJsonFactory;

/*
 * #%L
 * VCF Autocomplete for Vaadin 24
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
@NpmPackage(value = "@lit-labs/observers", version = "2.0.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-autocomplete", version = "24.1.6")
@JsModule("@vaadin-component-factory/vcf-autocomplete/src/vcf-autocomplete.js")
public class Autocomplete extends LitTemplate implements HasTheme, HasSize,
        HasValue<Autocomplete.AutocompleteValueAppliedEvent, String>,
        Focusable<Autocomplete>, HasValidation, HasPrefix, HasSuffix {
    private static final String OPTIONS = "options";
    private static final String TEXTFIELD_SELECTOR = "this._textField";
    private static final String LIMIT_PROP = "limit";
    private static final String READONLY_PROP = "readonly";
    private static final String LABEL_PROP = "label";
    private static final String PLACEHOLDER_PROP = "placeholder";
    private static final String VALUE_PROP = "value";
    private static final String ENABLED_PROP = "enabled";

    private boolean invalid;
    private boolean readOnly;
    private boolean requiredIndicatorVisible;

    private String errorMessage;

    @Id("textField")
    private TextField textField;

    public Autocomplete() {
        this.readOnly = false;
        /* //TODO
        getElement().executeJs(String.format("textFieldStyles = %s.style;%s%s",
                TEXTFIELD_SELECTOR, "textFieldStyles.width='100%';",
                "textFieldStyles.height='100%';"));
         */
    }

    public Autocomplete(int limit) {
        this();
        setLimit(limit);
    }

    public void setLimit(int limit) {
        getElement().setProperty(LIMIT_PROP, limit);
    }

    @Override
    public void setErrorMessage(String s) {
        this.errorMessage = errorMessage;
        getElement().executeJs(String.format("%s.errorMessage=$0", TEXTFIELD_SELECTOR), errorMessage);
    }

    @Override
    public String getErrorMessage() {
        return Objects.nonNull(this.errorMessage) ? this.errorMessage : "";
    }

    @Override
    public void setInvalid(boolean b) {
        this.invalid = invalid;
        getElement().executeJs(String.format("%s.invalid=$0", TEXTFIELD_SELECTOR), invalid);
    }

    @Override
    public boolean isInvalid() {
        return this.invalid;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.getElement().setProperty(ENABLED_PROP, enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.getElement().getProperty(ENABLED_PROP, true);
    }

    public String getPlaceholderString() {
        return getElement().getProperty(PLACEHOLDER_PROP);
    }

    public void setPlaceholder(String placeholder) {
        getElement().setProperty(PLACEHOLDER_PROP,
                placeholder == null ? "" : placeholder);
    }

    public String getLabel() {
        return getElement().getProperty(LABEL_PROP);
    }


    public void setLabel(String label) {
        getElement().setProperty(LABEL_PROP, label == null ? "" : label);
    }

    public void setOptions(List<String> options) {
        JsonFactory jsonFactory = new JreJsonFactory();
        JsonArray jsonArray = jsonFactory.createArray();
        for (int i = 0; i < options.size(); i++) {
            jsonArray.set(i, options.get(i));
        }
        getElement().setPropertyJson(OPTIONS, jsonArray);
    }

    public Registration addChangeListener(
            ComponentEventListener<AucompleteChangeEvent> listener) {
        return addListener(AucompleteChangeEvent.class, listener);
    }

    @Override
    public Registration addValueChangeListener(
            HasValue.ValueChangeListener<? super AutocompleteValueAppliedEvent> listener) {
        return addAutocompleteValueAppliedListener(listener::valueChanged);
    }

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
    public Registration addAutocompleteValueAppliedListener(
            ComponentEventListener<AutocompleteValueAppliedEvent> listener) {
        return addListener(AutocompleteValueAppliedEvent.class, listener);
    }

    public void setValue(String value) {
        getElement().callJsFunction("_setValue", value);
    }

    @Synchronize(property = VALUE_PROP, value = "value-changed")
    public String getValue() {
        return getElement().getProperty(VALUE_PROP, "");
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        getElement().setProperty(READONLY_PROP, readOnly);
        getElement().getStyle().set("pointer-events", readOnly ? "none" : "auto");
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

    public Registration addValueClearListener(
            ComponentEventListener<ValueClearEvent> listener) {
        return addListener(ValueClearEvent.class, listener);
    }

    @DomEvent("clear")
    public static class ValueClearEvent extends ComponentEvent<Autocomplete> {
        public ValueClearEvent(Autocomplete source, boolean fromClient) {
            super(source, fromClient);
        }
    }


    @Override
    public void setPrefixComponent(Component component) {
        this.textField.setPrefixComponent(component);
    }

    @Override
    public Component getPrefixComponent() {
        return this.textField.getPrefixComponent();
    }

    @Override
    public void setSuffixComponent(Component component) {
        this.textField.setSuffixComponent(component);
    }

    @Override
    public Component getSuffixComponent() {
        return this.textField.getSuffixComponent();
    }
}
