package com.codegym.service;

import java.util.Optional;

public interface IGeneralService<C> {

    Iterable<C> findALl();

    Optional<C> findById(Long id);

    C save(C c);

    void remove(Long id);
}
