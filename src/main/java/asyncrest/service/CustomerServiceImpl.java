package asyncrest.service;

import asyncrest.persistence.Customer;
import asyncrest.persistence.dto.CustomerRequestDto;
import asyncrest.persistence.dto.CustomerResponseDto;
import asyncrest.persistence.mapper.CustomerToDTOMapper;
import asyncrest.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerToDTOMapper customerToDTOMapper;

    @Async
    @Override
    @Transactional
    public CompletableFuture<List<CustomerResponseDto>> saveAllFromApi(List<CustomerRequestDto> customers) {
        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        List<Customer> customersList = customerRepository.saveAll(customerToDTOMapper.requestToEntityList(customers));
        AtomicLong end = new AtomicLong(System.currentTimeMillis());
        log.info("Saved {} customers in {} msm Thread: {}", customersList.size(), end.get() - start.get(), Thread.currentThread().getName());
        return CompletableFuture.completedFuture(customerToDTOMapper.toResponseDtoList(customersList));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> getPageableCustomerList(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customerToDTOMapper::toResponseDto);
    }

    @Override
    public void deleteMany(List<Long> ids) {
        customerRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        customerRepository.deleteAll();
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<CustomerResponseDto>> findAll() {
        log.info("Find all customers in Future: {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(customerToDTOMapper.toResponseDtoList(customerRepository.findAll()));
    }
}