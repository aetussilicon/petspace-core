package br.com.valkyriesystems.petspace.domain.ports.interfaces;

import jakarta.annotation.Nullable;

import java.util.List;

public interface DefaultCRUDInterface<
        T extends ResponseType,
        R extends CreateDtoType,
        P extends UpdateDtoType> {

    T create(R createDto);
    T update(P updateDto, String id);
    T listById(String id);
    List<T> listAll(@Nullable String method, @Nullable String param);
    void delete(String id);
}
