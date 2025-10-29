package com.example.job_portal.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.job_portal.model.Job;
import com.example.job_portal.model.JobApplication;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJob(Job job);
}
