package br.com.valkyriesystems.petspace.domain.ports.interfaces;

public interface DefaultCRUDInterface<
        T extends ResponseType,
        R extends CreateDtoType,
        P extends UpdateDtoType> {

    T create(R createDto);
    T update(P updateDto, String id);
    T listById(String id);
    void delete(String id);
}
