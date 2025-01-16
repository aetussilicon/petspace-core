package br.com.valkyriesystems.petspace.domain.ports.controllers;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateUsersDto;
import br.com.valkyriesystems.petspace.domain.ports.interfaces.DefaultControllerInterface;

public interface UsersController extends DefaultControllerInterface<UsersDto, CreateUsersDto, UpdateUsersDto> {
}
