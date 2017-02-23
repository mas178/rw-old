package works.rational.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.User;
import works.rational.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    Assert.notNull(userRepository);
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public List<User> findAll(List<String> usernames) {
    return userRepository.findAll(usernames);
  }

  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAllOrderById(pageable);
  }

  public User findOne(String username) {
    return userRepository.findOne(username);
  }

  public User save(User user, UserDetails userDetails) {
    user.setTimeStamp(userDetails.getUsername());
    return userRepository.save(user);
  }
}
