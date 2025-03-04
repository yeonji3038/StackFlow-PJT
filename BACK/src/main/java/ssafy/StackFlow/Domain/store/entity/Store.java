package ssafy.StackFlow.Domain.store.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.product.entity.ProductStore;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String location; // 매장 위치

    @Column(nullable = false, unique = true)
    private String storeCode; // 매장 코드

    @Column
    private Timestamp createdAt;


    @ManyToMany
    @JoinTable(
            name = "store_product",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductStore> storeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Signup> signups;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id") // signup 테이블의 user_id를 참조
//    private Signup signup; // signup 도메인 엔티티와 연결
//
//    // Signup에서 username 가져오기
//    public String getUsername() {
//        return signup != null ? signup.getUsername() : null;
//    }
//
//    // Signup에서 email 가져오기
//    public String getEmail() {
//        return signup != null ? signup.getEmail() : null;
//    }
}