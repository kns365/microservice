package vn.com.kns.portalapi.application.service.shop.shopImportDetail;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailInputDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.core.entity.shop.ShopImportDetail;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ShopImportDetailRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopImportDetailSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopImportDetailServiceImpl implements ShopImportDetailService {

    @Autowired
    private ShopImportDetailRepository shopImportDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    //    @Cacheable(value = "shopImportDetails")
    @Override
    public List<ShopImportDetailDto> getAll(Long shopId) {
        List<ShopImportDetail> list = new ArrayList<>();
        if (shopId == null) {
            shopImportDetailRepository.findAll().forEach(list::add);
        } else {
            shopImportDetailRepository.findDistinctByShopId(shopId).forEach(list::add);
        }
        List<ShopImportDetailDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopImportDetailDto.class))
                .collect(Collectors.toList());
        return output;
    }

    //    @Cacheable(value = "shopImportDetails_supplies")
    @Override
    public List<SupplyDto> getAllSupplyByShopId(Long shopId) {
        List<ShopImportDetail> list = new ArrayList<>();
        shopImportDetailRepository.findDistinctByShopId(shopId)
                .stream().collect(Collectors.groupingBy(p -> p.getSupplyId()))
                .values()
                .forEach(p -> list.add(p.get(0)));
        List<SupplyDto> output = list.stream()
                .map(source -> modelMapper.map(source.getSupply(), SupplyDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<ShopImportDetail> spec = ShopImportDetailSpec.filterBy(f);
        Page<ShopImportDetail> page = shopImportDetailRepository.findAll(spec, pageable);

        List<ShopImportDetailDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopImportDetailDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopImportDetailDto getById(Long id) {
        Optional<ShopImportDetail> data = shopImportDetailRepository.findById(id);
        if (data.isPresent()) {
            ShopImportDetailDto output = modelMapper.map(data.get(), ShopImportDetailDto.class);
            return output;
        }
        return null;
    }

    //    @CacheEvict(value = "shopImportDetails", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        shopImportDetailRepository.deleteById(id);
    }

    //    @CacheEvict(value = "shopImportDetails", allEntries = true)
    @Override
    @Transactional
    public ShopImportDetailDto createOrEdit(ShopImportDetailInputDto input) {
        ShopImportDetailDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopImportDetailDto create(ShopImportDetailInputDto input) {
        ShopImportDetail temp = modelMapper.map(input, ShopImportDetail.class);
        ShopImportDetail data = shopImportDetailRepository.save(temp);
        return modelMapper.map(shopImportDetailRepository.save(data), ShopImportDetailDto.class);
    }

    private ShopImportDetailDto edit(ShopImportDetailInputDto input) {
        ShopImportDetail data = shopImportDetailRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ShopImportDetailDto.class);
    }
}
