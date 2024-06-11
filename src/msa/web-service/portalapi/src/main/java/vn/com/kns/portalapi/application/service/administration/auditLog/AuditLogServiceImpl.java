package vn.com.kns.portalapi.application.service.administration.auditLog;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogDto;
import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogInputDto;
import vn.com.kns.portalapi.core.constant.HasRoleConst;
import vn.com.kns.portalapi.core.entity.app.AuditLog;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.app.AuditLogRepository;
import vn.com.kns.portalapi.data.specification.app.AuditLogSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AuditLogDto> getAll() {
        List<AuditLog> list = new ArrayList<>();
        auditLogRepository.findAll().forEach(list::add);
        List<AuditLogDto> output = list.stream()
                .map(source -> modelMapper.map(source, AuditLogDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<AuditLog> spec = AuditLogSpec.filterBy(f);
        Page<AuditLog> page = auditLogRepository.findAll(spec, pageable);

        List<AuditLogDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, AuditLogDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public AuditLogDto getById(Long id) {
        Optional<AuditLog> data = auditLogRepository.findById(id);
        if (data.isPresent()) {
            return modelMapper.map(data.get(), AuditLogDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        auditLogRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AuditLogDto createOrEdit(AuditLogInputDto input) {
        AuditLogDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private AuditLogDto create(AuditLogInputDto input) {
        AuditLog data = modelMapper.map(input, AuditLog.class);
        return modelMapper.map(auditLogRepository.save(data), AuditLogDto.class);
    }

    private AuditLogDto edit(AuditLogInputDto input) {
        AuditLog data = auditLogRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(auditLogRepository.save(data), AuditLogDto.class);
    }
}
