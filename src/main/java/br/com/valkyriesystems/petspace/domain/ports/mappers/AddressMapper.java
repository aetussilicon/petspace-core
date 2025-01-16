package br.com.valkyriesystems.petspace.domain.ports.mappers;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.AddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address toEntity(CreateAddressDto createDto);

    AddressDto toDto(Address address);

    List<AddressDto> toDto(List<Address> address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(UpdateAddressDto updateDto, @MappingTarget Address address);
}