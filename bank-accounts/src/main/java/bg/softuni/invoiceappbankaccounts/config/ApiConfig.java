package bg.softuni.invoiceappbankaccounts.config;

import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class ApiConfig {
  
  @Bean
  public DataSourceInitializer dataSourceInitializer(
      DataSource dataSource,
      BankAccountRepository bankAccountRepository,
      ResourceLoader resourceLoader) {
    
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    
    if (bankAccountRepository.count() == 0) {
      ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
      populator.addScript(resourceLoader.getResource("classpath:data.sql"));
      initializer.setDatabasePopulator(populator);
    }
    
    return initializer;
  }
}
