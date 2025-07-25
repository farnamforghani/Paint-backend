package org.example.painter_backend.service;

import jakarta.transaction.Transactional;
import org.example.painter_backend.dto.PaintingDto;
import org.example.painter_backend.dto.ShapeDto;
import org.example.painter_backend.model.Painting;
import org.example.painter_backend.model.Shape;
import org.example.painter_backend.model.User;
import org.example.painter_backend.repository.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaintingService {

    @Autowired
    private PaintingRepository paintingRepository;

    public List<PaintingDto> getUserPaintings(User user) {
        List<Painting> paintings = paintingRepository.findByUserOrderByUpdatedAtDesc(user);
        return paintings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PaintingDto> getPaintingById(Long id, User user) {
        Optional<Painting> painting = paintingRepository.findByIdAndUser(id, user);
        return painting.map(this::convertToDto);
    }

    public PaintingDto savePainting(PaintingDto paintingDto, User user) {
        Painting painting = new Painting(paintingDto.getName(), user);
        painting.setVersion(paintingDto.getVersion());

        // Convert shapes
        List<Shape> shapes = paintingDto.getShapes().stream()
                .map(shapeDto -> convertToShape(shapeDto, painting))
                .collect(Collectors.toList());

        painting.setShapes(shapes);
        Painting savedPainting = paintingRepository.save(painting);
        return convertToDto(savedPainting);
    }

    public PaintingDto updatePainting(Long id, PaintingDto paintingDto, User user) {
        Optional<Painting> existingPainting = paintingRepository.findByIdAndUser(id, user);

        if (existingPainting.isPresent()) {
            Painting painting = existingPainting.get();
            painting.setName(paintingDto.getName());
            painting.setVersion(paintingDto.getVersion());

            // Clear existing shapes and add new ones
            painting.getShapes().clear();
            List<Shape> shapes = paintingDto.getShapes().stream()
                    .map(shapeDto -> convertToShape(shapeDto, painting))
                    .collect(Collectors.toList());
            painting.getShapes().addAll(shapes);

            Painting savedPainting = paintingRepository.save(painting);
            return convertToDto(savedPainting);
        }

        throw new RuntimeException("Painting not found with id: " + id);
    }

    public void deletePainting(Long id, User user) {
        paintingRepository.deleteByIdAndUser(id, user);
    }

    private PaintingDto convertToDto(Painting painting) {
        List<ShapeDto> shapeDtos = painting.getShapes().stream()
                .map(this::convertToShapeDto)
                .collect(Collectors.toList());

        PaintingDto dto = new PaintingDto(painting.getName(), shapeDtos, painting.getVersion());
        dto.setId(painting.getId());
        dto.setTimestamp(painting.getUpdatedAt());
        return dto;
    }

    private ShapeDto convertToShapeDto(Shape shape) {
        return new ShapeDto(
                shape.getShapeId(),
                shape.getType(),
                shape.getX(),
                shape.getY(),
                shape.getWidth(),
                shape.getHeight(),
                shape.getColor()
        );
    }

    private Shape convertToShape(ShapeDto shapeDto, Painting painting) {
        return new Shape(
                shapeDto.getId(),
                shapeDto.getType(),
                shapeDto.getX(),
                shapeDto.getY(),
                shapeDto.getWidth(),
                shapeDto.getHeight(),
                shapeDto.getColor(),
                painting
        );
    }
}
