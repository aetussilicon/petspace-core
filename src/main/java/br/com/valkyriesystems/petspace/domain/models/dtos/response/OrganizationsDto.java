package br.com.valkyriesystems.petspace.domain.models.dtos.response;

import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.ports.interfaces.ResponseType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.Organizations}
 */
public record OrganizationsDto(UUID ongId, String ongName, String taxNumber, String description, String website,
                               String email, String password, String contactEmail, String contactPhone,
                               Map<String, String> socialMediaHandles, Boolean verified, Address location, String workingHours,
                               String donationInfo, Timestamp createdAt, Timestamp updatedAt) implements Serializable, ResponseType {
}