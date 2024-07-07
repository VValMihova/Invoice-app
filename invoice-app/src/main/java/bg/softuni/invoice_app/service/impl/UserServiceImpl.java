package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.UserRegisterDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;
  
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
  }
  
  
  @Override
  public void register(UserRegisterDto registerData) {
    User user = createUser(registerData);
    this.userRepository.save(user);
  }
  
  private User createUser(UserRegisterDto registerData) {
    return new User()
        .setEmail(registerData.getEmail())
        .setPassword(passwordEncoder.encode(registerData.getPassword()))
        .setCompanyDetails(modelMapper.map(registerData.getCompanyDetails(), CompanyDetails.class));
  }
  
  @Override
  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }
}
