package br.com.valkyriesystems.petspace.domain.ports.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreatedOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.OrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.ports.interfaces.DefaultCRUDInterface;

public interface OrganizationsService extends DefaultCRUDInterface<OrganizationsDto, CreatedOrganizationsDto, UpdateOrganizationsDto> {
}
