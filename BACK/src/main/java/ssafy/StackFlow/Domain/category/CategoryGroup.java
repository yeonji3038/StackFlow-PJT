package ssafy.StackFlow.Domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.product.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CategoryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_group_id")
    private Long id;
    private String groupName;

    @OneToMany(mappedBy = "cateGroup")
    private List<Category> cate_codes = new ArrayList<>();

    @OneToMany(mappedBy = "cateGroup")
    private List<Product> products_cate_group = new ArrayList<>();
}
