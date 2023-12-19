package com.desafioestagio.Projeto_Estagio;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProjetoEstagioApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(ProjetoEstagioApplication.class).run(args);
	}

}
