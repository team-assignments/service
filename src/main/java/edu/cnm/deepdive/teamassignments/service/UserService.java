package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.UserRepository;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * User service class.
 */
@Service
public class UserService implements Converter <Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository repository;

  /**
   * User service repository.
   * @param repository
   */
  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Get or create users.
   * @param oauthKey
   * @param displayName
   * @return
   */
  public User getOrCreate(String oauthKey, String displayName) {
    return repository.findFirstByOauthKey(oauthKey)
        .map((user) -> {
          user.setConnected(new Date());
           return repository.save(user);
        })
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          user.setConnected(new Date());
          return repository.save(user);
        });
  }

  /**
   * Save user to database.
   * @param user
   * @return
   */
  public User save(User user) {
    return repository.save(user);   //TODO review with team
  }

  public Optional<User> get(long id) {
    return repository.findById(id);
  }

  /**
   * Get or create authentication token for user.
   * @param jwt
   * @return
   */
  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    Collection<SimpleGrantedAuthority> grants = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(), jwt.getClaim("name")), jwt.getTokenValue(), grants);
  }
}
