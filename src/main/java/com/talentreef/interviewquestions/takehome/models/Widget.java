package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@Table
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(toBuilder=true)
public class Widget {
  @Id
  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  @NotBlank(message = "Name is mandatory")
  private String name;

  @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters")
  private String description;

  @DecimalMin(value = "1.00", message = "Price must be greater or equal to 1")
  @DecimalMax(value = "20000.00", message = "Price must be less or equal to 20000")
  @Digits(integer = 5, fraction = 2, message = "Price must have 2 decimal places")
  private Number price;

}
