package com.alumni.backend.repository;

import com.alumni.backend.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByReceiverIdAndStatus(Long receiverId, String status);
    List<Connection> findBySenderId(Long senderId);
    List<Connection> findByReceiverId(Long receiverId);
}
