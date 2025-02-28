package ssafy.StackFlow.Domain.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="color_id")
    private Long id;

    private String colorName;
    private String colorCode;

    @OneToMany(mappedBy = "colorCode")
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return colorCode;
    }
}