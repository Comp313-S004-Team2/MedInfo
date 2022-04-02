package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Doctor type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Doctors", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Doctor implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField FIRST_NAME = field("firstName");
  public static final QueryField LAST_NAME = field("lastName");
  public static final QueryField EMAIL = field("email");
  public static final QueryField DOCTOR_LICENSE_NUMBER = field("doctorLicenseNumber");
  public static final QueryField PASSWORD = field("password");
  public static final QueryField ADDRESS = field("address");
  public static final QueryField PHONE_NUMBER = field("phoneNumber");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String firstName;
  private final @ModelField(targetType="String", isRequired = true) String lastName;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String doctorLicenseNumber;
  private final @ModelField(targetType="String", isRequired = true) String password;
  private final @ModelField(targetType="String", isRequired = true) String address;
  private final @ModelField(targetType="String", isRequired = true) String phoneNumber;
  private @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime") Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFirstName() {
      return firstName;
  }
  
  public String getLastName() {
      return lastName;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getDoctorLicenseNumber() {
      return doctorLicenseNumber;
  }
  
  public String getPassword() {
      return password;
  }
  
  public String getAddress() {
      return address;
  }
  
  public String getPhoneNumber() {
      return phoneNumber;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Doctor(String id, String firstName, String lastName, String email, String doctorLicenseNumber, String password, String address, String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.doctorLicenseNumber = doctorLicenseNumber;
    this.password = password;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Doctor doctor = (Doctor) obj;
      return ObjectsCompat.equals(getId(), doctor.getId()) &&
              ObjectsCompat.equals(getFirstName(), doctor.getFirstName()) &&
              ObjectsCompat.equals(getLastName(), doctor.getLastName()) &&
              ObjectsCompat.equals(getEmail(), doctor.getEmail()) &&
              ObjectsCompat.equals(getDoctorLicenseNumber(), doctor.getDoctorLicenseNumber()) &&
              ObjectsCompat.equals(getPassword(), doctor.getPassword()) &&
              ObjectsCompat.equals(getAddress(), doctor.getAddress()) &&
              ObjectsCompat.equals(getPhoneNumber(), doctor.getPhoneNumber()) &&
              ObjectsCompat.equals(getCreatedAt(), doctor.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), doctor.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstName())
      .append(getLastName())
      .append(getEmail())
      .append(getDoctorLicenseNumber())
      .append(getPassword())
      .append(getAddress())
      .append(getPhoneNumber())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Doctor {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("doctorLicenseNumber=" + String.valueOf(getDoctorLicenseNumber()) + ", ")
      .append("password=" + String.valueOf(getPassword()) + ", ")
      .append("address=" + String.valueOf(getAddress()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FirstNameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Doctor justId(String id) {
    return new Doctor(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstName,
      lastName,
      email,
      doctorLicenseNumber,
      password,
      address,
      phoneNumber);
  }
  public interface FirstNameStep {
    LastNameStep firstName(String firstName);
  }
  

  public interface LastNameStep {
    EmailStep lastName(String lastName);
  }
  

  public interface EmailStep {
    DoctorLicenseNumberStep email(String email);
  }
  

  public interface DoctorLicenseNumberStep {
    PasswordStep doctorLicenseNumber(String doctorLicenseNumber);
  }
  

  public interface PasswordStep {
    AddressStep password(String password);
  }
  

  public interface AddressStep {
    PhoneNumberStep address(String address);
  }
  

  public interface PhoneNumberStep {
    BuildStep phoneNumber(String phoneNumber);
  }
  

  public interface BuildStep {
    Doctor build();
    BuildStep id(String id);
  }
  

  public static class Builder implements FirstNameStep, LastNameStep, EmailStep, DoctorLicenseNumberStep, PasswordStep, AddressStep, PhoneNumberStep, BuildStep {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String doctorLicenseNumber;
    private String password;
    private String address;
    private String phoneNumber;
    @Override
     public Doctor build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Doctor(
          id,
          firstName,
          lastName,
          email,
          doctorLicenseNumber,
          password,
          address,
          phoneNumber);
    }
    
    @Override
     public LastNameStep firstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }
    
    @Override
     public EmailStep lastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
        return this;
    }
    
    @Override
     public DoctorLicenseNumberStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public PasswordStep doctorLicenseNumber(String doctorLicenseNumber) {
        Objects.requireNonNull(doctorLicenseNumber);
        this.doctorLicenseNumber = doctorLicenseNumber;
        return this;
    }
    
    @Override
     public AddressStep password(String password) {
        Objects.requireNonNull(password);
        this.password = password;
        return this;
    }
    
    @Override
     public PhoneNumberStep address(String address) {
        Objects.requireNonNull(address);
        this.address = address;
        return this;
    }
    
    @Override
     public BuildStep phoneNumber(String phoneNumber) {
        Objects.requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String firstName, String lastName, String email, String doctorLicenseNumber, String password, String address, String phoneNumber) {
      super.id(id);
      super.firstName(firstName)
        .lastName(lastName)
        .email(email)
        .doctorLicenseNumber(doctorLicenseNumber)
        .password(password)
        .address(address)
        .phoneNumber(phoneNumber);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder doctorLicenseNumber(String doctorLicenseNumber) {
      return (CopyOfBuilder) super.doctorLicenseNumber(doctorLicenseNumber);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
    
    @Override
     public CopyOfBuilder phoneNumber(String phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }
  }
  
}
