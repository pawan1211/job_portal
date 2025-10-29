package com.example.job_portal.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jobs") // Explicit table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String description;

    private String requiredSkills; // comma-separated list (e.g., "Java,Spring Boot,SQL")

    private double experienceRequired;

    private String location;

    @Column(nullable = false, updatable = false)
    private Instant postedAt = Instant.now(); // timestamp when job is created

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    
    @JsonIgnore
    private Recruiter postedBy;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  
    @JsonIgnore
    private List<JobApplication> applications;
    
}
