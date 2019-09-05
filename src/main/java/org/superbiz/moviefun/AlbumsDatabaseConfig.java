package org.superbiz.moviefun;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class AlbumsDatabaseConfig {

    @Bean
    public DataSource albumsDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        HikariDataSource hikariDataSource = new HikariDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("albums-mysql"));
        hikariDataSource.setDataSource(dataSource);
        return hikariDataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBeanForAlbums(DataSource albumsDataSource , HibernateJpaVendorAdapter getHibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean  localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(albumsDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(getHibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.superbiz.moviefun");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("albums");
        return localContainerEntityManagerFactoryBean;


    }

    @Bean
    public PlatformTransactionManager getPlatformTransactionManagerforAlbums(EntityManagerFactory getLocalContainerEntityManagerFactoryBeanForAlbums){
        return new JpaTransactionManager(getLocalContainerEntityManagerFactoryBeanForAlbums);
    }
}
