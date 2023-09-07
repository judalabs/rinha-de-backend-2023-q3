package com.judalabs.rinhabackend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatasourceConfig {

    @Value("${spring.datasource.url}") String url;
    @Value("${spring.datasource.username}") String username;
    @Value("${spring.datasource.password}") String password;

    @Bean
    public DataSource datasource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setConnectionTimeout(60000);
        config.setMinimumIdle(50);
        config.setMaximumPoolSize(300);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
