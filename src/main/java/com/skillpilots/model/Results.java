package com.skillpilots.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
@Data
@Entity
public class Results {
	
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long resultId;
 private int marks;
 
 private int correctAns;
 
 private int wrongAns;
 private int noAns;
 private int outof;
 private int totalMarks;
 
 private String resultStatus;
 private Long studentId;
 
 
 private Long testId;
 
 @Temporal(TemporalType.TIMESTAMP)
 private Date testTakenDate;
}
