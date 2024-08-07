package asyncrest.controller;

import asyncrest.persistence.dto.CustomerRequestDto;
import asyncrest.persistence.dto.CustomerResponseDto;
import asyncrest.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/v2/customers")
@RequiredArgsConstructor
public class CustomerAsyncRestController {

    private final CustomerService customerService;

    @GetMapping(produces = "application/json")
    public CompletableFuture<ResponseEntity<List<CustomerResponseDto>>> getAll() {
        return customerService.findAll()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    log.error("Error", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    ///v2/customers/page?page=21&size=10
    @GetMapping(value = "/page", produces = "application/json")
    public Page<CustomerResponseDto> getList(Pageable pageable) {
        return customerService.getPageableCustomerList(pageable);
    }

    @PostMapping(produces = "application/json")
    public CompletableFuture<ResponseEntity<List<CustomerResponseDto>>> saveFromApi(
            @RequestBody
            @Valid List<CustomerRequestDto> customers) {

        return customerService.saveAllFromApi(customers).thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    log.error("Error {}, {}", ex.getMessage(), ex.getClass().getName());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of());
                });
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMany(@RequestBody List<Long> ids) {
        try {
            customerService.deleteMany(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            customerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            customerService.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
