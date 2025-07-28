package org.example.painter_backend.repository;

import org.example.painter_backend.model.Painting;
import org.example.painter_backend.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {
    Optional<Shape> findByIdAndPainting(Long id, Painting painting);
    void deleteByIdAndPainting(Long id, Painting painting);

    List<Shape> getByPainting(Painting painting);
}
