package com.example.job_portal.sevices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.job_portal.model.Candidate;
import com.example.job_portal.model.Job;
import com.example.job_portal.model.JobApplication;
import com.example.job_portal.model.Recruiter;
import com.example.job_portal.repo.CandidateRepository;
import com.example.job_portal.repo.JobApplicationRepository;
import com.example.job_portal.repo.JobRepository;
import com.example.job_portal.repo.RecruiterRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final RecruiterRepository recruiterRepository;
    private final CandidateRepository candidateRepository;
    private final JobApplicationRepository jobApplicationRepository;
    

    @Autowired
    public JobService(JobRepository jobRepository,
                      RecruiterRepository recruiterRepository,
                      CandidateRepository candidateRepository,
                      JobApplicationRepository jobApplicationRepository) {
        this.jobRepository = jobRepository;
        this.recruiterRepository = recruiterRepository;
        this.candidateRepository = candidateRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<Job> searchJobs(String skill, String location) {
        String skillLower = skill.toLowerCase();
        String locationLower = location.toLowerCase();
        List<Job> jobs=jobRepository.findAll();

        List<Job> result = new ArrayList<>();

        for (Job job : jobs) {
            boolean skillMatch = containsKeyword(job.getRequiredSkills(), skillLower);
            boolean locationMatch = containsKeyword(job.getLocation(), locationLower);
        
            if (skillMatch && locationMatch) {
                result.add(job);
            }
        }

        return result;
    }

    private boolean containsKeyword(String fieldValue, String keyword) {
        if (fieldValue == null || keyword == null || keyword.isEmpty()) {
            return false;
        }

        // Convert both to lowercase for case-insensitive comparison
        String[] parts = fieldValue.split(",");
        for (String part : parts) {
            if (part.trim().toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public List<Job> allJobs() {
        return jobRepository.findAll();
    }

    public Job getJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job postJob(String email, Job job) {
        Recruiter recruiter = recruiterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
        job.setPostedBy(recruiter);
        return jobRepository.save(job);
    }

    public List<Job> myJobs(String email) {
        Recruiter recruiter = recruiterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
        return jobRepository.findByPostedBy(recruiter);
    }

    public Map<String, String> applyToJob(Long jobId, String email) {
        Candidate candidate = candidateRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        JobApplication app = new JobApplication();
        app.setJob(job);
        app.setCandidate(candidate);
        jobApplicationRepository.save(app);
        return Map.of("status", "applied");
    }

    public List<Candidate> viewApplicants(Long jobId, String recruiterEmail) {
        Recruiter recruiter = recruiterRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (!job.getPostedBy().getId().equals(recruiter.getId())) {
            throw new RuntimeException("Not authorized for this job");
        }
        var apps = jobApplicationRepository.findByJob(job);
        return apps.stream().map(JobApplication::getCandidate).collect(Collectors.toList());
    }
}
