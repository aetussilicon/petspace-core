package br.com.valkyriesystems.petspace.infra.factory;

import br.com.valkyriesystems.petspace.domain.Organizations;
import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreatedOrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.OrganizationsDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrganizationsTestFactory {

    public static Organizations create() {
        Address address = AddressTestFactory.create();
        Map<String, String> socialMediaHandles = new HashMap<>();
        socialMediaHandles.put("Facebook", "facebook_test");
        socialMediaHandles.put("Instagram", "insta_test");

        return new Organizations(
                UUID.randomUUID(),
                "ONG Example",
                "58469280000161",
                "Descrição da ONG",
                "https://ongexample.com",
                "ong@example.com",
                "password123",
                "contact@example.com",
                "1234567890",
                socialMediaHandles,
                false,
                address,
                "9h - 18h",
                "Doações para ONG Example",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }

    public static OrganizationsDto response() {
        Address address = AddressTestFactory.create();
        Map<String, String> socialMediaHandles = new HashMap<>();
        socialMediaHandles.put("Facebook", "facebook_test");
        socialMediaHandles.put("Instagram", "insta_test");

        return new OrganizationsDto(
                UUID.randomUUID(),
                "ONG Example",
                "58469280000161",
                "Descrição da ONG",
                "https://ongexample.com",
                "ong@example.com",
                "password123",
                "contact@example.com",
                "1234567890",
                socialMediaHandles,
                false,
                address,
                "9h - 18h",
                "Doações para ONG Example",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }

    public static CreatedOrganizationsDto createDto() {
        return new CreatedOrganizationsDto(
                "ONG Example",
                "58469280000161",
                "ong@example.com",
                "password123",
                AddressTestFactory.createDto()
        );
    }
}
