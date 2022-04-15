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

/** This is an auto generated class representing the Appointment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Appointments", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Appointment implements Model {
  public static final QueryField ID = field("Appointment", "id");
  public static final QueryField PATIENT_ID = field("Appointment", "patientId");
  public static final QueryField DOCTOR_ID = field("Appointment", "doctorId");
  public static final QueryField YEAR = field("Appointment", "year");
  public static final QueryField MONTH = field("Appointment", "month");
  public static final QueryField DAY = field("Appointment", "day");
  public static final QueryField TIME_SLOT = field("Appointment", "timeSlot");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String patientId;
  private final @ModelField(targetType="String", isRequired = true) String doctorId;
  private final @ModelField(targetType="Int", isRequired = true) Integer year;
  private final @ModelField(targetType="Int", isRequired = true) Integer month;
  private final @ModelField(targetType="Int", isRequired = true) Integer day;
  private final @ModelField(targetType="Int", isRequired = true) Integer timeSlot;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getPatientId() {
      return patientId;
  }
  
  public String getDoctorId() {
      return doctorId;
  }
  
  public Integer getYear() {
      return year;
  }
  
  public Integer getMonth() {
      return month;
  }
  
  public Integer getDay() {
      return day;
  }
  
  public Integer getTimeSlot() {
      return timeSlot;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Appointment(String id, String patientId, String doctorId, Integer year, Integer month, Integer day, Integer timeSlot) {
    this.id = id;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.year = year;
    this.month = month;
    this.day = day;
    this.timeSlot = timeSlot;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Appointment appointment = (Appointment) obj;
      return ObjectsCompat.equals(getId(), appointment.getId()) &&
              ObjectsCompat.equals(getPatientId(), appointment.getPatientId()) &&
              ObjectsCompat.equals(getDoctorId(), appointment.getDoctorId()) &&
              ObjectsCompat.equals(getYear(), appointment.getYear()) &&
              ObjectsCompat.equals(getMonth(), appointment.getMonth()) &&
              ObjectsCompat.equals(getDay(), appointment.getDay()) &&
              ObjectsCompat.equals(getTimeSlot(), appointment.getTimeSlot()) &&
              ObjectsCompat.equals(getCreatedAt(), appointment.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), appointment.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPatientId())
      .append(getDoctorId())
      .append(getYear())
      .append(getMonth())
      .append(getDay())
      .append(getTimeSlot())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Appointment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("patientId=" + String.valueOf(getPatientId()) + ", ")
      .append("doctorId=" + String.valueOf(getDoctorId()) + ", ")
      .append("year=" + String.valueOf(getYear()) + ", ")
      .append("month=" + String.valueOf(getMonth()) + ", ")
      .append("day=" + String.valueOf(getDay()) + ", ")
      .append("timeSlot=" + String.valueOf(getTimeSlot()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static PatientIdStep builder() {
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
  public static Appointment justId(String id) {
    return new Appointment(
      id,
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
      patientId,
      doctorId,
      year,
      month,
      day,
      timeSlot);
  }
  public interface PatientIdStep {
    DoctorIdStep patientId(String patientId);
  }
  

  public interface DoctorIdStep {
    YearStep doctorId(String doctorId);
  }
  

  public interface YearStep {
    MonthStep year(Integer year);
  }
  

  public interface MonthStep {
    DayStep month(Integer month);
  }
  

  public interface DayStep {
    TimeSlotStep day(Integer day);
  }
  

  public interface TimeSlotStep {
    BuildStep timeSlot(Integer timeSlot);
  }
  

  public interface BuildStep {
    Appointment build();
    BuildStep id(String id);
  }
  

  public static class Builder implements PatientIdStep, DoctorIdStep, YearStep, MonthStep, DayStep, TimeSlotStep, BuildStep {
    private String id;
    private String patientId;
    private String doctorId;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer timeSlot;
    @Override
     public Appointment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Appointment(
          id,
          patientId,
          doctorId,
          year,
          month,
          day,
          timeSlot);
    }
    
    @Override
     public DoctorIdStep patientId(String patientId) {
        Objects.requireNonNull(patientId);
        this.patientId = patientId;
        return this;
    }
    
    @Override
     public YearStep doctorId(String doctorId) {
        Objects.requireNonNull(doctorId);
        this.doctorId = doctorId;
        return this;
    }
    
    @Override
     public MonthStep year(Integer year) {
        Objects.requireNonNull(year);
        this.year = year;
        return this;
    }
    
    @Override
     public DayStep month(Integer month) {
        Objects.requireNonNull(month);
        this.month = month;
        return this;
    }
    
    @Override
     public TimeSlotStep day(Integer day) {
        Objects.requireNonNull(day);
        this.day = day;
        return this;
    }
    
    @Override
     public BuildStep timeSlot(Integer timeSlot) {
        Objects.requireNonNull(timeSlot);
        this.timeSlot = timeSlot;
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
    private CopyOfBuilder(String id, String patientId, String doctorId, Integer year, Integer month, Integer day, Integer timeSlot) {
      super.id(id);
      super.patientId(patientId)
        .doctorId(doctorId)
        .year(year)
        .month(month)
        .day(day)
        .timeSlot(timeSlot);
    }
    
    @Override
     public CopyOfBuilder patientId(String patientId) {
      return (CopyOfBuilder) super.patientId(patientId);
    }
    
    @Override
     public CopyOfBuilder doctorId(String doctorId) {
      return (CopyOfBuilder) super.doctorId(doctorId);
    }
    
    @Override
     public CopyOfBuilder year(Integer year) {
      return (CopyOfBuilder) super.year(year);
    }
    
    @Override
     public CopyOfBuilder month(Integer month) {
      return (CopyOfBuilder) super.month(month);
    }
    
    @Override
     public CopyOfBuilder day(Integer day) {
      return (CopyOfBuilder) super.day(day);
    }
    
    @Override
     public CopyOfBuilder timeSlot(Integer timeSlot) {
      return (CopyOfBuilder) super.timeSlot(timeSlot);
    }
  }
  
}
