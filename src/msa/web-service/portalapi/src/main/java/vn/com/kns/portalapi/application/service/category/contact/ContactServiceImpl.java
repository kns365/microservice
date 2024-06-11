package vn.com.kns.portalapi.application.service.category.contact;

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
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactDto;
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactInputDto;
import vn.com.kns.portalapi.core.entity.other.Contact;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ContactRepository;
import vn.com.kns.portalapi.data.specification.other.ContactSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "contacts")
    @Override
    public List<ContactDto> getAll() {
        List<Contact> list = new ArrayList<>();
        contactRepository.findAll().forEach(list::add);
        List<ContactDto> output = list.stream()
                .map(source -> modelMapper.map(source, ContactDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Contact> spec = ContactSpec.filterBy(f);
        Page<Contact> page = contactRepository.findAll(spec, pageable);

        List<ContactDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ContactDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ContactDto getById(Long id) {
        Optional<Contact> data = contactRepository.findById(id);
        if (data.isPresent()) {
            ContactDto output = modelMapper.map(data.get(), ContactDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "contacts", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    @CacheEvict(value = "contacts", allEntries = true)
    @Override
    @Transactional
    public ContactDto createOrEdit(ContactInputDto input) {
        ContactDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ContactDto create(ContactInputDto input) {
        Contact data = modelMapper.map(input, Contact.class);
        return modelMapper.map(contactRepository.save(data), ContactDto.class);
    }

    private ContactDto edit(ContactInputDto input) {
        Contact data = contactRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ContactDto.class);
    }
}
