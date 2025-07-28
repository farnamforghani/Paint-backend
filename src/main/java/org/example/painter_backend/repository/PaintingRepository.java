package org.example.painter_backend.repository;

import org.example.painter_backend.model.Painting;
import org.example.painter_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
    List<Painting> findByUserOrderByUpdatedAtDesc(User user);
    Optional<Painting> findByIdAndUser(Long id, User user);
}
