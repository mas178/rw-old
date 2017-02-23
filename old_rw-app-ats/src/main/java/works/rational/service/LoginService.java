package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Group;
import works.rational.domain.Tenant;
import works.rational.domain.User;
import works.rational.repository.GroupRepository;
import works.rational.repository.TenantRepository;
import works.rational.repository.UserRepository;

import java.io.IOException;
import java.util.Arrays;

@Service
@Transactional
public class LoginService implements UserDetailsService {
  private final TenantRepository tenantRepository;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  public LoginService(TenantRepository tenantRepository, GroupRepository groupRepository, UserRepository userRepository) {
    Assert.notNull(tenantRepository);
    Assert.notNull(groupRepository);
    Assert.notNull(userRepository);
    this.tenantRepository = tenantRepository;
    this.groupRepository = groupRepository;
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

  public void save(String username, String password, String tenant_name) throws IOException {
    if (userRepository.findOne(username) != null) {
      throw new UsernameNotFoundException("The requested user has been already registered.");
    }

    Tenant tenant = new Tenant();
    tenant.setName(tenant_name);
    tenant.setCreatedUpdatedBy(username);
    tenantRepository.save(tenant);

    User user = new User();
    user.setUsername(username);
    user.setEncodedPassword(new Pbkdf2PasswordEncoder().encode(password));
    user.setTenant(tenant);
    user.setTimeStamp(username);

    Group group = new Group();
    group.setName("デフォルト");
    group.setTenant(tenant);
    group.setCreatedUpdatedBy(username);
    group.setUsers(Arrays.asList(user));
    groupRepository.save(group);
  }
}
