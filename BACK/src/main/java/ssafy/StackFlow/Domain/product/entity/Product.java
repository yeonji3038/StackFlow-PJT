package ssafy.StackFlow.Domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.StackFlow.Domain.category.entity.Category;
import ssafy.StackFlow.Domain.category.entity.CategoryGroup;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String prodName;
    private String prodCode;
    private String prodDetail;

    private int stockPrice;     // 입고 가격
    private int sellPrice;      // 출고 가격
    private int stockQuantity;  // 입고 수량

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category prodCate;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "brand_id")
//    private Brand brandCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "color_id")
    private Color colorCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_group_id")
    private CategoryGroup cateGroup;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "products")
//    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductStore> storeProducts = new ArrayList<>();

    public int getQuantity() {
        return storeProducts.stream()
                .mapToInt(ProductStore::getStockQuantity)
                .sum();
    }
}

