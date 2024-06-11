package vn.com.kns.portalapi.application.service.notify;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.application.service.notify.dto.NotifyDto;
import vn.com.kns.portalapi.application.service.notify.dto.NotifyInputDto;
import vn.com.kns.portalapi.core.entity.app.Notify;
import vn.com.kns.portalapi.core.entity.app.Notify_;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.app.NotifyRepository;
import vn.com.kns.portalapi.data.specification.app.NotifySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NotifyDto> getAll(String username, Integer max) {
        List<Notify> list = new ArrayList<>();
        if (Strings.isBlank(username)) {
            notifyRepository.findAll().forEach(list::add);
        } else {
            Sort sort = Sort.by(Notify_.CREATED_DATE).descending();
            Pageable pageable = PageRequest.of(0, max, sort);
            notifyRepository.findAllByUsername(pageable, username).forEach(list::add);
        }
        List<NotifyDto> output = list.stream()
                .map(source -> modelMapper.map(source, NotifyDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Notify> spec = NotifySpec.filterBy(f);
        Page<Notify> page = notifyRepository.findAll(spec, pageable);

        List<NotifyDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, NotifyDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public NotifyDto getById(Long id) {
        Optional<Notify> data = notifyRepository.findById(id);
        if (data.isPresent()) {
            NotifyDto output = modelMapper.map(data.get(), NotifyDto.class);
            return output;
        }
        return null;
    }

    @Override
    public NotifyDto readNotifyById(Long id) {
        Optional<Notify> data = notifyRepository.findById(id);
        if (data.isPresent()) {
            data.get().setState(true);
            notifyRepository.save(data.get());
            NotifyDto output = modelMapper.map(data.get(), NotifyDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "supplies", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        notifyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NotifyDto createOrEdit(NotifyInputDto input) {
        NotifyDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private NotifyDto create(NotifyInputDto input) {
        Notify temp = modelMapper.map(input, Notify.class);
        Notify data = notifyRepository.save(temp);
        return modelMapper.map(data, NotifyDto.class);
    }

    private NotifyDto edit(NotifyInputDto input) {
        Notify data = notifyRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, NotifyDto.class);
    }

    @Override
    @Transactional
    public NotifyDto sendNotify(NotifyInputDto input) {
        NotifyDto output;
        output = create(input);
        return output;
    }
}
