package ssafy.StackFlow.api.RT;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Repository.RT.RtApiRepository;
import ssafy.StackFlow.Repository.StoreRepository;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Repository.product.SizeRepository;
import ssafy.StackFlow.Service.RT.RtService;
import ssafy.StackFlow.api.RT.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@CrossOrigin(origins="http://localhost:3000")

@RestController
@RequiredArgsConstructor
public class RtApiController {

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

    @GetMapping("/api/rt/product")
    public List<RtProdDto> RtProdListApi() {
        List<Product> products = productRepo.findAll();
        List<RtProdDto> result = products.stream()
                .map(o -> new RtProdDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/rt/category")
    public List<RtCategoryDto> RtCateListApi() {
        List<Category> categories = categoryRepository.findAll();
        List<RtCategoryDto> result = categories.stream()
                .map(o -> new RtCategoryDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/rt/categoryGroup")
    public List<RtCategoryGroupDto> RtCateGroupListApi() {
        List<CategoryGroup> categorieGroups = categoryGroupRepository.findAll();
        List<RtCategoryGroupDto> result = categorieGroups.stream()
                .map(o -> new RtCategoryGroupDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/rt/color")
    public List<RtColorDto> RtColorListApi() {
        List<Color> colors = colorRepository.findAll();
        List<RtColorDto> result = colors.stream()
                .map(o -> new RtColorDto(o))
                .collect(toList());
        return result;
    }
    @GetMapping("/api/rt/store")
    public List<RtStoreDto> RtStoreListApi() {
        List<Store> stores = storeRepository.findAll();
        List<RtStoreDto> result = stores.stream()
                .map(o -> new RtStoreDto(o))
                .collect(toList());
        return result;
    }
    @GetMapping("/api/rt/size")
    public List<RtSizeDto> RtSizeListApi() {
        List<Size> sizes = sizeRepository.findAll();
        List<RtSizeDto> result = sizes.stream()
                .map(o -> new RtSizeDto(o))
                .collect(toList());
        return result;
    }


@GetMapping("/api/rt/all")
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
        colorCodeMap.put(color.getId(), color.getColorName());
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


    @PostMapping("/api/rt/submit")
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

    @GetMapping("/api/rt/meToOtherRtlist")
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

    @GetMapping("/api/rt/OtherToMeRtlist")
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


    @GetMapping("/api/rt/RTAlllist")
    public List<RTAllDto> RtList3Api() {
        List<RT> rtList = rtApiRepository.findAllWithItem();
        List<RTAllDto> result = rtList.stream()
                .map(o -> new RTAllDto(o))
                .collect(toList());
        return result;
    }
}
