package edu.stanford.bmir.protege.web.client.form;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import edu.stanford.bmir.protege.web.client.editor.ValueEditor;
import edu.stanford.bmir.protege.web.client.editor.ValueListEditor;
import edu.stanford.bmir.protege.web.shared.DirtyChangedHandler;
import edu.stanford.bmir.protege.web.shared.form.data.FormDataList;
import edu.stanford.bmir.protege.web.shared.form.data.FormDataValue;

import java.util.List;
import java.util.Optional;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 30/03/16
 */
public class RepeatingEditor implements ValueEditor<FormDataValue> {

    private ValueListEditor<FormDataValue> delegate;

    private HandlerManager handlerManager;

    public RepeatingEditor(ValueListEditor<FormDataValue> delegate) {
        this.delegate = delegate;
        this.handlerManager = new HandlerManager(this);
        this.delegate.setEnabled(true);
        this.delegate.addValueChangeHandler(event -> {
           ValueChangeEvent.fire(this, getValue());
        });
        delegate.setNewRowMode(ValueListEditor.NewRowMode.MANUAL);
    }

    @Override
    public void setValue(FormDataValue object) {
        delegate.setValue(object.asList());
    }

    @Override
    public void clearValue() {
        delegate.clearValue();
    }

    @Override
    public Optional<FormDataValue> getValue() {
        Optional<List<FormDataValue>> value = delegate.getValue();
        // Always returns a value
        return Optional.of(value.map(FormDataList::new)
                                .orElse(FormDataList.empty()));
    }

    @Override
    public boolean isDirty() {
        return delegate.isDirty();
    }

    @Override
    public HandlerRegistration addDirtyChangedHandler(DirtyChangedHandler handler) {
        return delegate.addDirtyChangedHandler(handler);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Optional<FormDataValue>> handler) {
        return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    @Override
    public boolean isWellFormed() {
        return delegate.isWellFormed();
    }

    @Override
    public Widget asWidget() {
        return delegate.asWidget();
    }
}
