package br.com.valkyriesystems.petspace.domain.models.dtos.update;

import br.com.valkyriesystems.petspace.domain.ports.interfaces.UpdateDtoType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Map;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.Organizations}
 */
public record UpdateOrganizationsDto(String ongName, @Size(max = 14) String taxNumber, String description,
                                     @URL String website, @Email String email, String password, String contactEmail,
                                     String contactPhone, Map<String, String> socialMediaHandles, Boolean verified,
                                     UpdateAddressDto location, String workingHours,
                                     String donationInfo) implements Serializable, UpdateDtoType {
}