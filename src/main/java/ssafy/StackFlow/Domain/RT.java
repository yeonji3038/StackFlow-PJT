package ssafy.StackFlow.Domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
public class RT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RT_id")
    private Long id;
    private String prodCode;
    private String prodName;
    private String colorCode;
    private String size;
    private String reqStore;
    private LocalDateTime reqDate;

    @JsonIgnore
    @OneToMany(mappedBy = "RT", cascade = CascadeType.ALL)
    private List<RtProduct> rtProducts = new ArrayList<>();

    public void addRtProduct(RtProduct rtProduct) {
        rtProducts.add(rtProduct);
        rtProduct.setRT(this);
    }

    public static RT createRT(Product product, Store store) {
        RT rt = new RT();
        rt.setProdCode(product.getProdCode());
        rt.setProdName(product.getProdName());
        rt.setColorCode(product.getColorCode());
        rt.setSize(product.getSize());
        rt.setReqStore(store.getStoreName());
        rt.setReqDate(LocalDateTime.now());
        return rt;
    }
}


