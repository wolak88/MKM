package zal.client;

import zal.shared.Person;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PersonServiceAsync {

	void addPersonByString(String toParse, AsyncCallback<Long> callback);
	void addPerson(Long pesel, String firstName, String lastName, AsyncCallback<Long> callback);
	void getAllPesels(AsyncCallback<Long[]> callback);
	void removePerson(Long pesel, AsyncCallback<Void> callback);
	void getPerson(Long pesel, AsyncCallback<Person> callback);
	void getAllPersonsAsStrings(AsyncCallback<String[]> callback);
	void getAllPersons(AsyncCallback<Person[]> callback);
	void changeName(Long pesel, String newName, AsyncCallback<Void> callback);
	
}
