package ssafy.StackFlow.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Size;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String prodName;
    private String prodCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category prodCate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "color_id")
    private Color colorCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Store> stores = new ArrayList<>();
}

