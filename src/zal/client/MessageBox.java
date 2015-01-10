package zal.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DialogBox;

public class MessageBox extends DialogBox {
        public MessageBox(HTML message) {
                super();
                setAnimationEnabled(true);
            final Button closeButton = new Button("Close");
            // We can set the id of a widget by accessing its Element
            closeButton.getElement().setId("closeButton");
            VerticalPanel dialogVPanel = new VerticalPanel();
            dialogVPanel.addStyleName("dialogVPanel");
            dialogVPanel.add(message);
            dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
            dialogVPanel.add(closeButton);
            setWidget(dialogVPanel);

            // Add a handler to close the DialogBox
            closeButton.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                hide();
              }
            });

            center();
        closeButton.setFocus(true);
        }
}