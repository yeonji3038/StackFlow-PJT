package ssafy.StackFlow.Domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.product.Product;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;
    @Column(name = "cate_code", nullable = false)
    private String cateCode;
    @Column(name = "cate_name", nullable = false)
    private String cateName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_code")
    private CategoryGroup cateGroup;

    @OneToMany(mappedBy = "prodCate")
    private List<Product> products_cate = new ArrayList<>();
}
