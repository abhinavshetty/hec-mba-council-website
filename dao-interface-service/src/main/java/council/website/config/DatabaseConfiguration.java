package council.website.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import council.website.constants.SystemConstants;

/**
 * Initializes a hikari data source for the web controller
 * 
 * @author Abhinav Shetty
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"council.website"})
@ComponentScan(basePackages = { "council.website" })
public class DatabaseConfiguration {
	
	private final static Logger LOG = LogManager.getLogger(DatabaseConfiguration.class);

	@Autowired
	private Environment env;

	private DataSource dataSource;

	private SqlSessionFactory sqlSession;

	@Bean
	public SqlSessionFactory sessionFactory() throws Exception {
		LOG.traceEntry();
		if (sqlSession == null) {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setDataSource(getDataSource());
			sessionFactory.setTypeAliases(new Class[]{YesNoBooleanTypeHandler.class});
			this.sqlSession = sessionFactory.getObject();
			LOG.info("Mybatis sql sessions generated!");
		}
		LOG.traceExit();
		return this.sqlSession;
	};

	@Bean
	public DataSource getDataSource() throws SQLException {
		LOG.traceEntry();
		if (this.dataSource == null) {
			LOG.trace("Retrieving properties for setting up db connection");
			
			String username = env.getProperty(SystemConstants.USERNAME_PROPERTY_SPRING);
			String password = env.getProperty(SystemConstants.PASSWORD_PORPERTY_SPRING);
			String jdbUrl = env.getProperty(SystemConstants.DATABASEURL_PROPERTY_SPRING);
			String dbDriver = env.getProperty(SystemConstants.DATABASEDRIVER_PROPERTY_SPRING);
			Integer dbPoolSize = SystemConstants.APPLICATION_DB_POOL_SIZE;
			Long maxLifetime = SystemConstants.MAX_DBCONNECTION_LIFETIME_PROPERTY;
			
			LOG.trace("Properties successfully retrieved!");
			
			LOG.trace("Initializing the datasource...");
			DataSourceBuilder<HikariDataSource> builder = DataSourceBuilder.create().type(HikariDataSource.class);
			builder.driverClassName(dbDriver);
			builder.url(jdbUrl);
			builder.username(username);
			builder.password(password);

			HikariDataSource dataSource = builder.build();
			dataSource.setMaximumPoolSize(dbPoolSize);
			dataSource.setMaxLifetime(maxLifetime);
			
			this.dataSource = dataSource;
			
			LOG.info("Data source initialization complete! " + dbPoolSize + " connections are in use...");
		}
		
		LOG.traceExit();
		return this.dataSource;
	}

}
