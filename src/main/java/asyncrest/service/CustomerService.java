package asyncrest.service;

import asyncrest.persistence.dto.CustomerRequestDto;
import asyncrest.persistence.dto.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerService {

    CompletableFuture<List<CustomerResponseDto>> findAll();

    CompletableFuture<List<CustomerResponseDto>> saveAllFromApi(List<CustomerRequestDto> customers);

    Page<CustomerResponseDto> getPageableCustomerList(Pageable pageable);

    void deleteMany(List<Long> ids);

    void deleteById(Long id);

    void deleteAll();
}
