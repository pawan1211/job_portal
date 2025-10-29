package com.example.job_portal.sevices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.job_portal.dto.AuthRequests.LoginRequest;
import com.example.job_portal.dto.AuthRequests.RegisterCandidate;
import com.example.job_portal.dto.AuthRequests.RegisterRecruiter;
import com.example.job_portal.model.Candidate;
import com.example.job_portal.model.Recruiter;
import com.example.job_portal.model.Role;
import com.example.job_portal.repo.CandidateRepository;
import com.example.job_portal.repo.RecruiterRepository;
import com.example.job_portal.security.JwtUtil;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(CandidateRepository candidateRepository,
                       RecruiterRepository recruiterRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.candidateRepository = candidateRepository;
        this.recruiterRepository = recruiterRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> registerCandidate(RegisterCandidate req) {
        if (candidateRepository.findByEmail(req.email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Candidate c = new Candidate();
        c.setName(req.name);
        c.setEmail(req.email);
        c.setPassword(passwordEncoder.encode(req.password));
        c.setExperience(req.experience);
        c.setSkills(req.skills);
        c.setLocation(req.location);
        candidateRepository.save(c);

        String token = jwtUtil.generateToken(c.getEmail(), Role.ROLE_CANDIDATE.name());
        return Map.of("token", token);
    }

    public Map<String, String> registerRecruiter(RegisterRecruiter req) {
        if (recruiterRepository.findByEmail(req.email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Recruiter r = new Recruiter();
        r.setName(req.name);
        r.setCompany(req.company);
        r.setEmail(req.email);
        r.setPassword(passwordEncoder.encode(req.password));
        recruiterRepository.save(r);

        String token = jwtUtil.generateToken(r.getEmail(), Role.ROLE_RECRUITER.name());
        return Map.of("token", token);
    }

    public Map<String, String> login(LoginRequest req) {
        Optional<Candidate> candOpt = candidateRepository.findByEmail(req.email);
        if (candOpt.isPresent() && passwordEncoder.matches(req.password, candOpt.get().getPassword())) {
            String token = jwtUtil.generateToken(req.email, Role.ROLE_CANDIDATE.name());
            return Map.of("token", token);
        }
        Optional<Recruiter> recOpt = recruiterRepository.findByEmail(req.email);
        if (recOpt.isPresent() && passwordEncoder.matches(req.password, recOpt.get().getPassword())) {
            String token = jwtUtil.generateToken(req.email, Role.ROLE_RECRUITER.name());
            return Map.of("token", token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
