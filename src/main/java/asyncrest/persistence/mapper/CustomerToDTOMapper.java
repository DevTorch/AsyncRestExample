package asyncrest.persistence.mapper;

import asyncrest.persistence.Customer;
import asyncrest.persistence.dto.CustomerRequestDto;
import asyncrest.persistence.dto.CustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerToDTOMapper {

    CustomerResponseDto toResponseDto(Customer customer);

    List<CustomerResponseDto> toResponseDtoList(List<Customer> customers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    Customer requestToEntity(CustomerRequestDto customerRequestDto);

    List<Customer> requestToEntityList(List<CustomerRequestDto> customerRequestDtoList);
}
