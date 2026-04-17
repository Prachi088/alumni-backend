package com.alumni.backend.controller;

import com.alumni.backend.model.Job;
import com.alumni.backend.repository.JobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/jobs")
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // GET all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // POST — alumni creates a new job listing
    @PostMapping
    public Job postJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    // DELETE — alumni removes their job listing
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
    }

    // POST /{id}/apply — student applies; tracking is done client-side via localStorage
    @PostMapping("/{id}/apply")
    public ResponseEntity<String> applyJob(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        // Application tracking is handled on the frontend in localStorage.
        // This endpoint simply returns 200 OK so the frontend flow completes.
        return ResponseEntity.ok("Applied successfully");
    }
}
