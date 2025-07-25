package org.example.painter_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShapeDto {
    // Getters and Setters
    private String id;
    private String type;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private String color;

    // Constructors
    public ShapeDto() {}

    public ShapeDto(String id, String type, Double x, Double y,
                    Double width, Double height, String color) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

}
