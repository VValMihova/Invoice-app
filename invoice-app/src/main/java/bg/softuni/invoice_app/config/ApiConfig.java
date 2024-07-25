package bg.softuni.invoice_app.config;

import bg.softuni.invoice_app.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class ApiConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  
  @Bean
  public DataSourceInitializer dataSourceInitializer(
      DataSource dataSource,
      RoleRepository roleRepository,
      ResourceLoader resourceLoader) {
    
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    
    if (roleRepository.count() == 0){
      ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
      populator.addScript(resourceLoader.getResource("classpath:data.sql"));
      initializer.setDatabasePopulator(populator);
    }
    
    return initializer;
  }
}
