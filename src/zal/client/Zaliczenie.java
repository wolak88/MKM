package zal.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import zal.shared.Person;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;


//Dopisałem się Piotr G :D


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Zaliczenie implements EntryPoint {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final PersonServiceAsync personService = GWT.create(PersonService.class);

	private TextBox newPerson = new TextBox();
	private ListBox personList = new ListBox();
	
    private void personListRefresh() {
    	logger.log(Level.INFO, "Przygotowuję wywołanie getAllPersons");
    	personService.getAllPersons(new AsyncCallback<Person[]> () {

			@Override
			public void onFailure(Throwable error) {
				logger.log(Level.WARNING, "Błąd podczas pobierania danych o osobie - " + error.toString());
			}

			@Override
			public void onSuccess(Person[] persons) {
				personList.clear();
				for(Person person: persons) {
					personList.addItem(person.getFirstName() + " " + person.getLastName(), person.getPesel().toString());
				}
			}
    		
    	});
      }

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		logger.log(Level.INFO, "onModuleLoad wystartował");
		
		RootPanel.get("newPerson").add(newPerson);
		
		Button newPersonButton = new Button("Dodaj osobę");
		newPersonButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				logger.log(Level.INFO, "PersonService.addPerson call");
				
				personService.addPersonByString(newPerson.getText(), new AsyncCallback<Long> () {
	                   public void onFailure(Throwable error) {
	                	   logger.log(Level.WARNING, "Błąd podczas wywołania RPC addPerson - " + error.toString());
	                   }
	                   public void onSuccess(Long pesel) {
	                	   if(pesel != null) {
	                		   logger.log(Level.INFO, "Nowa osoba dodana z peselem " + pesel.toString());
	                		   personListRefresh();
	                	   } else {
	                		   logger.log(Level.WARNING, "Osoba nie została dodana");
	                	   }
	                   }
					});
			}
		});
		RootPanel.get("newPersonButton").add(newPersonButton);
		
		personList.setVisibleItemCount(5);
		personList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
                final Long pesel = Long.parseLong(personList.getValue(personList.getSelectedIndex()));
                String oldName = personList.getItemText(personList.getSelectedIndex());
                logger.log(Level.INFO, "Szykuję się do edycji " + pesel + ": " + oldName);

                new PersonEditor(new HTML("Edytuj osobę"), oldName, new AsyncCallback<String> () {

                              @Override
                              public void onFailure(Throwable caught) {
                            	  logger.log(Level.INFO, "Anulowano edycję");
                              }

                              @Override
                              public void onSuccess(String result) {
                            	  if(result != null) {
                            		  logger.log(Level.INFO, "Przygotowuję do aktualizacji obiekt id=" + pesel + ", wartość " + result);
                            		  personService.changeName(pesel, result, new AsyncCallback<Void>() {
                                                      @Override
                                                      public void onFailure(Throwable caught) {
                                                              logger.log(Level.WARNING, "changeName nieudane - " + caught.toString());                         
                                                      }

                                                      @Override
                                                      public void onSuccess(Void foo) {
                                                              logger.log(Level.INFO, "changeName ok");
                                                              personListRefresh();
                                                      }

                                        });
                            	  } else {
                            		  logger.log(Level.INFO, "Przygotowuję do usunięcia obiekt id=" + pesel);
                            		  personService.removePerson(pesel, new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											logger.log(Level.WARNING, "Usunięcie nieudane - " + caught.toString());
										}

										@Override
										public void onSuccess(Void result) {
											logger.log(Level.INFO, "removePerson ok");
											personListRefresh();
										}
                            			  
                            		  });
                            	  }
                              }
                });
                            
			}
		});
		
		RootPanel.get("personList").add(personList);
		personListRefresh();
  }
}
