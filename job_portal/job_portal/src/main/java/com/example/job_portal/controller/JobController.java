package com.example.job_portal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.job_portal.model.Job;
import com.example.job_portal.sevices.JobService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestParam String skill, @RequestParam String location) {
        return ResponseEntity.ok(jobService.searchJobs(skill, location));
    }

    @GetMapping
    public ResponseEntity<List<Job>> allJobs() {
        return ResponseEntity.ok(jobService.allJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJob(id));
    }

    @PostMapping
    public ResponseEntity<?> postJob(Authentication auth, @RequestBody Job job) {
        String email = (String) auth.getPrincipal();
        return ResponseEntity.ok(jobService.postJob(email, job));
    }

    @GetMapping("/mine")
    public ResponseEntity<?> myJobs(Authentication auth) {
        String email = (String) auth.getPrincipal();
        return ResponseEntity.ok(jobService.myJobs(email));
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<?> applyToJob(@PathVariable Long id, Authentication auth) {
        String email = (String) auth.getPrincipal();
        return ResponseEntity.ok(jobService.applyToJob(id, email));
    }

    @GetMapping("/applications/{jobId}")
    public ResponseEntity<?> viewApplicants(@PathVariable Long jobId, Authentication auth) {
        String email = (String) auth.getPrincipal();
        return ResponseEntity.ok(jobService.viewApplicants(jobId, email));
    }
}
