//package ssafy.StackFlow.Domain.product;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class Brand {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="brand_id")
//    private Long id;
//
//    private String brandCode;
//    private String brandDetail;
//
////    @OneToMany(mappedBy = "brandCode")
////    private List<Product> products = new ArrayList<>();
//
//    @Override
//    public String toString() {
//        return brandCode;
//    }
//}
