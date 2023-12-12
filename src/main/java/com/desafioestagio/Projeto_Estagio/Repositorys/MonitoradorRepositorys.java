package com.desafioestagio.Projeto_Estagio.Repositorys;

import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoradorRepositorys extends JpaRepository<Monitorador,Long> {

    Monitorador findTopByOrderByIdDesc();
    boolean existsByCnpj(String cnpj);

    boolean existsByCpf(String cpf);
    boolean existsByRg(String Rg);
    boolean existsByInscricao(String inscricao);


}
