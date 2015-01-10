package zal.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DialogBox;

public class PersonEditor extends DialogBox {
        private TextBox textBox = new TextBox();
        public PersonEditor(HTML message, String value, final AsyncCallback<String> callback) {
                super(false, true);
                setAnimationEnabled(true);
            final Button closeButton = new Button("Ok");
            final Button deleteButton = new Button("Usuń");
            final Button cancelButton = new Button("Anuluj");
            // We can set the id of a widget by accessing its Element
            VerticalPanel dialogVPanel = new VerticalPanel();
            dialogVPanel.addStyleName("dialogVPanel");
            dialogVPanel.add(message);
            textBox.setText(value);
            dialogVPanel.add(textBox);
            dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
            HorizontalPanel dialogHPanel = new HorizontalPanel();
            dialogHPanel.add(closeButton);
            dialogHPanel.add(deleteButton);
            dialogHPanel.add(cancelButton);
            dialogVPanel.add(dialogHPanel);
            setWidget(dialogVPanel);

            // Add a handler to close the DialogBox
            closeButton.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                callback.onSuccess(textBox.getText());
                hide();
              }
            });
            deleteButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                  callback.onSuccess(null);
                  hide();
                }
              });
            cancelButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                  callback.onFailure(null);
                  hide();
                }
              });

      center();
      textBox.setFocus(true);
    }
  }
