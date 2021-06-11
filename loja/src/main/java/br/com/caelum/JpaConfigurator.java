package br.com.caelum;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfigurator {

	@Bean
	public DataSource getDataSource() throws PropertyVetoException {
		// Abaixo, temos o DataSource do JDBC padrao, que devolve apenas uma conexao
		// com o bando de dados

//	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//	    dataSource.setUrl("jdbc:mysql://localhost:3306/loja_jpa?createDatabaseIfNotExist=true&serverTimezone=UTC");
//	    dataSource.setUsername("developer");
//	    dataSource.setPassword("dev123");

		// Vamos usar, agora, o DataSource do C3P0 para fazer um Connectio Pool

		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/loja_jpa?createDatabaseIfNotExist=true&serverTimezone=UTC");
		dataSource.setUser("developer");
		dataSource.setPassword("dev123");
		
		dataSource.setMinPoolSize(3);
		dataSource.setMaxPoolSize(5);
		dataSource.setNumHelperThreads(15);
		
		dataSource.setIdleConnectionTestPeriod(1); //a cada um segundo testamos as conexões ociosas

		return dataSource;
	}
	
	// O metodo abaixo e' para usara o statistics que o HIBERNATE oferece
	@Bean
	public Statistics statistics(EntityManagerFactory emf) {
		SessionFactory factory = emf.unwrap(SessionFactory.class);
		return factory.getStatistics();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setPackagesToScan("br.com.caelum");
		entityManagerFactory.setDataSource(dataSource);

		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties props = new Properties();

		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		
		// habilita cache de segundo nível
		props.setProperty("hibernate.cache.use_second_level_cache", "true");
		
		// habilita cache de segundo nível para queries (Opcional! Habilitar somente se quiser cache de query)
		props.setProperty("hibernate.cache.use_query_cache", "true");
		
		// indica qual o provider de cache de segundo nível
		props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory"); 
		
		// habilita a classe de statistics do hibernate
		props.setProperty("hibernate.generate_statistics", "true"); 

		entityManagerFactory.setJpaProperties(props);
		return entityManagerFactory;
	}

	@Bean
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

}
