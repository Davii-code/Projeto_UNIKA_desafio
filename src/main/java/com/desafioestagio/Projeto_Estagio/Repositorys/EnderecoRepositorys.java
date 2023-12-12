package com.desafioestagio.Projeto_Estagio.Repositorys;

import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepositorys extends JpaRepository<Endereco,Long> {


    boolean existsByCep(String obj);
    boolean existsByPrincipal(boolean obj);

    @Query(value = "SELECT monitorador_id FROM tb_endereco WHERE id = ?", nativeQuery = true)
    Long VerificaMonitorador(Long id);

    @Query(value = "SELECT id FROM tb_endereco WHERE monitorador_id = ?", nativeQuery = true)
    List<Long> ValidadorEstrangeiro(Long id);
    @Query(value = "SELECT id FROM tb_endereco WHERE monitorador_id = ?", nativeQuery = true)
    List<Long> deleteEnderecoByIdIn (Long id);


}
