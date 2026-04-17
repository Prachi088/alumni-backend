package com.alumni.backend.repository;

import com.alumni.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPostedBy(Long postedBy);
}
