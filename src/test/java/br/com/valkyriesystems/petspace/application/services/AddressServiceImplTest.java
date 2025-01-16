package br.com.valkyriesystems.petspace.application.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.ports.mappers.AddressMapper;
import br.com.valkyriesystems.petspace.domain.ports.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private Address response;
    private CreateAddressDto createDto;
    private UpdateAddressDto updateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        address = new Address();
        address.setLocationId(1L);
        address.setCountry("Brasil");
        address.setState("São Paulo");
        address.setCity("São Paulo");
        address.setDistrict("Bairro");
        address.setStreet("Rua 10");
        address.setNumber("10");
        address.setComplement("Complemento");
        address.setZip("11111111");
        address.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        address.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void create_success() {
        createDto = new CreateAddressDto(
                "Brasil", "São Paulo" ,"São Paulo",
                "Bairro", "Rua 10", "10",
                "Complemento", "11111111"
        );

        when(addressMapper.toEntity(createDto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);

        response = addressService.create(createDto);

        assertNotNull(response);
        assertEquals(response, address);
    }

    @Test
    void update_success() {
        updateDto = new UpdateAddressDto(
                "USA", "Los Angeles", "California", "District",
                "Street", null, null, null
        );

        Address newAddress = new Address();
        newAddress.setCountry("USA");
        newAddress.setState("Los Angeles");
        newAddress.setCity("California");
        newAddress.setDistrict("District");
        newAddress.setStreet("Street");
        newAddress.setNumber("10");
        newAddress.setComplement("Complemento");
        newAddress.setZip("11111111");
        newAddress.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newAddress.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.partialUpdate(updateDto, address)).thenReturn(newAddress);
        when(addressRepository.save(newAddress)).thenReturn(newAddress);

        response = addressService.update(updateDto, "1");

        assertNotNull(response);
        assertEquals(response, newAddress);
    }
}