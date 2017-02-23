package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Task;
import works.rational.repository.TaskRepository;

import java.util.List;

@Service
@Transactional
public class TaskService {
  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    Assert.notNull(taskRepository);
    this.taskRepository = taskRepository;
  }

  public List<Task> findAll() {
    return taskRepository.findAll();
  }

  public Task findOne(String id) {
    return taskRepository.findOne(id);
  }

  public Task save(Task task, UserDetails userDetails) {
    task.setCreatedUpdatedBy(userDetails.getUsername());
    return taskRepository.save(task);
  }
}
