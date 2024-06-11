package vn.com.kns.portalapi.application.service.shop.shopExportDetail;

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
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailInputDto;
import vn.com.kns.portalapi.core.entity.shop.ShopExportDetail;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.shop.ShopExportDetailRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopExportDetailSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopExportDetailServiceImpl implements ShopExportDetailService {

    @Autowired
    private ShopExportDetailRepository shopExportDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "shopExportDetails")
    @Override
    public List<ShopExportDetailDto> getAll() {
        List<ShopExportDetail> list = new ArrayList<>();
        shopExportDetailRepository.findAll().forEach(list::add);
        List<ShopExportDetailDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopExportDetailDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<ShopExportDetail> spec = ShopExportDetailSpec.filterBy(f);
        Page<ShopExportDetail> page = shopExportDetailRepository.findAll(spec, pageable);

        List<ShopExportDetailDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopExportDetailDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopExportDetailDto getById(Long id) {
        Optional<ShopExportDetail> data = shopExportDetailRepository.findById(id);
        if (data.isPresent()) {
            ShopExportDetailDto output = modelMapper.map(data.get(), ShopExportDetailDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "shopExportDetails", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        shopExportDetailRepository.deleteById(id);
    }

    @CacheEvict(value = "shopExportDetails", allEntries = true)
    @Override
    @Transactional
    public ShopExportDetailDto createOrEdit(ShopExportDetailInputDto input) {
        ShopExportDetailDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopExportDetailDto create(ShopExportDetailInputDto input) {
        ShopExportDetail temp = modelMapper.map(input, ShopExportDetail.class);
        ShopExportDetail data = shopExportDetailRepository.save(temp);
        return modelMapper.map(shopExportDetailRepository.save(data), ShopExportDetailDto.class);
    }

    private ShopExportDetailDto edit(ShopExportDetailInputDto input) {
        ShopExportDetail data = shopExportDetailRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ShopExportDetailDto.class);
    }
}
