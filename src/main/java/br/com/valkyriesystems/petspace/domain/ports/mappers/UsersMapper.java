package br.com.valkyriesystems.petspace.domain.ports.mappers;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Users;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class}, imports = {UUID.class})
public interface UsersMapper {

    @Mapping(target = "userId", expression = "java(UUID.randomUUID())")
    Users toEntity(CreateUsersDto createDto);

    UsersDto toDto(Users users);
    List<UsersDto> toDto(List<Users> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users partialUpdate(UpdateUsersDto updateDto, @MappingTarget Users users);
}