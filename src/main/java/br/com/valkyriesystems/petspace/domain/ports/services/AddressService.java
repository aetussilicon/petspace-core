package br.com.valkyriesystems.petspace.domain.ports.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.ports.interfaces.DefaultCRUDInterface;

public interface AddressService extends DefaultCRUDInterface<Address, CreateAddressDto, UpdateAddressDto> {
}
