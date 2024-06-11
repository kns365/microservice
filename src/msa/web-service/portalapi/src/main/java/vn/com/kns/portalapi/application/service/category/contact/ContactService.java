package vn.com.kns.portalapi.application.service.category.contact;

import vn.com.kns.portalapi.application.service.category.contact.dto.ContactDto;
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ContactService {

    List<ContactDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ContactDto getById(Long id);

    void deleteById(Long id);

    ContactDto createOrEdit(ContactInputDto input);

}
