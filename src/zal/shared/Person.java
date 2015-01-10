package zal.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Person implements IsSerializable {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long pesel;
  @Persistent
  private String firstName;
  @Persistent
  private String lastName;

  public Person() {}

  public Person(Long pesel, String firstName, String lastName) {
    this();
    this.pesel = pesel;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getPesel() {
    return this.pesel;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String toString() {
	  return pesel + ":" + firstName + " " + lastName;
  }
}