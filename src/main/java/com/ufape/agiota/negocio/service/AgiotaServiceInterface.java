package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.Agiota;
import java.util.List;

public interface AgiotaServiceInterface {
    Agiota save(Agiota agiota);

    void delete(Long id);

    Agiota find(Long id);

    List<Agiota> findAll();

    Agiota update(Long id, Agiota agiota);
    
}
