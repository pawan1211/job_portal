package com.example.job_portal.dto;



public class AuthRequests {

    // DTO for Candidate Registration
    public static class RegisterCandidate {
        public String name;
        public String email;
        public String password;
        public double experience;
        public String skills;
        public String location;
    }

    // DTO for Recruiter Registration
    public static class RegisterRecruiter {
        public String name;
        public String company;
        public String email;
        public String password;
    }

    // DTO for Login Request
    public static class LoginRequest {
        public String email;
        public String password;
    }
}
