package vn.com.kns.portalapi.application.service.supplier;

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
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierInputDto;
import vn.com.kns.portalapi.core.entity.other.Supplier;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.SupplierRepository;
import vn.com.kns.portalapi.data.specification.other.SupplierSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "suppliers")
    @Override
    public List<SupplierDto> getAll() {
        List<Supplier> list = new ArrayList<>();
        supplierRepository.findAll().forEach(list::add);
        List<SupplierDto> output = list.stream()
                .map(source -> modelMapper.map(source, SupplierDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Supplier> spec = SupplierSpec.filterBy(f);
        Page<Supplier> page = supplierRepository.findAll(spec, pageable);

        List<SupplierDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, SupplierDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public SupplierDto getById(Long id) {
        Optional<Supplier> data = supplierRepository.findById(id);
        if (data.isPresent()) {
            SupplierDto output = modelMapper.map(data.get(), SupplierDto.class);
            return output;
        }
        return null;
    }

    @Cacheable(value = "supplier")
    @Override
    public SupplierDto getByCode(String input) {
        Optional<Supplier> data = supplierRepository.findByCode(input);
        if (data.isPresent()) {
            SupplierDto output = modelMapper.map(data.get(), SupplierDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "suppliers", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    @CacheEvict(value = "suppliers", allEntries = true)
    @Override
    @Transactional
    public SupplierDto createOrEdit(SupplierInputDto input) {
        SupplierDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private SupplierDto create(SupplierInputDto input) {
        Supplier temp = modelMapper.map(input, Supplier.class);
        temp.setCode(generateCode());
        Supplier data = supplierRepository.save(temp);
        temp.getContacts().stream().forEach(p -> p.setSupplierId(temp.getId()));
        return modelMapper.map(data, SupplierDto.class);
    }

    private SupplierDto edit(SupplierInputDto input) {
        Supplier data = supplierRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, SupplierDto.class);
    }

    private String generateCode() {
        String prefix = "NCC";
        String output = "";
        Long id = supplierRepository.findTopByOrderByIdDesc().get().getId();
        String code = StringUtils.leftPad(String.valueOf(id + 1), 6, '0');
        output = prefix + code;
        return output;
    }

}
