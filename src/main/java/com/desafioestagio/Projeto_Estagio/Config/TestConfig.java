package com.desafioestagio.Projeto_Estagio.Config;


import com.desafioestagio.Projeto_Estagio.Repositorys.EnderecoRepositorys;
import com.desafioestagio.Projeto_Estagio.Repositorys.MonitoradorRepositorys;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private MonitoradorRepositorys monitoradorRepositorys;
    @Autowired
    private EnderecoRepositorys enderecoRepositorys;

    @Override
    public void run(String... args) throws Exception {
       // Monitorador m1 = new Monitorador(null, "Fisica", "085.583.351-33", "Davi Faria", "davifariagoncalves@gmail.com", "7112849", Instant.parse("2003-11-16T02:50:03Z"), true);
//        Monitorador m2 = new Monitorador(null, "Juridica", "78.789.789/0001-07", "Pescaria do Lucas", null, "3215464", null, true);
//
//        monitoradorRepositorys.saveAll(Arrays.asList(m2));
//
//     //   Endereco end1 = new Endereco (null, "SEN JOSE LOURENCO DIAS","1440","75020-010","62999919116","anapolis","GO", true,m2);
//        Endereco end2 = new Endereco (null, "Av. Prof. Benvindo Machado","1620","7143-565","6298543813","anapolis","GO", true,m2);
//
//        enderecoRepositorys.saveAll(Arrays.asList(end2));
    }
}