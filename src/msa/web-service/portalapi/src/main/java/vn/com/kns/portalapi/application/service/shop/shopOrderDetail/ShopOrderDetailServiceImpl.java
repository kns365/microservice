package vn.com.kns.portalapi.application.service.shop.shopOrderDetail;

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
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailInputDto;
import vn.com.kns.portalapi.core.entity.shop.ShopOrderDetail;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ShopOrderDetailRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopOrderDetailSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopOrderDetailServiceImpl implements ShopOrderDetailService {

    @Autowired
    private ShopOrderDetailRepository shopOrderDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "shopOrderDetails")
    @Override
    public List<ShopOrderDetailDto> getAll() {
        List<ShopOrderDetail> list = new ArrayList<>();
        shopOrderDetailRepository.findAll().forEach(list::add);
        List<ShopOrderDetailDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopOrderDetailDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<ShopOrderDetail> spec = ShopOrderDetailSpec.filterBy(f);
        Page<ShopOrderDetail> page = shopOrderDetailRepository.findAll(spec, pageable);

        List<ShopOrderDetailDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopOrderDetailDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopOrderDetailDto getById(Long id) {
        Optional<ShopOrderDetail> data = shopOrderDetailRepository.findById(id);
        if (data.isPresent()) {
            ShopOrderDetailDto output = modelMapper.map(data.get(), ShopOrderDetailDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "shopOrderDetails", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        shopOrderDetailRepository.deleteById(id);
    }

    @CacheEvict(value = "shopOrderDetails", allEntries = true)
    @Override
    @Transactional
    public ShopOrderDetailDto createOrEdit(ShopOrderDetailInputDto input) {
        ShopOrderDetailDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopOrderDetailDto create(ShopOrderDetailInputDto input) {
        ShopOrderDetail temp = modelMapper.map(input, ShopOrderDetail.class);
        ShopOrderDetail data = shopOrderDetailRepository.save(temp);
        return modelMapper.map(shopOrderDetailRepository.save(data), ShopOrderDetailDto.class);
    }

    private ShopOrderDetailDto edit(ShopOrderDetailInputDto input) {
        ShopOrderDetail data = shopOrderDetailRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ShopOrderDetailDto.class);
    }
}
