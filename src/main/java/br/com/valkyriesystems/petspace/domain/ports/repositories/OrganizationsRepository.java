package br.com.valkyriesystems.petspace.domain.ports.repositories;

import br.com.valkyriesystems.petspace.domain.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationsRepository extends JpaRepository<Organizations, UUID> {
    Optional<Organizations> findById(UUID uuid);
    Optional<Organizations> findByEmail(String email);
    Optional<Organizations> findByTaxNumber(String taxNumber);
}