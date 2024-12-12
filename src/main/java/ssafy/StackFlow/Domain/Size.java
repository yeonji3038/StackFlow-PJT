package ssafy.StackFlow.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="size_id")
    private Long id;
    private String size;

    @OneToMany(mappedBy = "size")
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return size;
    }
}
