package vn.com.kns.portalapi.application.service.shop.shop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopInputDto;
import vn.com.kns.portalapi.core.entity.shop.Shop;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ShopRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "shops")
    @Override
    public List<ShopDto> getAll() {
        List<Shop> list = new ArrayList<>();
        shopRepository.findAll().forEach(list::add);
        List<ShopDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Shop> spec = ShopSpec.filterBy(f);
        Page<Shop> page = shopRepository.findAll(spec, pageable);

        List<ShopDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopDto getById(Long id) {
        Optional<Shop> data = shopRepository.findById(id);
        if (data.isPresent()) {
            ShopDto output = modelMapper.map(data.get(), ShopDto.class);
            return output;
        }
        return null;
    }

    @Cacheable(value = "shop")
    @Override
    public ShopDto getByCode(String input) {
        Optional<Shop> data = shopRepository.findByCode(input);
        if (data.isPresent()) {
            ShopDto output = modelMapper.map(data.get(), ShopDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "shops", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }

    @CacheEvict(value = "shops", allEntries = true)
    @Override
    @Transactional
    public ShopDto createOrEdit(ShopInputDto input) {
        ShopDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopDto create(ShopInputDto input) {
        Shop temp = modelMapper.map(input, Shop.class);
        temp.setCode(generateCode());
        Shop data = shopRepository.save(temp);
        temp.getContacts().stream().forEach(p -> p.setShopId(temp.getId()));
        return modelMapper.map(data, ShopDto.class);
    }

    private ShopDto edit(ShopInputDto input) {
        Shop data = shopRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ShopDto.class);
    }

    private String generateCode() {
        String prefix = "CH";
        String output = "";
        Long id = shopRepository.findTopByOrderByIdDesc().get().getId();
        String code = StringUtils.leftPad(String.valueOf(id + 1), 6, '0');
        output = prefix + code;
        return output;
    }

}
