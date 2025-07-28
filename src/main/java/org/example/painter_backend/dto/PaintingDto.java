package org.example.painter_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PaintingDto {
    // Getters and Setters
    private Long id;
    private String name;
    private List<ShapeDto> shapes;
    private LocalDateTime timestamp;
    private String version;

    public PaintingDto(String name, List<ShapeDto> shapes, String version) {
        this.name = name;
        this.shapes = shapes;
        this.version = version;
        this.timestamp = LocalDateTime.now();
    }

}
