package vn.com.kns.portalapi.application.service.category.groupItem;

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
import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemDto;
import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemInputDto;
import vn.com.kns.portalapi.core.entity.other.GroupItem;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.GroupItemRepository;
import vn.com.kns.portalapi.data.specification.other.GroupItemSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupItemServiceImpl implements GroupItemService {

    @Autowired
    private GroupItemRepository groupItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "groupItems")
    @Override
    public List<GroupItemDto> getAll() {
        List<GroupItem> list = new ArrayList<>();
        groupItemRepository.findAll().forEach(list::add);
        List<GroupItemDto> output = list.stream()
                .map(source -> modelMapper.map(source, GroupItemDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<GroupItem> spec = GroupItemSpec.filterBy(f);
        Page<GroupItem> page = groupItemRepository.findAll(spec, pageable);

        List<GroupItemDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, GroupItemDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public GroupItemDto getById(Long id) {
        Optional<GroupItem> data = groupItemRepository.findById(id);
        if (data.isPresent()) {
            GroupItemDto output = modelMapper.map(data.get(), GroupItemDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "groupItems", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        groupItemRepository.deleteById(id);
    }

    @CacheEvict(value = "groupItems", allEntries = true)
    @Override
    @Transactional
    public GroupItemDto createOrEdit(GroupItemInputDto input) {
        GroupItemDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private GroupItemDto create(GroupItemInputDto input) {
        GroupItem data = modelMapper.map(input, GroupItem.class);
        return modelMapper.map(groupItemRepository.save(data), GroupItemDto.class);
    }

    private GroupItemDto edit(GroupItemInputDto input) {
        GroupItem data = groupItemRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, GroupItemDto.class);
    }
}
