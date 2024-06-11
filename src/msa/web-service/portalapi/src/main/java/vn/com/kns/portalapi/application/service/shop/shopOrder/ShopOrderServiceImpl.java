package vn.com.kns.portalapi.application.service.shop.shopOrder;

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
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderDto;
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderInputDto;
import vn.com.kns.portalapi.core.entity.shop.ShopOrder;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ShopOrderRepository;
import vn.com.kns.portalapi.data.specification.shop.ShopOrderSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopOrderServiceImpl implements ShopOrderService {

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "shopOrders")
    @Override
    public List<ShopOrderDto> getAll() {
        List<ShopOrder> list = new ArrayList<>();
        shopOrderRepository.findAll().forEach(list::add);
        List<ShopOrderDto> output = list.stream()
                .map(source -> modelMapper.map(source, ShopOrderDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<ShopOrder> spec = ShopOrderSpec.filterBy(f);
        Page<ShopOrder> page = shopOrderRepository.findAll(spec, pageable);

        List<ShopOrderDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ShopOrderDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ShopOrderDto getById(Long id) {
        Optional<ShopOrder> data = shopOrderRepository.findById(id);
        if (data.isPresent()) {
            ShopOrderDto output = modelMapper.map(data.get(), ShopOrderDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "shopOrders", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<ShopOrder> data = shopOrderRepository.findById(id);
        if (data.isPresent()) {
            shopOrderRepository.deleteById(id);
            NotifyHelper.sendNotify("deleted", "order", data.get().getCode(), "ShopOrder.Delete");
        }
    }

    @CacheEvict(value = "shopOrders", allEntries = true)
    @Override
    @Transactional
    public ShopOrderDto createOrEdit(ShopOrderInputDto input) {
        ShopOrderDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ShopOrderDto create(ShopOrderInputDto input) {
        ShopOrder temp = modelMapper.map(input, ShopOrder.class);
        temp.setCode(generateCode());
        ShopOrder data = shopOrderRepository.save(temp);
        temp.getShopOrderDetails().stream().forEach(p -> p.setShopOrderId(temp.getId()));
        NotifyHelper.sendNotify("created", "order", data.getCode(), "ShopOrder.Create");
        return modelMapper.map(shopOrderRepository.save(data), ShopOrderDto.class);
    }

    private ShopOrderDto edit(ShopOrderInputDto input) {
        ShopOrder data = shopOrderRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        NotifyHelper.sendNotify("updated", "order", data.getCode(), "ShopOrder.Create");
        return modelMapper.map(data, ShopOrderDto.class);
    }

    private String generateCode() {
        String prefix = "PDH";
        String output = "";
//        Long id = shopOrderRepository.findTopByOrderByIdDesc().get().getId();
        String id = DateHelper.getSystemDateStr(".yyyyMMdd.HHmmss.SSS");
        String code = StringUtils.leftPad(id, 20, '0');
        output = prefix + code;
        return output;
    }
}
