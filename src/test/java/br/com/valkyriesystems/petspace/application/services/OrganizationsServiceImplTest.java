package br.com.valkyriesystems.petspace.application.services;

import br.com.valkyriesystems.petspace.domain.Organizations;
import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreatedOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.OrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.ports.mappers.OrganizationsMapper;
import br.com.valkyriesystems.petspace.domain.ports.repositories.OrganizationsRepository;
import br.com.valkyriesystems.petspace.domain.ports.services.AddressService;
import br.com.valkyriesystems.petspace.infra.factory.OrganizationsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationsServiceImplTest {

    @Mock
    private OrganizationsRepository organizationsRepository;

    @Mock
    private OrganizationsMapper organizationsMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private OrganizationsServiceImpl organizationsService;

    private CreatedOrganizationsDto createDto;
    private UpdateOrganizationsDto updateDto;
    private Organizations existingOrganization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createDto = OrganizationsTestFactory.createDto();
        updateDto = new UpdateOrganizationsDto(
                "Updated ONG",
                "58469280000162",
                "Updated Description",
                "https://updated-ong.com",
                "updated@example.com",
                "updatedPassword",
                "contact@updated.com",
                "9876543210",
                new HashMap<>(Map.of("Facebook", "facebook_updated")),
                true,
                null,
                "10h - 20h",
                "Updated donation info"
        );
        existingOrganization = OrganizationsTestFactory.create();

    }

    @Test
    void create_success() {
        Organizations organizationEntity = OrganizationsTestFactory.create();
        OrganizationsDto expectedDto = OrganizationsTestFactory.response();

        when(organizationsRepository.findByEmail(createDto.email())).thenReturn(java.util.Optional.empty());
        when(organizationsRepository.findByTaxNumber(createDto.taxNumber())).thenReturn(java.util.Optional.empty());
        when(organizationsMapper.toEntity(createDto)).thenReturn(organizationEntity);
        when(addressService.create(createDto.location())).thenReturn(organizationEntity.getLocation());
        when(organizationsRepository.save(organizationEntity)).thenReturn(organizationEntity);
        when(organizationsMapper.toDto(organizationEntity)).thenReturn(expectedDto);

        OrganizationsDto result = organizationsService.create(createDto);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(organizationsRepository).findByEmail(createDto.email());
        verify(organizationsRepository).findByTaxNumber(createDto.taxNumber());
        verify(organizationsRepository).save(organizationEntity);
        verify(organizationsMapper).toDto(organizationEntity);
    }

    @Test
    void create_emailAlreadyInUse() {
        when(organizationsRepository.findByEmail(createDto.email())).thenReturn(java.util.Optional.of(OrganizationsTestFactory.create()));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            organizationsService.create(createDto);
        });

        assertEquals("Email already in use", thrown.getMessage());

        verify(organizationsRepository).findByEmail(createDto.email());
        verify(organizationsRepository, never()).findByTaxNumber(createDto.taxNumber());
        verify(organizationsRepository, never()).save(any());
        verify(organizationsMapper, never()).toDto(any(Organizations.class));
    }

    @Test
    void create_cnpjAlredyInUse() {
        
        when(organizationsRepository.findByEmail(createDto.email())).thenReturn(java.util.Optional.empty());
        when(organizationsRepository.findByTaxNumber(createDto.taxNumber())).thenReturn(java.util.Optional.of(OrganizationsTestFactory.create()));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            organizationsService.create(createDto);
        });

        assertEquals("CNPJ already in use", thrown.getMessage());

        verify(organizationsRepository).findByEmail(createDto.email());
        verify(organizationsRepository).findByTaxNumber(createDto.taxNumber());
        verify(organizationsRepository, never()).save(any());
        verify(organizationsMapper, never()).toDto(any(Organizations.class));
    }

    @Test
    void shouldUpdateOrganizationSuccessfully() {
        
        Organizations updatedOrganization = new Organizations(
                UUID.randomUUID(),
                "Updated ONG",
                "58469280000162",
                "Updated Description",
                "https://updated-ong.com",
                "updated@example.com",
                "updatedPassword",
                "contact@updated.com",
                "9876543210",
                Map.of("Facebook", "facebook_updated"),
                true,
                existingOrganization.getLocation(),
                "10h - 20h",
                "Updated donation info",
                existingOrganization.getCreatedAt(),
                existingOrganization.getUpdatedAt()
        );
        OrganizationsDto expectedDto = new OrganizationsDto(
                updatedOrganization.getOngId(),
                updatedOrganization.getOngName(),
                updatedOrganization.getTaxNumber(),
                updatedOrganization.getDescription(),
                updatedOrganization.getWebsite(),
                updatedOrganization.getEmail(),
                updatedOrganization.getPassword(),
                updatedOrganization.getContactEmail(),
                updatedOrganization.getContactPhone(),
                updatedOrganization.getSocialMediaHandles(),
                updatedOrganization.getVerified(),
                updatedOrganization.getLocation(),
                updatedOrganization.getWorkingHours(),
                updatedOrganization.getDonationInfo(),
                updatedOrganization.getCreatedAt(),
                updatedOrganization.getUpdatedAt()
        );

        when(organizationsRepository.findById(UUID.fromString(existingOrganization.getOngId().toString()))).thenReturn(java.util.Optional.of(existingOrganization));
        when(organizationsMapper.partialUpdate(updateDto, existingOrganization)).thenReturn(updatedOrganization);
        when(organizationsRepository.save(updatedOrganization)).thenReturn(updatedOrganization);
        when(organizationsMapper.toDto(updatedOrganization)).thenReturn(expectedDto);

        OrganizationsDto result = organizationsService.update(updateDto, existingOrganization.getOngId().toString());

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(organizationsRepository).findById(UUID.fromString(existingOrganization.getOngId().toString()));
        verify(organizationsRepository).save(updatedOrganization);
        verify(organizationsMapper).toDto(updatedOrganization);
        verify(organizationsMapper).partialUpdate(updateDto, existingOrganization);
    }

    @Test
    void shouldThrowExceptionIfOrganizationNotFound() {
        String nonExistentId = UUID.randomUUID().toString();

        when(organizationsRepository.findById(UUID.fromString(nonExistentId))).thenReturn(java.util.Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            organizationsService.update(updateDto, nonExistentId);
        });

        assertEquals("Organization not found", thrown.getMessage());

        verify(organizationsRepository).findById(UUID.fromString(nonExistentId));
        verify(organizationsRepository, never()).save(any());
        verify(organizationsMapper, never()).toDto(any(Organizations.class));
        verify(organizationsMapper, never()).partialUpdate(any(), any());
    }

    @Test
    void shouldNotUpdateSocialMediaHandlesIfNotProvided() {
        UpdateOrganizationsDto dtoWithoutSocialMedia = new UpdateOrganizationsDto(
                "Updated ONG",
                "58469280000162",
                "Updated Description",
                "https://updated-ong.com",
                "updated@example.com",
                "updatedPassword",
                "contact@updated.com",
                "9876543210",
                null,
                true,
                null,
                "10h - 20h",
                "Updated donation info"
        );

        Organizations updatedOrganization = new Organizations(
                UUID.randomUUID(),
                "Updated ONG",
                "58469280000162",
                "Updated Description",
                "https://updated-ong.com",
                "updated@example.com",
                "updatedPassword",
                "contact@updated.com",
                "9876543210",
                existingOrganization.getSocialMediaHandles(),
                true,
                existingOrganization.getLocation(),
                "10h - 20h",
                "Updated donation info",
                existingOrganization.getCreatedAt(),
                existingOrganization.getUpdatedAt()
        );

        OrganizationsDto expectedDto = new OrganizationsDto(
                updatedOrganization.getOngId(),
                updatedOrganization.getOngName(),
                updatedOrganization.getTaxNumber(),
                updatedOrganization.getDescription(),
                updatedOrganization.getWebsite(),
                updatedOrganization.getEmail(),
                updatedOrganization.getPassword(),
                updatedOrganization.getContactEmail(),
                updatedOrganization.getContactPhone(),
                updatedOrganization.getSocialMediaHandles(),
                updatedOrganization.getVerified(),
                updatedOrganization.getLocation(),
                updatedOrganization.getWorkingHours(),
                updatedOrganization.getDonationInfo(),
                updatedOrganization.getCreatedAt(),
                updatedOrganization.getUpdatedAt()
        );

        when(organizationsRepository.findById(UUID.fromString(existingOrganization.getOngId().toString()))).thenReturn(java.util.Optional.of(existingOrganization));
        when(organizationsMapper.partialUpdate(dtoWithoutSocialMedia, existingOrganization)).thenReturn(updatedOrganization);
        when(organizationsRepository.save(updatedOrganization)).thenReturn(updatedOrganization);
        when(organizationsMapper.toDto(updatedOrganization)).thenReturn(expectedDto);

        OrganizationsDto result = organizationsService.update(dtoWithoutSocialMedia, existingOrganization.getOngId().toString());

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(organizationsRepository).findById(UUID.fromString(existingOrganization.getOngId().toString()));
        verify(organizationsRepository).save(updatedOrganization);
        verify(organizationsMapper).toDto(updatedOrganization);
        verify(organizationsMapper).partialUpdate(dtoWithoutSocialMedia, existingOrganization);
    }

    @Test
    void shouldUpdateAddressSuccessfully() {
        UpdateAddressDto newAddressDto = new UpdateAddressDto(
                "New Country", "City", "State",
                "Distrcit", "Street", "123",
                null, "09876543");

        UpdateOrganizationsDto dtoWithNewAddress = new UpdateOrganizationsDto(
                "Updated ONG",
                "58469280000162",
                "Updated Description",
                "https://updated-ong.com",
                "updated@example.com",
                "updatedPassword",
                "contact@updated.com",
                "9876543210",
                new HashMap<>(Map.of("Facebook", "facebook_updated")),
                true,
                newAddressDto,
                "10h - 20h",
                "Updated donation info"
        );

        Organizations updatedOrganization = new Organizations(
                UUID.randomUUID(),
                "Updated ONG",
                "58469280000162",
                "Updated Description",
                "https://updated-ong.com",
                "updated@example.com",
                "updatedPassword",
                "contact@updated.com",
                "9876543210",
                dtoWithNewAddress.socialMediaHandles(),
                true,
                new Address(
                        1L,
                        "New Country",
                        "City",
                        "State",
                        "Distrcit",
                        "Street",
                        "123",
                        null,
                        "09876543",
                        null,
                        null
                ),
                "10h - 20h",
                "Updated donation info",
                existingOrganization.getCreatedAt(),
                existingOrganization.getUpdatedAt()
        );

        OrganizationsDto expectedDto = new OrganizationsDto(
                updatedOrganization.getOngId(),
                updatedOrganization.getOngName(),
                updatedOrganization.getTaxNumber(),
                updatedOrganization.getDescription(),
                updatedOrganization.getWebsite(),
                updatedOrganization.getEmail(),
                updatedOrganization.getPassword(),
                updatedOrganization.getContactEmail(),
                updatedOrganization.getContactPhone(),
                updatedOrganization.getSocialMediaHandles(),
                updatedOrganization.getVerified(),
                updatedOrganization.getLocation(),
                updatedOrganization.getWorkingHours(),
                updatedOrganization.getDonationInfo(),
                updatedOrganization.getCreatedAt(),
                updatedOrganization.getUpdatedAt()
        );

        when(organizationsRepository.findById(UUID.fromString(existingOrganization.getOngId().toString()))).thenReturn(java.util.Optional.of(existingOrganization));
        when(organizationsMapper.partialUpdate(dtoWithNewAddress, existingOrganization)).thenReturn(updatedOrganization);
        when(organizationsRepository.save(updatedOrganization)).thenReturn(updatedOrganization);
        when(organizationsMapper.toDto(updatedOrganization)).thenReturn(expectedDto);

        OrganizationsDto result = organizationsService.update(dtoWithNewAddress, existingOrganization.getOngId().toString());

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(organizationsRepository).findById(UUID.fromString(existingOrganization.getOngId().toString()));
        verify(organizationsRepository).save(updatedOrganization);
        verify(organizationsMapper).toDto(updatedOrganization);
        verify(organizationsMapper).partialUpdate(dtoWithNewAddress, existingOrganization);
    }

    @Test
    void shouldThrowExceptionIfMappingFails() {
        
        when(organizationsRepository.findById(UUID.fromString(existingOrganization.getOngId().toString()))).thenReturn(java.util.Optional.of(existingOrganization));
        when(organizationsMapper.partialUpdate(updateDto, existingOrganization)).thenThrow(new RuntimeException("Mapping failed"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            organizationsService.update(updateDto, existingOrganization.getOngId().toString());
        });

        assertEquals("Mapping failed", thrown.getMessage());

        verify(organizationsRepository).findById(UUID.fromString(existingOrganization.getOngId().toString()));
        verify(organizationsMapper).partialUpdate(updateDto, existingOrganization);
        verify(organizationsRepository, never()).save(any());
        verify(organizationsMapper, never()).toDto(any(Organizations.class));
    }


}
