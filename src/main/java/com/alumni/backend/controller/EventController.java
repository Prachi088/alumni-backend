package com.alumni.backend.controller;

import com.alumni.backend.model.Event;
import com.alumni.backend.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // GET all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // POST — alumni creates a new event
    @PostMapping
    public Event postEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // DELETE — alumni removes their event
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
    }

    // POST /{id}/register — student registers; tracking is done client-side via localStorage
    @PostMapping("/{id}/register")
    public ResponseEntity<String> registerEvent(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        // Registration tracking is handled on the frontend in localStorage.
        // This endpoint simply returns 200 OK so the frontend flow completes.
        return ResponseEntity.ok("Registered successfully");
    }
}
