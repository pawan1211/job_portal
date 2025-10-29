package com.example.job_portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.job_portal.model.Job;
import com.example.job_portal.model.Recruiter;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {


    List<Job> findByPostedBy(Recruiter recruiter);
}
