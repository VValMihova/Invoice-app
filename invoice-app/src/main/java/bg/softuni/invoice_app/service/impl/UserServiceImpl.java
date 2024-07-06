package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.UserRegisterDto;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  
  @Override
  public void register(UserRegisterDto registerData) {
    User user = createUser(registerData);
    this.userRepository.save(user);
  }
  
  private User createUser(UserRegisterDto registerData) {
    return new User(registerData, registerData.getCompanyDetails());
  }
  @Override
  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }
}
