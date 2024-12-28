package ssafy.StackFlow.Domain.Retrieval;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.user.Signup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Retrieval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Retrieval_id")
    private Long id;
    private String prodCode;
    private String prodName;
    private String colorCode;
    private String size;
    private int retQuantity;
    private String retStore;
    private LocalDateTime retDate;
    private String reqStore;

    @Enumerated(EnumType.STRING)
    private RetrievalStatus retStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "Retrieval", cascade = CascadeType.ALL)
    private List<RetrievalProduct> retrievalProducts = new ArrayList<>();

    public void addRetrievalProduct(RetrievalProduct retrievalProduct) {
        retrievalProducts.add(retrievalProduct);
        retrievalProduct.setRetrieval(this);
    }

    public static Retrieval createRetrieval(Product product, Store store, Signup loginUser) {
        Retrieval retrieval = new Retrieval();
        retrieval.retStatus = RetrievalStatus.RETRIEVAL;

        retrieval.setProdCode(product.getProdCode());
        retrieval.setProdName(product.getProdName());

        if (product.getColorCode() != null) {
            retrieval.setColorCode(product.getColorCode().getColorCode());
        }

        if (product.getSize() != null) {
            retrieval.setSize(product.getSize().getSize());
        }
        if (loginUser != null && loginUser.getStore() != null) {
            retrieval.setReqStore(loginUser.getStore().getStoreName());
        }
        retrieval.setRetStore(store.getStoreName());
        retrieval.setRetDate(LocalDateTime.now());

        return retrieval;
    }


}