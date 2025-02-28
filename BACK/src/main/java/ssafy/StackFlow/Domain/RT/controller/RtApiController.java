package ssafy.StackFlow.Domain.RT.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtStatus;
import ssafy.StackFlow.Domain.RT.dto.*;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.category.entity.Category;
import ssafy.StackFlow.Domain.category.entity.CategoryGroup;
import ssafy.StackFlow.Domain.product.entity.Color;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.product.entity.Size;
import ssafy.StackFlow.Domain.RT.repository.RtApiRepository;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.category.repository.CategoryGroupRepository;
import ssafy.StackFlow.Domain.category.repository.CategoryRepository;
import ssafy.StackFlow.Domain.product.repository.ColorRepository;
import ssafy.StackFlow.Domain.product.repository.ProductRepo;
import ssafy.StackFlow.Domain.product.repository.SizeRepository;
import ssafy.StackFlow.Domain.RT.service.RtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.global.docs.RtApiSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@CrossOrigin(origins="http://localhost:3000")

@RestController
@RequiredArgsConstructor
@RequestMapping("/rt")
public class RtApiController implements RtApiSpecification {

    private final RtService rtService;
    private final RtApiRepository rtApiRepository;
    private final ProductRepo productRepo;
    private final CategoryRepository categoryRepository;
    private final CategoryGroupRepository categoryGroupRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final StoreRepository storeRepository;


    @GetMapping("/csrf")
    public CsrfToken csrf(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    @GetMapping("/product")
    public List<RtProdDto> RtProdListApi() {
        List<Product> products = productRepo.findAll();
        List<RtProdDto> result = products.stream()
                .map(o -> new RtProdDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/category")
    public List<RtCategoryDto> RtCateListApi() {
        List<Category> categories = categoryRepository.findAll();
        List<RtCategoryDto> result = categories.stream()
                .map(o -> new RtCategoryDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/categoryGroup")
    public List<RtCategoryGroupDto> RtCateGroupListApi() {
        List<CategoryGroup> categorieGroups = categoryGroupRepository.findAll();
        List<RtCategoryGroupDto> result = categorieGroups.stream()
                .map(o -> new RtCategoryGroupDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/color")
    public List<RtColorDto> RtColorListApi() {
        List<Color> colors = colorRepository.findAll();
        List<RtColorDto> result = colors.stream()
                .map(o -> new RtColorDto(o))
                .collect(toList());
        return result;
    }
    @GetMapping("/store")
    public List<RtStoreDto> RtStoreListApi() {
        List<Store> stores = storeRepository.findAll();
        List<RtStoreDto> result = stores.stream()
                .map(o -> new RtStoreDto(o))
                .collect(toList());
        return result;
    }
    @GetMapping("/size")
    public List<RtSizeDto> RtSizeListApi() {
        List<Size> sizes = sizeRepository.findAll();
        List<RtSizeDto> result = sizes.stream()
                .map(o -> new RtSizeDto(o))
                .collect(toList());
        return result;
    }


@GetMapping("/all")
public Map<String, Map<Long, String>> RtAllListApi() {
    List<Color> colors = colorRepository.findAll();
    List<CategoryGroup> categoryGroups = categoryGroupRepository.findAll();
    List<Category> categories = categoryRepository.findAll();
    List<Size> sizes = sizeRepository.findAll();
    List<Store> stores = storeRepository.findAll();

    Map<String, Map<Long, String>> result = new HashMap<>();

    Map<Long, String> categoryCodeMap = new HashMap<>();
    for (Category category : categories) {
        categoryCodeMap.put(category.getId(), category.getCateCode());
    }
    result.put("category_code", categoryCodeMap);

    Map<Long, String> categoryGroupCodeMap = new HashMap<>();
    for (CategoryGroup categoryGroup : categoryGroups) {
        categoryGroupCodeMap.put(categoryGroup.getId(), categoryGroup.getGroupName());
    }
    result.put("category_group_code", categoryGroupCodeMap);

    Map<Long, String> colorCodeMap = new HashMap<>();
    for (Color color : colors) {
        colorCodeMap.put(color.getId(), color.getColorCode());
    }
    result.put("color_code", colorCodeMap);

    Map<Long, String> sizeMap = new HashMap<>();
    for (Size size : sizes) {
        sizeMap.put(size.getId(), size.getSize());
    }
    result.put("size", sizeMap);

    Map<Long, String> storeNameMap = new HashMap<>();
    for (Store store : stores) {
        storeNameMap.put(store.getId(), store.getStoreName());
    }
    result.put("store_name", storeNameMap);
    return result;
}


    @PostMapping("/submit")
    public ResponseEntity<RtResponseDto> createInstructions(@RequestBody RtRequestDto request) {
        try {
            List<Long> rtIds = new ArrayList<>();

            for (RtInstructionDto instruction : request.getInstructions()) {
                Long rtId = rtService.createInstruction(
                        instruction.getProductId(),
                        instruction.getStoreId(),
                        instruction.getRequestQuantity()
                ).getId();
                rtIds.add(rtId);
            }

            return ResponseEntity.ok(new RtResponseDto("success", rtIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new RtResponseDto("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new RtResponseDto("error", "Unexpected error occurred"));
        }
    }

    @GetMapping("/meToOtherRtlist")
    public List<MyRtDto> RtList1Api() {
        Store loginStore = rtService.getUserStore();
        if (loginStore == null) {
            return new ArrayList<>();
        }
        List<RT> rtList = rtApiRepository.findAllWithItem();
        List<MyRtDto> result = rtList.stream()
                .filter(rt -> rt.getMyStore().equals(loginStore.getStoreName()))
                .map(MyRtDto::new)
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/OtherToMeRtlist")
    public List<OtherRtDto> RtList2Api() {
        Store loginStore = rtService.getUserStore();
        if (loginStore == null) {
            return new ArrayList<>();
        }
        List<RT> rtList = rtApiRepository.findAllWithItem();
        List<OtherRtDto> result = rtList.stream()
                .filter(rt -> rt.getReqStore().equals(loginStore.getStoreName()))
                .map(OtherRtDto::new)
                .collect(Collectors.toList());
        return result;
    }


    @GetMapping("/RTAlllist")
    public List<RTAllDto> RtList3Api() {
        List<RT> rtList = rtApiRepository.findAllWithItem();
        List<RTAllDto> result = rtList.stream()
                .map(o -> new RTAllDto(o))
                .collect(toList());
        return result;
    }

    @PutMapping("/RTstatus")
    public ResponseEntity<?> updateRtStatus(
            @RequestBody RtStatusUpdateRequest request) {
        try {
            rtService.updateRtStatus(request.getSelectedRequests(), RtStatus.valueOf(request.getAction()));
            return ResponseEntity.ok(Map.of(
                    "message", RtStatus.valueOf(request.getAction()) == RtStatus.APPROVAL ?
                            "승인 처리가 완료되었습니다." : "거절 처리가 완료되었습니다."
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
