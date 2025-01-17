package br.com.valkyriesystems.petspace.domain.ports.mappers;

import br.com.valkyriesystems.petspace.domain.Organizations;
import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreatedOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.OrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateOrganizationsDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class}, imports = {UUID.class})
public interface OrganizationsMapper {

    @Mapping(target = "ongId", expression = "java(UUID.randomUUID())")
    Organizations toEntity(CreatedOrganizationsDto createDto);

    OrganizationsDto toDto(Organizations organizations);
    List<OrganizationsDto> toDto(List<Organizations> organizations);

    @Mapping(target = "socialMediaHandles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Organizations partialUpdate(UpdateOrganizationsDto updateDto, @MappingTarget Organizations organizations);
}