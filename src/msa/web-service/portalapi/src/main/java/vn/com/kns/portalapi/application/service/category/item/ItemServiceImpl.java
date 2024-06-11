package vn.com.kns.portalapi.application.service.category.item;

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
import vn.com.kns.portalapi.application.service.category.item.dto.ItemDto;
import vn.com.kns.portalapi.application.service.category.item.dto.ItemInputDto;
import vn.com.kns.portalapi.core.entity.other.Item;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ItemRepository;
import vn.com.kns.portalapi.data.specification.other.ItemSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "items")
    @Override
    public List<ItemDto> getAll(Long groupItemId) {
        List<Item> list = new ArrayList<>();
        if (groupItemId != null) {
            itemRepository.findAllByGroupItemId(groupItemId).forEach(list::add);
        } else {
            itemRepository.findAll().forEach(list::add);
        }
        List<ItemDto> output = list.stream()
                .map(source -> modelMapper.map(source, ItemDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Item> spec = ItemSpec.filterBy(f);
        Page<Item> page = itemRepository.findAll(spec, pageable);

        List<ItemDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ItemDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ItemDto getById(Long id) {
        Optional<Item> data = itemRepository.findById(id);
        if (data.isPresent()) {
            ItemDto output = modelMapper.map(data.get(), ItemDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "items", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @CacheEvict(value = "items", allEntries = true)
    @Override
    @Transactional
    public ItemDto createOrEdit(ItemInputDto input) {
        ItemDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ItemDto create(ItemInputDto input) {
        Item temp = modelMapper.map(input, Item.class);
        temp.setCode(generateCode());
        Item data = itemRepository.save(temp);
        return modelMapper.map(itemRepository.save(data), ItemDto.class);
    }

    private ItemDto edit(ItemInputDto input) {
        Item data = itemRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ItemDto.class);
    }

    private String generateCode() {
        String prefix = "SP";
        String output = "";
        Long id = itemRepository.findTopByOrderByIdDesc().get().getId();
        String code = StringUtils.leftPad(String.valueOf(id + 1), 6, '0');
        output = prefix + code;
        return output;
    }
}
