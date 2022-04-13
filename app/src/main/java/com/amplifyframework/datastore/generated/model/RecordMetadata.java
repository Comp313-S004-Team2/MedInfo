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

/** This is an auto generated class representing the RecordMetadata type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "RecordMetadata", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class RecordMetadata implements Model {
  public static final QueryField ID = field("RecordMetadata", "id");
  public static final QueryField TITLE = field("RecordMetadata", "title");
  public static final QueryField DESCRIPTION = field("RecordMetadata", "description");
  public static final QueryField UPLOADER = field("RecordMetadata", "uploader");
  public static final QueryField UPLOADER_ROLE = field("RecordMetadata", "uploaderRole");
  public static final QueryField UPLOADER_ID = field("RecordMetadata", "uploaderId");
  public static final QueryField FILE_EXTENSION = field("RecordMetadata", "fileExtension");
  public static final QueryField PATIENT_ID = field("RecordMetadata", "patientId");
  public static final QueryField CREATED_ON = field("RecordMetadata", "createdOn");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="String", isRequired = true) String uploader;
  private final @ModelField(targetType="String", isRequired = true) String uploaderRole;
  private final @ModelField(targetType="String", isRequired = true) String uploaderId;
  private final @ModelField(targetType="String", isRequired = true) String fileExtension;
  private final @ModelField(targetType="String", isRequired = true) String patientId;
  private final @ModelField(targetType="String", isRequired = true) String createdOn;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getUploader() {
      return uploader;
  }
  
  public String getUploaderRole() {
      return uploaderRole;
  }
  
  public String getUploaderId() {
      return uploaderId;
  }
  
  public String getFileExtension() {
      return fileExtension;
  }
  
  public String getPatientId() {
      return patientId;
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
  
  private RecordMetadata(String id, String title, String description, String uploader, String uploaderRole, String uploaderId, String fileExtension, String patientId, String createdOn) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.uploader = uploader;
    this.uploaderRole = uploaderRole;
    this.uploaderId = uploaderId;
    this.fileExtension = fileExtension;
    this.patientId = patientId;
    this.createdOn = createdOn;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      RecordMetadata recordMetadata = (RecordMetadata) obj;
      return ObjectsCompat.equals(getId(), recordMetadata.getId()) &&
              ObjectsCompat.equals(getTitle(), recordMetadata.getTitle()) &&
              ObjectsCompat.equals(getDescription(), recordMetadata.getDescription()) &&
              ObjectsCompat.equals(getUploader(), recordMetadata.getUploader()) &&
              ObjectsCompat.equals(getUploaderRole(), recordMetadata.getUploaderRole()) &&
              ObjectsCompat.equals(getUploaderId(), recordMetadata.getUploaderId()) &&
              ObjectsCompat.equals(getFileExtension(), recordMetadata.getFileExtension()) &&
              ObjectsCompat.equals(getPatientId(), recordMetadata.getPatientId()) &&
              ObjectsCompat.equals(getCreatedOn(), recordMetadata.getCreatedOn()) &&
              ObjectsCompat.equals(getCreatedAt(), recordMetadata.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), recordMetadata.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getDescription())
      .append(getUploader())
      .append(getUploaderRole())
      .append(getUploaderId())
      .append(getFileExtension())
      .append(getPatientId())
      .append(getCreatedOn())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("RecordMetadata {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("uploader=" + String.valueOf(getUploader()) + ", ")
      .append("uploaderRole=" + String.valueOf(getUploaderRole()) + ", ")
      .append("uploaderId=" + String.valueOf(getUploaderId()) + ", ")
      .append("fileExtension=" + String.valueOf(getFileExtension()) + ", ")
      .append("patientId=" + String.valueOf(getPatientId()) + ", ")
      .append("createdOn=" + String.valueOf(getCreatedOn()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
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
  public static RecordMetadata justId(String id) {
    return new RecordMetadata(
      id,
      null,
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
      title,
      description,
      uploader,
      uploaderRole,
      uploaderId,
      fileExtension,
      patientId,
      createdOn);
  }
  public interface TitleStep {
    DescriptionStep title(String title);
  }
  

  public interface DescriptionStep {
    UploaderStep description(String description);
  }
  

  public interface UploaderStep {
    UploaderRoleStep uploader(String uploader);
  }
  

  public interface UploaderRoleStep {
    UploaderIdStep uploaderRole(String uploaderRole);
  }
  

  public interface UploaderIdStep {
    FileExtensionStep uploaderId(String uploaderId);
  }
  

  public interface FileExtensionStep {
    PatientIdStep fileExtension(String fileExtension);
  }
  

  public interface PatientIdStep {
    CreatedOnStep patientId(String patientId);
  }
  

  public interface CreatedOnStep {
    BuildStep createdOn(String createdOn);
  }
  

  public interface BuildStep {
    RecordMetadata build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TitleStep, DescriptionStep, UploaderStep, UploaderRoleStep, UploaderIdStep, FileExtensionStep, PatientIdStep, CreatedOnStep, BuildStep {
    private String id;
    private String title;
    private String description;
    private String uploader;
    private String uploaderRole;
    private String uploaderId;
    private String fileExtension;
    private String patientId;
    private String createdOn;
    @Override
     public RecordMetadata build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new RecordMetadata(
          id,
          title,
          description,
          uploader,
          uploaderRole,
          uploaderId,
          fileExtension,
          patientId,
          createdOn);
    }
    
    @Override
     public DescriptionStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public UploaderStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public UploaderRoleStep uploader(String uploader) {
        Objects.requireNonNull(uploader);
        this.uploader = uploader;
        return this;
    }
    
    @Override
     public UploaderIdStep uploaderRole(String uploaderRole) {
        Objects.requireNonNull(uploaderRole);
        this.uploaderRole = uploaderRole;
        return this;
    }
    
    @Override
     public FileExtensionStep uploaderId(String uploaderId) {
        Objects.requireNonNull(uploaderId);
        this.uploaderId = uploaderId;
        return this;
    }
    
    @Override
     public PatientIdStep fileExtension(String fileExtension) {
        Objects.requireNonNull(fileExtension);
        this.fileExtension = fileExtension;
        return this;
    }
    
    @Override
     public CreatedOnStep patientId(String patientId) {
        Objects.requireNonNull(patientId);
        this.patientId = patientId;
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
    private CopyOfBuilder(String id, String title, String description, String uploader, String uploaderRole, String uploaderId, String fileExtension, String patientId, String createdOn) {
      super.id(id);
      super.title(title)
        .description(description)
        .uploader(uploader)
        .uploaderRole(uploaderRole)
        .uploaderId(uploaderId)
        .fileExtension(fileExtension)
        .patientId(patientId)
        .createdOn(createdOn);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder uploader(String uploader) {
      return (CopyOfBuilder) super.uploader(uploader);
    }
    
    @Override
     public CopyOfBuilder uploaderRole(String uploaderRole) {
      return (CopyOfBuilder) super.uploaderRole(uploaderRole);
    }
    
    @Override
     public CopyOfBuilder uploaderId(String uploaderId) {
      return (CopyOfBuilder) super.uploaderId(uploaderId);
    }
    
    @Override
     public CopyOfBuilder fileExtension(String fileExtension) {
      return (CopyOfBuilder) super.fileExtension(fileExtension);
    }
    
    @Override
     public CopyOfBuilder patientId(String patientId) {
      return (CopyOfBuilder) super.patientId(patientId);
    }
    
    @Override
     public CopyOfBuilder createdOn(String createdOn) {
      return (CopyOfBuilder) super.createdOn(createdOn);
    }
  }
  
}
