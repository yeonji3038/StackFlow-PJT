package ssafy.StackFlow.Domain.RT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtProduct;
import ssafy.StackFlow.Domain.RT.entity.RtStatus;
import ssafy.StackFlow.Domain.RT.repository.RtRepository;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.product.repository.ProductRepo;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.user.entity.Signup;
import ssafy.StackFlow.Domain.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RtService {
    private final ProductRepo productRepo;
    private final RtRepository rtRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public Signup getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("로그인된 사용자가 없습니다");
        }

        String username = authentication.getName();
        return userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다: " + username));
    }

    public Store getUserStore() {
        Signup user = getCurrentUser();
        Store store = user.getStore();

        if (store == null) {
            throw new RuntimeException("사용자에게 할당된 매장이 없습니다");
        }

        return store;
    }

    public Product findProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));
    }

    public List<Product> search(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    public List<Product> searchByFilters(String categoryGroup, String categoryCode, String colorCode, String size) {
        return productRepo.findProductsByFilters(categoryGroup, categoryCode, colorCode, size);
    }

    @Transactional
    public RT createInstruction(Long productId, Long storeId, int reqQuan) {
        Signup currentUser = getCurrentUser();
        Store myStore = currentUser.getStore();

        if (myStore == null) {
            throw new RuntimeException("사용자에게 할당된 매장이 없습니다");
        }

        Store requestStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("요청 대상 매장을 찾을 수 없습니다"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));

        RT rt = RT.createRT(product, requestStore, currentUser);

        RtProduct rtProduct = RtProduct.createRtProduct(product, reqQuan);
        rt.addRtProduct(rtProduct);

        return rtRepository.save(rt);
    }

    public List<RT> getMyRTRequests() {
        Store myStore = getUserStore();
        return rtRepository.findByMyStoreWithProducts(myStore.getStoreName());
    }

    public List<RT> getOtherRTRequests() {
        Store myStore = getUserStore();
        return rtRepository.findByReqStoreWithProducts(myStore.getStoreName());
    }
    public Page<RT> getMyStoreRTs(Pageable pageable) {
        String myStoreName = getUserStore().getStoreName();
        return rtRepository.findByMyStore(myStoreName, pageable);
    }
    public Page<RT> getInstructionStatusList(String status, Pageable pageable) {
        if (status == null) {
            return rtRepository.findAll(pageable);
        }
        return rtRepository.findByStatus(RtStatus.valueOf(status), pageable);
    }

    @Transactional
    public void updateRtStatus(List<Long> rtIds, RtStatus status) {
        Store myStore = getUserStore();

        if (rtIds == null || rtIds.isEmpty()) {
            throw new IllegalArgumentException("RT ID 목록이 비어있습니다");
        }

        List<RT> rts = rtRepository.findAllById(rtIds);

        for (RT rt : rts) {
            if (!rt.getReqStore().equals(myStore.getStoreName())) {
                throw new RuntimeException("다른 매장의 RT 상태를 변경할 수 없습니다");
            }
            rt.setStatus(status);
        }

        rtRepository.saveAll(rts);
    }

    public Page<RT> getAllRTs(Pageable pageable) {
        return rtRepository.findAll(pageable);
    }

}