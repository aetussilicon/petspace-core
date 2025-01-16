package br.com.valkyriesystems.petspace.domain.ports.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateUsersDto;
import br.com.valkyriesystems.petspace.domain.ports.interfaces.DefaultCRUDInterface;

public interface UsersService extends DefaultCRUDInterface<UsersDto, CreateUsersDto, UpdateUsersDto> {
    UsersDto listByEmail(String email);
    UsersDto listByUsername(String username);
}