package ssafy.StackFlow.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String cateGroup;
    private String cateCode;
    private String cateName;

    @OneToOne(mappedBy = "prodCate")
    private Product prodCategory;
}
