package ssafy.StackFlow.Service.Retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Retrieval.Retrieval;
import ssafy.StackFlow.Domain.Retrieval.RetrievalProduct;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.ProductStore;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.RT.RtRepository;
import ssafy.StackFlow.Repository.Retrieval.RetrievalProductRepository;
import ssafy.StackFlow.Repository.Retrieval.RetrievalRepository;
import ssafy.StackFlow.Repository.StoreRepository;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Repository.product.ProductStoreRepository;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.Service.store.StoreService;

import java.util.List;

    @Service
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    public class RetrievalService {

        private final ProductRepo productRepo;
        private final RtRepository rtRepository;
        private final StoreRepository storeRepository;
        private final UserRepository userRepository;
        private final RetrievalRepository retrievalRepository;
        private final ProductStoreRepository productStoreRepository;
        private final StoreService storeService;
        private final ProductService productService;
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
        @Transactional
        public void retrievalAdmin(Long productId, Long headOfficeId, Long storeId, int quantity) {
            ProductStore TotalStock = productStoreRepository.findByStoreAndProduct(
                    storeService.findStoreById(headOfficeId),
                    productService.findProductById(productId)
            ).orElseThrow(() -> new IllegalArgumentException("본사 재고가 존재하지 않습니다."));

            ProductStore storeStock = productStoreRepository.findByStoreAndProduct(
                    storeService.findStoreById(storeId),
                    productService.findProductById(productId)
            ).orElseGet(() -> {
                ProductStore newStoreStock = new ProductStore();
                newStoreStock.setStore(storeService.findStoreById(storeId));
                newStoreStock.setProduct(productService.findProductById(productId));
                newStoreStock.setStockQuantity(0);
                return productStoreRepository.save(newStoreStock);
            });

            if (TotalStock.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("본사 재고가 부족합니다.");
            }
            TotalStock.setStockQuantity(TotalStock.getStockQuantity() - quantity);

            storeStock.setStockQuantity(storeStock.getStockQuantity() + quantity);
            productStoreRepository.save(TotalStock);
            productStoreRepository.save(storeStock);
        }

        @Transactional
        public void retrievalUser(Long productId, Long headOfficeId, int quantity) {
            ProductStore TotalStock = productStoreRepository.findByStoreAndProduct(
                    storeService.findStoreById(headOfficeId),
                    productService.findProductById(productId)
            ).orElseThrow(() -> new IllegalArgumentException("본사 재고가 존재하지 않습니다."));
            Store loginStore = getUserStore();
            Long storeId = loginStore.getId();
            ProductStore storeStock = productStoreRepository.findByStoreAndProduct(
                    storeService.findStoreById(storeId),
                    productService.findProductById(productId)
            ).orElseGet(() -> {
                ProductStore newStoreStock = new ProductStore();
                newStoreStock.setStore(storeService.findStoreById(storeId));
                newStoreStock.setProduct(productService.findProductById(productId));
                newStoreStock.setStockQuantity(0);
                return productStoreRepository.save(newStoreStock);
            });
            if (storeStock.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("본사 재고가 부족합니다.");
            }
            TotalStock.setStockQuantity(TotalStock.getStockQuantity() + quantity);
            storeStock.setStockQuantity(storeStock.getStockQuantity() - quantity);
            productStoreRepository.save(TotalStock);
            productStoreRepository.save(storeStock);
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
        public Retrieval createRetrievalInstruction(Long productId, Long storeId, int retQuan) {
            try {
                Product product = productRepo.findById(productId)
                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

                Store store = storeRepository.findById(storeId)
                        .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다."));

                Retrieval retrieval = Retrieval.createRetrieval(product, store);

                RetrievalProduct retrievalProduct = RetrievalProduct.createRetrievalProduct(product, retQuan);
                retrieval.addRetrievalProduct(retrievalProduct);

                Retrieval savedretrieval = retrievalRepository.save(retrieval);

                return savedretrieval;
            } catch (Exception e) {
                throw new RuntimeException("출고 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        @Transactional
        public Retrieval createRetrievalInstruction_User(Long productId, Long storeId, int retQuan) {
            try {
                Product product = productRepo.findById(productId)
                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

                Store store = storeRepository.findById(1L)
                        .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다."));

                Retrieval retrieval = Retrieval.createRetrieval(product, store);

                RetrievalProduct retrievalProduct = RetrievalProduct.createRetrievalProduct(product, retQuan);
                retrieval.addRetrievalProduct(retrievalProduct);

                Retrieval savedretrieval = retrievalRepository.save(retrieval);

                return savedretrieval;
            } catch (Exception e) {
                throw new RuntimeException("출고 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        public List<Retrieval> findAllRetrievals() {
            return retrievalRepository.findAll();
        }
    }

