package ssafy.StackFlow.Domain.RT.service;

import lombok.RequiredArgsConstructor;
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

    /**
     * 현재 인증된 사용자 정보 조회
     */
    public Signup getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("로그인된 사용자가 없습니다");
        }

        String username = authentication.getName();
        return userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다: " + username));
    }

    /**
     * 현재 사용자의 매장 정보 조회
     */
    public Store getUserStore() {
        Signup user = getCurrentUser();
        Store store = user.getStore();

        if (store == null) {
            throw new RuntimeException("사용자에게 할당된 매장이 없습니다");
        }

        return store;
    }

    /**
     * 상품 ID로 상품 조회
     */
    public Product findProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));
    }

    /**
     * 키워드로 상품 검색
     */
    public List<Product> search(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    /**
     * 필터로 상품 검색
     */
    public List<Product> searchByFilters(String categoryGroup, String categoryCode, String colorCode, String size) {
        return productRepo.findProductsByFilters(categoryGroup, categoryCode, colorCode, size);
    }

    /**
     * RT 생성
     */
    @Transactional
    public RT createInstruction(Long productId, Long storeId, int reqQuan) {
        // 현재 로그인한 사용자 및 매장 정보 가져오기
        Signup currentUser = getCurrentUser();
        Store myStore = currentUser.getStore();

        if (myStore == null) {
            throw new RuntimeException("사용자에게 할당된 매장이 없습니다");
        }

        // 요청 대상 매장 조회
        Store requestStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("요청 대상 매장을 찾을 수 없습니다"));

        // 상품 조회
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));

        // RT 생성
        RT rt = RT.createRT(product, requestStore, currentUser);

        // RT 상품 생성 및 연결
        RtProduct rtProduct = RtProduct.createRtProduct(product, reqQuan);
        rt.addRtProduct(rtProduct);

        // 저장 및 반환
        return rtRepository.save(rt);
    }

    /**
     * 현재 사용자 매장의 RT 요청 목록 조회 (상품 정보 포함)
     */
    public List<RT> getMyRTRequests() {
        Store myStore = getUserStore();
        return rtRepository.findByMyStoreWithProducts(myStore.getStoreName());
    }

    /**
     * 다른 매장이 현재 사용자 매장에 요청한 RT 목록 조회 (상품 정보 포함)
     */
    public List<RT> getOtherRTRequests() {
        Store myStore = getUserStore();
        return rtRepository.findByReqStoreWithProducts(myStore.getStoreName());
    }

    /**
     * 모든 RT 목록 조회 (상품 정보 포함)
     */
    public List<RT> getAllRTsWithProducts() {
        return rtRepository.findAllWithProducts();
    }

    /**
     * RT 상태 업데이트
     */
    @Transactional
    public void updateRtStatus(List<Long> rtIds, RtStatus status) {
        // 상태 업데이트 권한 확인
        Store myStore = getUserStore();

        if (rtIds == null || rtIds.isEmpty()) {
            throw new IllegalArgumentException("RT ID 목록이 비어있습니다");
        }

        List<RT> rts = rtRepository.findAllById(rtIds);

        // 모든 RT가 내 매장으로 요청된 것인지 확인
        for (RT rt : rts) {
            if (!rt.getReqStore().equals(myStore.getStoreName())) {
                throw new RuntimeException("다른 매장의 RT 상태를 변경할 수 없습니다");
            }

            // 상태 변경
            rt.setStatus(status);
        }

        rtRepository.saveAll(rts);
    }
}