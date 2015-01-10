package zal.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import zal.client.PersonService;
import zal.shared.Person;

import java.util.List;
import java.util.StringTokenizer;

public class PersonServiceImpl extends RemoteServiceServlet implements PersonService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2628560866113155982L;
	
	private static final PersistenceManagerFactory PMF =
		      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	 private PersistenceManager getPersistenceManager() {
		 return PMF.getPersistenceManager();
	 }
	
	@Override
	public Long addPersonByString(String toParse) {
		StringTokenizer st = new StringTokenizer(toParse);
		try {
			Long pesel = Long.parseLong(st.nextToken());
			return addPerson(pesel, st.nextToken(), st.nextToken());
		} catch(Exception e) {
			return null;
		}
	}

	@Override
    public Long addPerson(Long pesel, String firstName, String lastName) {
        PersistenceManager pm = getPersistenceManager();
        Long result = null;
        try {
            pm.getObjectById(Person.class, pesel);
            pm.close();
            return null;
        } catch(Exception e) {
            Person p = new Person(pesel, firstName, lastName);
            pm.makePersistent(p);
            result = pesel;        	
        } finally {
            pm.close();
        }
        return result;
    }

	@Override
	public void removePerson(Long pesel) {
	    PersistenceManager pm = getPersistenceManager();
        try {
        	Person p = (Person) pm.getObjectById(Person.class, pesel);
        	pm.deletePersistent(p);
        } finally {
          pm.close();
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long[] getAllPesels() {
		   PersistenceManager pm = getPersistenceManager();
		   Long[] pesels = null;
		   try {
		      Query q = pm.newQuery(Person.class);
		      List<Person> persons = (List<Person>) q.execute();
		      pesels = new Long[persons.size()];
		      int i = 0;
		      for(Person person: persons) {
		    	  pesels[i] = person.getPesel();
		    	  i++;
		      }
		   } finally {
		      pm.close();
		   }
		   return pesels;
	}

	@Override
	public void changeName(Long pesel, String newName) {
        PersistenceManager pm = getPersistenceManager();
        Person p = (Person) pm.getObjectById(Person.class, pesel);
        StringTokenizer st = new StringTokenizer(newName);
        p.setFirstName(st.nextToken());
        p.setLastName(st.nextToken());
        pm.close();
	}
	
	@Override
	public Person getPerson(Long pesel) {
		PersistenceManager pm = getPersistenceManager();
		Person p = (Person) pm.getObjectById(Person.class, pesel);
		Person detached = pm.detachCopy(p);
		pm.close();
		return detached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Person[] getAllPersons() {
		   PersistenceManager pm = getPersistenceManager();
		    List<Person> persons = null;
		    try {
		      Query q = pm.newQuery(Person.class);
		      q.setOrdering("lastName, firstName");
		      persons = (List<Person>) q.execute();
		      pm.detachCopyAll(persons);
		    } finally {
		      pm.close();
		    }
		    return persons.toArray(new Person[persons.size()]);
    }
	
    @SuppressWarnings("unchecked")
	@Override
	public String[] getAllPersonsAsStrings() {
		   PersistenceManager pm = getPersistenceManager();
		    String[] names = null;
		    try {
		      Query q = pm.newQuery(Person.class);
		      q.setOrdering("lastName, firstName");
		      List<Person> persons = (List<Person>) q.execute();
		      names = new String[persons.size()];
		      int i = 0;
		      for (Person person : persons) {
		    	  names[i] = person.toString();
		    	  i++;
		      }
		    } finally {
		      pm.close();
		    }
		    return names;
	}
}