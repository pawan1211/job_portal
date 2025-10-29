package com.example.job_portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.job_portal.model.Candidate;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByEmail(String email);
}
