package com.ufape.agiota.dados.repository;

import com.ufape.agiota.negocio.models.Agiota;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AgiotaRepository extends JpaRepository<Agiota, Long>{
    Agiota findByIdKc(String idKc);
    
}
