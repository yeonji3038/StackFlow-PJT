package ssafy.StackFlow.Domain.RT.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Signup;

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
    private String myStore;
    private LocalDateTime reqDate;

    @Enumerated(EnumType.STRING)
    private RtStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "RT", cascade = CascadeType.ALL)
    private List<RtProduct> rtProducts = new ArrayList<>();

    public void setStatus(RtStatus status) {
        this.status = status;
    }

    public RtStatus getStatus() {
        return status;
    }


    public void addRtProduct(RtProduct rtProduct) {
        rtProducts.add(rtProduct);
        rtProduct.setRT(this);
    }


    public static RT createRT(Product product, Store store, Signup loginUser) {
        RT rt = new RT();
        rt.status = RtStatus.REQUEST;

        rt.setProdCode(product.getProdCode());
        rt.setProdName(product.getProdName());

        if (product.getColorCode() != null) {
            rt.setColorCode(product.getColorCode().getColorCode());
        }

        if (product.getSize() != null) {
            rt.setSize(product.getSize().getSize());
        }

        rt.setReqStore(store.getStoreName());

        if (loginUser != null && loginUser.getStore() != null) {
            rt.setMyStore(loginUser.getStore().getStoreName());
        }

        rt.setReqDate(LocalDateTime.now());

        return rt;
    }


}