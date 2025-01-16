package br.com.valkyriesystems.petspace.domain.ports.repositories;

import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}