package com.desafioestagio.Projeto_Estagio.Repositorys;

import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepositorys extends JpaRepository<Endereco,Long> {

}
