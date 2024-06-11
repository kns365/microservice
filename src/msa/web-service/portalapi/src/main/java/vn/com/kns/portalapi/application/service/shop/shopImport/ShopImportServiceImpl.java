package vn.com.kns.portalapi.application.service.shop.shopImport;

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
import vn.com.kns.portalapi.application.helper.DateHelper;
import vn.com.kns.portalapi.application.helper.NotifyHelper;
import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportDto;
import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportInputDto;
import vn.com.kns.portalapi.core.entity.shop.ShopImport;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ShopImportRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopImportSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopImportServiceImpl implements ShopImportService {

    @Autowired
    private ShopImportRepository shopImportRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "shopImports")
    @Override
    public List<ShopImportDto> getAll() {
        List<ShopImport> list = new ArrayList<>();
        shopImportRepository.findAll().forEach(list::add);
        List<ShopImportDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopImportDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<ShopImport> spec = ShopImportSpec.filterBy(f);
        Page<ShopImport> page = shopImportRepository.findAll(spec, pageable);

        List<ShopImportDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopImportDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopImportDto getById(Long id) {
        Optional<ShopImport> data = shopImportRepository.findById(id);
        if (data.isPresent()) {
            ShopImportDto output = modelMapper.map(data.get(), ShopImportDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "shopImports", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<ShopImport> data = shopImportRepository.findById(id);
        if (data.isPresent()) {
            shopImportRepository.deleteById(id);
            NotifyHelper.sendNotify("deleted", "order", data.get().getCode(), "ShopImport.Delete");
        }
    }

    @CacheEvict(value = "shopImports", allEntries = true)
    @Override
    @Transactional
    public ShopImportDto createOrEdit(ShopImportInputDto input) {
        ShopImportDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopImportDto create(ShopImportInputDto input) {
        ShopImport temp = modelMapper.map(input, ShopImport.class);
        temp.setCode(generateCode());
        ShopImport data = shopImportRepository.save(temp);
        temp.getShopImportDetails().stream().forEach(p -> p.setShopImportId(temp.getId()));
//        NotifyHelper.sendNotify("created", "order", data.getCode(), "ShopImport.Create");
        return modelMapper.map(shopImportRepository.save(data), ShopImportDto.class);
    }

    private ShopImportDto edit(ShopImportInputDto input) {
        ShopImport data = shopImportRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
//        NotifyHelper.sendNotify("updated", "order", data.getCode(), "ShopImport.Create");
        return modelMapper.map(data, ShopImportDto.class);
    }

    private String generateCode() {
        String prefix = "PNH";
        String output = "";
//        Long id = shopImportRepository.findTopByOrderByIdDesc().get().getId();
        String id = DateHelper.getSystemDateStr(".yyyyMMdd.HHmmss.SSS");
        String code = StringUtils.leftPad(id, 20, '0');
        output = prefix + code;
        return output;
    }
}
