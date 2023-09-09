package com.judalabs.rinhabackend.config;

//@Configuration
//public class DatasourceConfig {
//
//    @Value("${spring.datasource.url}") String url;
//    @Value("${spring.datasource.username}") String username;
//    @Value("${spring.datasource.password}") String password;
//
//    @Bean
//    public DataSource datasource() {
//        final HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(username);
//        config.setPassword(password);
//
//        config.setConnectionTimeout(60000);
//        config.setMinimumIdle(50);
//        config.setMaximumPoolSize(300);
//        config.setDriverClassName("org.postgresql.Driver");
//
//        return new HikariDataSource(config);
//    }
//}
