package vn.com.kns.portalapi.application.service.shop.shopExport;

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
import vn.com.kns.portalapi.application.service.shop.shopExport.dto.ShopExportDto;
import vn.com.kns.portalapi.application.service.shop.shopExport.dto.ShopExportInputDto;
import vn.com.kns.portalapi.core.entity.shop.ShopExport;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.shop.ShopExportRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopExportSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopExportServiceImpl implements ShopExportService {

    @Autowired
    private ShopExportRepository shopExportRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "shopExports")
    @Override
    public List<ShopExportDto> getAll() {
        List<ShopExport> list = new ArrayList<>();
        shopExportRepository.findAll().forEach(list::add);
        List<ShopExportDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopExportDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<ShopExport> spec = ShopExportSpec.filterBy(f);
        Page<ShopExport> page = shopExportRepository.findAll(spec, pageable);

        List<ShopExportDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopExportDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopExportDto getById(Long id) {
        Optional<ShopExport> data = shopExportRepository.findById(id);
        if (data.isPresent()) {
            ShopExportDto output = modelMapper.map(data.get(), ShopExportDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "shopExports", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<ShopExport> data = shopExportRepository.findById(id);
        if (data.isPresent()) {
            shopExportRepository.deleteById(id);
            NotifyHelper.sendNotify("deleted", "order", data.get().getCode(), "ShopExport.Delete");
        }
    }

    @CacheEvict(value = "shopExports", allEntries = true)
    @Override
    @Transactional
    public ShopExportDto createOrEdit(ShopExportInputDto input) {
        ShopExportDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopExportDto create(ShopExportInputDto input) {
        ShopExport temp = modelMapper.map(input, ShopExport.class);
        temp.setCode(generateCode());
        ShopExport data = shopExportRepository.save(temp);
        temp.getShopExportDetails().stream().forEach(p -> p.setShopExportId(temp.getId()));
//        NotifyHelper.sendNotify("created", "order", data.getCode(), "ShopExport.Create");
        return modelMapper.map(shopExportRepository.save(data), ShopExportDto.class);
    }

    private ShopExportDto edit(ShopExportInputDto input) {
        ShopExport data = shopExportRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
//        NotifyHelper.sendNotify("updated", "order", data.getCode(), "ShopExport.Create");
        return modelMapper.map(data, ShopExportDto.class);
    }

    private String generateCode() {
        String prefix = "PNH";
        String output = "";
//        Long id = shopExportRepository.findTopByOrderByIdDesc().get().getId();
        String id = DateHelper.getSystemDateStr(".yyyyMMdd.HHmmss.SSS");
        String code = StringUtils.leftPad(id, 20, '0');
        output = prefix + code;
        return output;
    }
}
