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
    private String storeName; //매장 이름

    @Column(nullable = false)
    private String location; // 매장 위치

    private String adminArea; // 시·도

    private String subArea; // 시·군·구

    @Column(nullable = false, unique = true)
    private String storeCode; // 매장 코드

    @Column
    private Timestamp createdAt; //생성일


    //조인

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


    //회원
    @OneToMany(mappedBy = "store")
    private List<Signup> signups;


}