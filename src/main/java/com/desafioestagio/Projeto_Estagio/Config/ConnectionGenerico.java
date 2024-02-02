package com.desafioestagio.Projeto_Estagio.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class ConnectionGenerico {

    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return  dataSource.getConnection();
    }

}