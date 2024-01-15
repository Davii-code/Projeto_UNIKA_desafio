package com.desafioestagio.Projeto_Estagio.Repositorys;

import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonitoradorRepositorys extends JpaRepository<Monitorador,Long> {

    Monitorador findTopByOrderByIdDesc();
    boolean existsByCnpj(String cnpj);

    boolean existsByCpf(String cpf);
    boolean existsByRg(String Rg);
    boolean existsByInscricao(String inscricao);

    boolean existsByEmail(String email);

    List<Monitorador> findByNomeStartingWith( String Name);

    List<Monitorador> findByNome( String Name);


    Monitorador findBycnpj(String cnpj);

    Monitorador findByCpf(String cpf);









}
