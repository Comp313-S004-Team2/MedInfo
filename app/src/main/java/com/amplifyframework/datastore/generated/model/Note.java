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

/** This is an auto generated class representing the Note type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Notes", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Note implements Model {
  public static final QueryField ID = field("Note", "id");
  public static final QueryField RECORD_ID = field("Note", "recordId");
  public static final QueryField NOTE = field("Note", "note");
  public static final QueryField IS_DOCTOR_ONLY = field("Note", "isDoctorOnly");
  public static final QueryField WRITER_ROLE = field("Note", "writerRole");
  public static final QueryField WRITER_NAME = field("Note", "writerName");
  public static final QueryField WRITER_ID = field("Note", "writerId");
  public static final QueryField CREATED_ON = field("Note", "createdOn");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String recordId;
  private final @ModelField(targetType="String", isRequired = true) String note;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean isDoctorOnly;
  private final @ModelField(targetType="String", isRequired = true) String writerRole;
  private final @ModelField(targetType="String", isRequired = true) String writerName;
  private final @ModelField(targetType="String", isRequired = true) String writerId;
  private final @ModelField(targetType="String", isRequired = true) String createdOn;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getRecordId() {
      return recordId;
  }
  
  public String getNote() {
      return note;
  }
  
  public Boolean getIsDoctorOnly() {
      return isDoctorOnly;
  }
  
  public String getWriterRole() {
      return writerRole;
  }
  
  public String getWriterName() {
      return writerName;
  }
  
  public String getWriterId() {
      return writerId;
  }
  
  public String getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Note(String id, String recordId, String note, Boolean isDoctorOnly, String writerRole, String writerName, String writerId, String createdOn) {
    this.id = id;
    this.recordId = recordId;
    this.note = note;
    this.isDoctorOnly = isDoctorOnly;
    this.writerRole = writerRole;
    this.writerName = writerName;
    this.writerId = writerId;
    this.createdOn = createdOn;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Note note = (Note) obj;
      return ObjectsCompat.equals(getId(), note.getId()) &&
              ObjectsCompat.equals(getRecordId(), note.getRecordId()) &&
              ObjectsCompat.equals(getNote(), note.getNote()) &&
              ObjectsCompat.equals(getIsDoctorOnly(), note.getIsDoctorOnly()) &&
              ObjectsCompat.equals(getWriterRole(), note.getWriterRole()) &&
              ObjectsCompat.equals(getWriterName(), note.getWriterName()) &&
              ObjectsCompat.equals(getWriterId(), note.getWriterId()) &&
              ObjectsCompat.equals(getCreatedOn(), note.getCreatedOn()) &&
              ObjectsCompat.equals(getCreatedAt(), note.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), note.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRecordId())
      .append(getNote())
      .append(getIsDoctorOnly())
      .append(getWriterRole())
      .append(getWriterName())
      .append(getWriterId())
      .append(getCreatedOn())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Note {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("recordId=" + String.valueOf(getRecordId()) + ", ")
      .append("note=" + String.valueOf(getNote()) + ", ")
      .append("isDoctorOnly=" + String.valueOf(getIsDoctorOnly()) + ", ")
      .append("writerRole=" + String.valueOf(getWriterRole()) + ", ")
      .append("writerName=" + String.valueOf(getWriterName()) + ", ")
      .append("writerId=" + String.valueOf(getWriterId()) + ", ")
      .append("createdOn=" + String.valueOf(getCreatedOn()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static RecordIdStep builder() {
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
  public static Note justId(String id) {
    return new Note(
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
      recordId,
      note,
      isDoctorOnly,
      writerRole,
      writerName,
      writerId,
      createdOn);
  }
  public interface RecordIdStep {
    NoteStep recordId(String recordId);
  }
  

  public interface NoteStep {
    IsDoctorOnlyStep note(String note);
  }
  

  public interface IsDoctorOnlyStep {
    WriterRoleStep isDoctorOnly(Boolean isDoctorOnly);
  }
  

  public interface WriterRoleStep {
    WriterNameStep writerRole(String writerRole);
  }
  

  public interface WriterNameStep {
    WriterIdStep writerName(String writerName);
  }
  

  public interface WriterIdStep {
    CreatedOnStep writerId(String writerId);
  }
  

  public interface CreatedOnStep {
    BuildStep createdOn(String createdOn);
  }
  

  public interface BuildStep {
    Note build();
    BuildStep id(String id);
  }
  

  public static class Builder implements RecordIdStep, NoteStep, IsDoctorOnlyStep, WriterRoleStep, WriterNameStep, WriterIdStep, CreatedOnStep, BuildStep {
    private String id;
    private String recordId;
    private String note;
    private Boolean isDoctorOnly;
    private String writerRole;
    private String writerName;
    private String writerId;
    private String createdOn;
    @Override
     public Note build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Note(
          id,
          recordId,
          note,
          isDoctorOnly,
          writerRole,
          writerName,
          writerId,
          createdOn);
    }
    
    @Override
     public NoteStep recordId(String recordId) {
        Objects.requireNonNull(recordId);
        this.recordId = recordId;
        return this;
    }
    
    @Override
     public IsDoctorOnlyStep note(String note) {
        Objects.requireNonNull(note);
        this.note = note;
        return this;
    }
    
    @Override
     public WriterRoleStep isDoctorOnly(Boolean isDoctorOnly) {
        Objects.requireNonNull(isDoctorOnly);
        this.isDoctorOnly = isDoctorOnly;
        return this;
    }
    
    @Override
     public WriterNameStep writerRole(String writerRole) {
        Objects.requireNonNull(writerRole);
        this.writerRole = writerRole;
        return this;
    }
    
    @Override
     public WriterIdStep writerName(String writerName) {
        Objects.requireNonNull(writerName);
        this.writerName = writerName;
        return this;
    }
    
    @Override
     public CreatedOnStep writerId(String writerId) {
        Objects.requireNonNull(writerId);
        this.writerId = writerId;
        return this;
    }
    
    @Override
     public BuildStep createdOn(String createdOn) {
        Objects.requireNonNull(createdOn);
        this.createdOn = createdOn;
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
    private CopyOfBuilder(String id, String recordId, String note, Boolean isDoctorOnly, String writerRole, String writerName, String writerId, String createdOn) {
      super.id(id);
      super.recordId(recordId)
        .note(note)
        .isDoctorOnly(isDoctorOnly)
        .writerRole(writerRole)
        .writerName(writerName)
        .writerId(writerId)
        .createdOn(createdOn);
    }
    
    @Override
     public CopyOfBuilder recordId(String recordId) {
      return (CopyOfBuilder) super.recordId(recordId);
    }
    
    @Override
     public CopyOfBuilder note(String note) {
      return (CopyOfBuilder) super.note(note);
    }
    
    @Override
     public CopyOfBuilder isDoctorOnly(Boolean isDoctorOnly) {
      return (CopyOfBuilder) super.isDoctorOnly(isDoctorOnly);
    }
    
    @Override
     public CopyOfBuilder writerRole(String writerRole) {
      return (CopyOfBuilder) super.writerRole(writerRole);
    }
    
    @Override
     public CopyOfBuilder writerName(String writerName) {
      return (CopyOfBuilder) super.writerName(writerName);
    }
    
    @Override
     public CopyOfBuilder writerId(String writerId) {
      return (CopyOfBuilder) super.writerId(writerId);
    }
    
    @Override
     public CopyOfBuilder createdOn(String createdOn) {
      return (CopyOfBuilder) super.createdOn(createdOn);
    }
  }
  
}
