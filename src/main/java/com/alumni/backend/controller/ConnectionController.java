package com.alumni.backend.controller;

import com.alumni.backend.model.Connection;
import com.alumni.backend.model.User;
import com.alumni.backend.repository.ConnectionRepository;
import com.alumni.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/connections")
public class ConnectionController {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    public ConnectionController(ConnectionRepository connectionRepository,
                                UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository       = userRepository;
    }

    // POST /api/connections/send — called by StudentDashboard Network page
    @PostMapping("/send")
    public Connection sendConnectionRequest(@RequestBody Map<String, Long> body) {
        Connection c = new Connection();
        c.setSenderId(body.get("fromId"));
        c.setReceiverId(body.get("toId"));
        c.setStatus("pending");
        return connectionRepository.save(c);
    }

    // POST /api/connections — generic send (kept for backwards compatibility)
    @PostMapping
    public Connection sendRequest(@RequestBody Connection connection) {
        connection.setStatus("pending");
        return connectionRepository.save(connection);
    }

    // GET /api/connections — list all (admin / debug use)
    @GetMapping
    public List<Connection> getAll() {
        return connectionRepository.findAll();
    }

    // GET /api/connections/pending/{userId} — returns pending requests WITH sender details
    @GetMapping("/pending/{userId}")
    public List<Map<String, Object>> getPendingRequests(@PathVariable Long userId) {

        List<Connection> pending =
                connectionRepository.findByReceiverIdAndStatus(userId, "pending");

        List<Map<String, Object>> result = new ArrayList<>();

        for (Connection c : pending) {
            Optional<User> senderOpt = userRepository.findById(c.getSenderId());

            Map<String, Object> data = new HashMap<>();
            data.put("id",     c.getId());
            data.put("status", c.getStatus());

            if (senderOpt.isPresent()) {
                User sender = senderOpt.get();
                data.put("name",  sender.getName());
                data.put("email", sender.getEmail());
                data.put("role",  sender.getRole());
            }

            result.add(data);
        }

        return result;
    }

    // PUT /api/connections/accept/{id} — mark connection as accepted
    @PutMapping("/accept/{id}")
    public Connection acceptRequest(@PathVariable Long id) {
        Connection c = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found: " + id));
        c.setStatus("accepted");
        return connectionRepository.save(c);
    }

    // PUT /api/connections/reject/{id} — delete the connection request
    @PutMapping("/reject/{id}")
    public void rejectRequest(@PathVariable Long id) {
        connectionRepository.deleteById(id);
    }

    // GET /api/connections/accepted/{userId} — returns accepted connections WITH user details
    @GetMapping("/accepted/{userId}")
    public List<Map<String, Object>> getAcceptedConnections(@PathVariable Long userId) {

        List<Connection> all = connectionRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Connection c : all) {
            boolean isAccepted   = "accepted".equals(c.getStatus());
            boolean involvesUser = c.getSenderId().equals(userId) || c.getReceiverId().equals(userId);

            if (!isAccepted || !involvesUser) continue;

            // Get the OTHER person in the connection
            Long otherUserId = c.getSenderId().equals(userId)
                    ? c.getReceiverId()
                    : c.getSenderId();

            Optional<User> userOpt = userRepository.findById(otherUserId);

            Map<String, Object> data = new HashMap<>();
            data.put("id", c.getId());
            data.put("userId", otherUserId);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                data.put("name", user.getName());
                data.put("role", user.getRole());
            }

            result.add(data);
        }

        return result;
    }
}