package ssafy.StackFlow.Domain;

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

    @OneToOne(mappedBy = "colorCode")
    private Product prodColor;

    @Override
    public String toString() {
        return colorCode;
    }
}