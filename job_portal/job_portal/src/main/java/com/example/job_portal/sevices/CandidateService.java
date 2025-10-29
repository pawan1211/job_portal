package com.example.job_portal.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.job_portal.model.Candidate;
import com.example.job_portal.repo.CandidateRepository;

import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Optional<Candidate> getProfile(String email) {
        return candidateRepository.findByEmail(email);
    }

    public Candidate updateProfile(String email, Candidate updated) {
        Candidate c = candidateRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        c.setName(updated.getName());
        c.setExperience(updated.getExperience());
        c.setSkills(updated.getSkills());
        c.setLocation(updated.getLocation());
        return candidateRepository.save(c);
    }
}
