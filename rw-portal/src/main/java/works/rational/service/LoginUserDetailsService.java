package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import works.rational.domain.User;
import works.rational.repository.UserRepository;

import java.io.IOException;

@Service
public class LoginUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public LoginUserDetailsService(UserRepository userRepository) {
    Assert.notNull(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findOne(username);
    if (user == null) {
      throw new UsernameNotFoundException("The requested user is not found.");
    }
    return new LoginUserDetails(user);
  }

  public User create(User user) throws IOException {
    if (userRepository.findOne(user.getUsername()) != null) {
      throw new UsernameNotFoundException("The requested user has been already registered.");
    }

    if (!userRepository.saveInCamunda(user)) {
      throw new UsernameNotFoundException("The requested user can't be saved in camunda.");
    }

    return userRepository.save(user);
  }
}
