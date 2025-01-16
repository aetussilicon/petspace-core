package br.com.valkyriesystems.petspace.application.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.ports.mappers.AddressMapper;
import br.com.valkyriesystems.petspace.domain.ports.repositories.AddressRepository;
import br.com.valkyriesystems.petspace.domain.ports.services.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public Address create(CreateAddressDto createDto) {
        Address address = addressMapper.toEntity(createDto);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address update(UpdateAddressDto updateDto, String id) {
        Address address = addressRepository.findById(Long.valueOf(id)).orElseThrow(RuntimeException::new);
        address = addressMapper.partialUpdate(updateDto, address);
        return addressRepository.save(address);
    }

    @Override
    public Address listById(String id) {
        throw new UnsupportedOperationException("Not supported operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported operation!"); // TODO - Implement delete method
    }
}
