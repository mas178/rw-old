package works.rational.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.Main;
import works.rational.domain.Group;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@Repository
public class GroupRepositoryTest {
  @Autowired
  private GroupRepository repository;

  @Test
  public void normal() {
    List<Group> groups = repository.findAll();
    assertEquals(0, groups.size());
  }
}
