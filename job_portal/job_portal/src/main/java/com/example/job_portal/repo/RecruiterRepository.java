package com.example.job_portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.job_portal.model.Recruiter;

import java.util.Optional;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    Optional<Recruiter> findByEmail(String email);
}
