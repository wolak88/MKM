package zal.client;

import zal.shared.Person;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("person")
public interface PersonService extends RemoteService {
  public Long addPersonByString(String toParse);
  public Long addPerson(Long pesel, String firstName, String lastName);
  public void removePerson(Long pesel);
  public Long[] getAllPesels();
  public Person getPerson(Long pesel);
  public Person[] getAllPersons();
  public String[] getAllPersonsAsStrings();
  public void changeName(Long pesel, String newName);
}