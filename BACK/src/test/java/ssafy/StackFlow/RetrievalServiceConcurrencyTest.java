package ssafy.StackFlow;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssafy.StackFlow.Domain.product.entity.ProductStore;
import ssafy.StackFlow.Domain.product.repository.ProductStoreRepository;
import ssafy.StackFlow.Domain.Retrieval.service.RetrievalService;
import ssafy.StackFlow.Domain.product.service.ProductService;
import ssafy.StackFlow.Domain.store.service.StoreService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class RetrievalServiceConcurrencyTest {
    @Autowired
    private RetrievalService retrievalService;

    @Autowired
    private ProductStoreRepository productStoreRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Test
    void 동시에_100개_요청_보내기() throws InterruptedException {
        // given
        final int threadCount = 100;
        final int quantity = 1;
        final Long productId = 1L; // 테스트용 상품 ID
        final Long headOfficeId = 1L; // 본사 ID
        final Long storeId = 2L; // 테스트용 매장 ID

        // 초기 재고 설정
        ProductStore headOfficeStock = productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(headOfficeId),
                productService.findProductById(productId)
        ).orElseThrow();
        headOfficeStock.setStockQuantity(100); // 초기 재고를 100개로 설정
        productStoreRepository.save(headOfficeStock);

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    retrievalService.retrievalAdmin(productId, headOfficeId, storeId, quantity);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        ProductStore finalHeadOfficeStock = productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(headOfficeId),
                productService.findProductById(productId)
        ).orElseThrow();

        ProductStore finalStoreStock = productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(storeId),
                productService.findProductById(productId)
        ).orElseThrow();

        // 예상: 본사 재고는 0이 되어야 하고, 매장 재고는 100이 되어야 함
        assertEquals(0, finalHeadOfficeStock.getStockQuantity());
        assertEquals(100, finalStoreStock.getStockQuantity());
    }

    @Test
    void 동시에_재고초과_요청_보내기() throws InterruptedException {
        // given
        final int threadCount = 100;
        final int quantity = 2; // 각 요청당 2개씩
        final Long productId = 1L;
        final Long headOfficeId = 1L;
        final Long storeId = 2L;

        // 초기 재고 150개로 설정 (100개 요청 * 2개 = 200개 필요)
        ProductStore headOfficeStock = productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(headOfficeId),
                productService.findProductById(productId)
        ).orElseThrow();
        headOfficeStock.setStockQuantity(150);
        productStoreRepository.save(headOfficeStock);

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    retrievalService.retrievalAdmin(productId, headOfficeId, storeId, quantity);
                    successCount.incrementAndGet();
                } catch (IllegalArgumentException e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        ProductStore finalHeadOfficeStock = productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(headOfficeId),
                productService.findProductById(productId)
        ).orElseThrow();

        ProductStore finalStoreStock = productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(storeId),
                productService.findProductById(productId)
        ).orElseThrow();

        System.out.println("성공한 요청 수: " + successCount.get());
        System.out.println("실패한 요청 수: " + failCount.get());
        System.out.println("최종 본사 재고: " + finalHeadOfficeStock.getStockQuantity());
        System.out.println("최종 매장 재고: " + finalStoreStock.getStockQuantity());

        // 모든 요청이 처리되었는지 확인
        assertEquals(threadCount, successCount.get() + failCount.get());
        // 재고가 음수가 되지 않았는지 확인
        assertTrue(finalHeadOfficeStock.getStockQuantity() >= 0);
        // 총 처리된 수량이 초기 재고를 넘지 않는지 확인
        assertTrue(finalStoreStock.getStockQuantity() <= 150);
    }
}
