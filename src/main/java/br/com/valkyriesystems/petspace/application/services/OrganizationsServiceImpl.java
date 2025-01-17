package br.com.valkyriesystems.petspace.application.services;

import br.com.valkyriesystems.petspace.domain.Organizations;
import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreatedOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.OrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.ports.mappers.OrganizationsMapper;
import br.com.valkyriesystems.petspace.domain.ports.repositories.OrganizationsRepository;
import br.com.valkyriesystems.petspace.domain.ports.services.AddressService;
import br.com.valkyriesystems.petspace.domain.ports.services.OrganizationsService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationsServiceImpl implements OrganizationsService {
    private final OrganizationsRepository organizationsRepository;
    private final OrganizationsMapper organizationsMapper;
    private final AddressService addressService;

    @Override
    public OrganizationsDto create(CreatedOrganizationsDto createDto) {
        organizationsRepository.findByEmail(createDto.email()).ifPresent(organization -> {
            throw new IllegalArgumentException("Email already in use");
        });

        organizationsRepository.findByTaxNumber(createDto.taxNumber()).ifPresent(organization -> {
            throw new IllegalArgumentException("CNPJ already in use");
        });

        Organizations ong = organizationsMapper.toEntity(createDto);
        Address location = addressService.create(createDto.location());
        ong.setLocation(location);
        ong = organizationsRepository.save(ong);
        return organizationsMapper.toDto(ong);
    }

    @Override
    public OrganizationsDto update(UpdateOrganizationsDto updateDto, String id) {
        Organizations toUpdate = organizationsRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Organization not found"));
        toUpdate = organizationsMapper.partialUpdate(updateDto, toUpdate);

        if (updateDto.socialMediaHandles() != null && !updateDto.socialMediaHandles().isEmpty()) {
            Map<String, String> existingSocialMedia = toUpdate.getSocialMediaHandles();

            updateDto.socialMediaHandles().forEach(existingSocialMedia::putIfAbsent);

            toUpdate.setSocialMediaHandles(existingSocialMedia);
        }


        toUpdate = organizationsRepository.save(toUpdate);
        return organizationsMapper.toDto(toUpdate);
    }

    @Override
    public OrganizationsDto listById(String id) {
        return null;
    }

    @Override
    public List<OrganizationsDto> listAll(@Nullable String method, @Nullable String param) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }
}
