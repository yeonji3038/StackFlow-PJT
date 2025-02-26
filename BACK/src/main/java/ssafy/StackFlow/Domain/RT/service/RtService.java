package ssafy.StackFlow.Domain.RT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.RT.entity.RtStatus;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtProduct;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Signup;
import ssafy.StackFlow.Domain.product.repository.ProductRepo;
import ssafy.StackFlow.Domain.RT.repository.RtRepository;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ssafy.StackFlow.Domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RtService {

    private final ProductRepo productRepo;
    private final RtRepository rtRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public Product findProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    public List<Product> findProducts() {
        return productRepo.findAll();
    }

    public List<Product> search(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    public List<Product> searchByFilters(String categoryGroup, String categoryCode, String colorCode, String size) {
        return productRepo.findProductsByFilters(categoryGroup, categoryCode, colorCode, size);
    }

    @Transactional
    public RT createInstruction(Long productId, Long storeId, int reqQuan) {
        try {
            Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            
            Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다."));
            
            Signup currentUser = getCurrentUser();
            
            RT rt = RT.createRT(product, store, currentUser);
            
            RtProduct rtProduct = RtProduct.createRtProduct(product, reqQuan);
            rt.addRtProduct(rtProduct);
            
            RT savedRt = rtRepository.save(rt);
            
            return savedRt;
        } catch (Exception e) {
            throw new RuntimeException("RT 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public List<RT> findAllRTs() {
        return rtRepository.findAll();
    }

    public Signup getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("로그인된 사용자 없음");
        }

        String username = authentication.getName();
        
        return userRepository.findByusername(username)
            .orElseThrow(() -> new RuntimeException("해당 사용자 찾지 못함: " + username));
    }

    public Store getUserStore() {
        Signup user = getCurrentUser();
        return user.getStore();
    }

    public List<RT> getMyRT() {
        Store loginStore = getUserStore();
        if (loginStore == null) {
            return new ArrayList<>();
        }
        List<RT> rtList = findAllRTs();
        List<RT> myRTs = new ArrayList<>();
        for (RT rt : rtList) {
            if (rt.getReqStore().equals(loginStore.getStoreName())) {
                myRTs.add(rt);
            }
        }
        return myRTs;
    }

    public List<RT> getOtherRT() {
        Store loginStore = getUserStore();
        if (loginStore == null) {
            return new ArrayList<>();
        }
        List<RT> rtList = findAllRTs();
        List<RT> otherRTs = new ArrayList<>();
        for (RT rt : rtList) {
            if (rt.getMyStore().equals(loginStore.getStoreName())) {
                otherRTs.add(rt);
            }
        }
        return otherRTs;
    }

    @Transactional
    public void updateRtStatus(List<Long> rtIds, RtStatus status) {
        if (rtIds == null || rtIds.isEmpty()) {
            throw new IllegalArgumentException("RT IDs cannot be null or empty");
        }
        List<RT> rts = rtRepository.findAllById(rtIds);
        for (RT rt : rts) {
            rt.setStatus(status);
        }
        rtRepository.saveAll(rts);
    }
}