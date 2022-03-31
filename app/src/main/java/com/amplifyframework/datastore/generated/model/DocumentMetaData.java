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

/** This is an auto generated class representing the DocumentMetaData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "DocumentMetaData", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class DocumentMetaData implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField TITLE = field("title");
  public static final QueryField SHORT_DESCRIPTION = field("shortDescription");
  public static final QueryField UPLOAD_DATE = field("uploadDate");
  public static final QueryField UPLOADED_BY = field("uploadedBy");
  public static final QueryField HEALTH_CARD = field("healthCard");
  public static final QueryField FILE_EXTENTION = field("fileExtention");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String shortDescription;
  private final @ModelField(targetType="String", isRequired = true) String uploadDate;
  private final @ModelField(targetType="String", isRequired = true) String uploadedBy;
  private final @ModelField(targetType="String", isRequired = true) String healthCard;
  private final @ModelField(targetType="String", isRequired = true) String fileExtention;
  private @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime") Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getShortDescription() {
      return shortDescription;
  }
  
  public String getUploadDate() {
      return uploadDate;
  }
  
  public String getUploadedBy() {
      return uploadedBy;
  }
  
  public String getHealthCard() {
      return healthCard;
  }
  
  public String getFileExtention() {
      return fileExtention;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private DocumentMetaData(String id, String title, String shortDescription, String uploadDate, String uploadedBy, String healthCard, String fileExtention) {
    this.id = id;
    this.title = title;
    this.shortDescription = shortDescription;
    this.uploadDate = uploadDate;
    this.uploadedBy = uploadedBy;
    this.healthCard = healthCard;
    this.fileExtention = fileExtention;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      DocumentMetaData documentMetaData = (DocumentMetaData) obj;
      return ObjectsCompat.equals(getId(), documentMetaData.getId()) &&
              ObjectsCompat.equals(getTitle(), documentMetaData.getTitle()) &&
              ObjectsCompat.equals(getShortDescription(), documentMetaData.getShortDescription()) &&
              ObjectsCompat.equals(getUploadDate(), documentMetaData.getUploadDate()) &&
              ObjectsCompat.equals(getUploadedBy(), documentMetaData.getUploadedBy()) &&
              ObjectsCompat.equals(getHealthCard(), documentMetaData.getHealthCard()) &&
              ObjectsCompat.equals(getFileExtention(), documentMetaData.getFileExtention()) &&
              ObjectsCompat.equals(getCreatedAt(), documentMetaData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), documentMetaData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getShortDescription())
      .append(getUploadDate())
      .append(getUploadedBy())
      .append(getHealthCard())
      .append(getFileExtention())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("DocumentMetaData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("shortDescription=" + String.valueOf(getShortDescription()) + ", ")
      .append("uploadDate=" + String.valueOf(getUploadDate()) + ", ")
      .append("uploadedBy=" + String.valueOf(getUploadedBy()) + ", ")
      .append("healthCard=" + String.valueOf(getHealthCard()) + ", ")
      .append("fileExtention=" + String.valueOf(getFileExtention()) + ", ")
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
  public static DocumentMetaData justId(String id) {
    return new DocumentMetaData(
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
      title,
      shortDescription,
      uploadDate,
      uploadedBy,
      healthCard,
      fileExtention);
  }
  public interface TitleStep {
    ShortDescriptionStep title(String title);
  }
  

  public interface ShortDescriptionStep {
    UploadDateStep shortDescription(String shortDescription);
  }
  

  public interface UploadDateStep {
    UploadedByStep uploadDate(String uploadDate);
  }
  

  public interface UploadedByStep {
    HealthCardStep uploadedBy(String uploadedBy);
  }
  

  public interface HealthCardStep {
    FileExtentionStep healthCard(String healthCard);
  }
  

  public interface FileExtentionStep {
    BuildStep fileExtention(String fileExtention);
  }
  

  public interface BuildStep {
    DocumentMetaData build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TitleStep, ShortDescriptionStep, UploadDateStep, UploadedByStep, HealthCardStep, FileExtentionStep, BuildStep {
    private String id;
    private String title;
    private String shortDescription;
    private String uploadDate;
    private String uploadedBy;
    private String healthCard;
    private String fileExtention;
    @Override
     public DocumentMetaData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new DocumentMetaData(
          id,
          title,
          shortDescription,
          uploadDate,
          uploadedBy,
          healthCard,
          fileExtention);
    }
    
    @Override
     public ShortDescriptionStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public UploadDateStep shortDescription(String shortDescription) {
        Objects.requireNonNull(shortDescription);
        this.shortDescription = shortDescription;
        return this;
    }
    
    @Override
     public UploadedByStep uploadDate(String uploadDate) {
        Objects.requireNonNull(uploadDate);
        this.uploadDate = uploadDate;
        return this;
    }
    
    @Override
     public HealthCardStep uploadedBy(String uploadedBy) {
        Objects.requireNonNull(uploadedBy);
        this.uploadedBy = uploadedBy;
        return this;
    }
    
    @Override
     public FileExtentionStep healthCard(String healthCard) {
        Objects.requireNonNull(healthCard);
        this.healthCard = healthCard;
        return this;
    }
    
    @Override
     public BuildStep fileExtention(String fileExtention) {
        Objects.requireNonNull(fileExtention);
        this.fileExtention = fileExtention;
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
    private CopyOfBuilder(String id, String title, String shortDescription, String uploadDate, String uploadedBy, String healthCard, String fileExtention) {
      super.id(id);
      super.title(title)
        .shortDescription(shortDescription)
        .uploadDate(uploadDate)
        .uploadedBy(uploadedBy)
        .healthCard(healthCard)
        .fileExtention(fileExtention);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder shortDescription(String shortDescription) {
      return (CopyOfBuilder) super.shortDescription(shortDescription);
    }
    
    @Override
     public CopyOfBuilder uploadDate(String uploadDate) {
      return (CopyOfBuilder) super.uploadDate(uploadDate);
    }
    
    @Override
     public CopyOfBuilder uploadedBy(String uploadedBy) {
      return (CopyOfBuilder) super.uploadedBy(uploadedBy);
    }
    
    @Override
     public CopyOfBuilder healthCard(String healthCard) {
      return (CopyOfBuilder) super.healthCard(healthCard);
    }
    
    @Override
     public CopyOfBuilder fileExtention(String fileExtention) {
      return (CopyOfBuilder) super.fileExtention(fileExtention);
    }
  }
  
}
