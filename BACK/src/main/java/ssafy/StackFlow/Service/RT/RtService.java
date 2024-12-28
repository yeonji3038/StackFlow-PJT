package ssafy.StackFlow.Service.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.RT.RtStatus;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.RT.RtProduct;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Repository.RT.RtRepository;
import ssafy.StackFlow.Repository.StoreRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ssafy.StackFlow.Repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void updateRtStatus(List<Long> requestIds, String status) {
        final RtStatus rtStatus;
        try {
            rtStatus = RtStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            System.err.println("잘못된 상태 값입니다: " + status);
            return;
        }

        for (Long requestId : requestIds) {
            Optional<RT> rtRequestOpt = rtRepository.findById(requestId);
            rtRequestOpt.ifPresent(rtRequest -> {
                rtRequest.setStatus(rtStatus);
                rtRepository.save(rtRequest);
            });
        }
    }


}