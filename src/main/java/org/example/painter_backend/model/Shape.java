package org.example.painter_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "shapes")
public class Shape {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shape_id", nullable = false)
    private String shapeId;

    @Column(nullable = false)
    private String type;

    @Column(name = "pos_x", nullable = false)
    private Double x;

    @Column(name = "pos_y", nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "painting_id", nullable = false)
    private Painting painting;

    // Constructors
    public Shape() {}

    public Shape(String shapeId, String type, Double x, Double y,
                 Double width, Double height, String color, Painting painting) {
        this.shapeId = shapeId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.painting = painting;
    }

}
