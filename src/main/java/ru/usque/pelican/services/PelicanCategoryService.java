package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.repository.PelicanCategoryRepository;
import ru.usque.pelican.repository.PelicanUserRepository;
import ru.usque.pelican.services.interfaces.IPelicanCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanCategoryService implements IPelicanCategoryService {
  private final PelicanCategoryRepository repository;
  private final PelicanUserRepository userRepository;

  @Autowired
  public PelicanCategoryService(PelicanCategoryRepository repository, PelicanUserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
  }

  @Override
  public List<PelicanCategory> findAll() {
    List<PelicanCategory> categories = new ArrayList<>();
    repository.findAll().forEach(categories::add);
    return categories;
  }

  @Override
  public PelicanCategory findById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public List<PelicanCategory> findByUserId(Integer id) {
    List<PelicanCategory> catByUser = repository.findByUserId(id);
    if (catByUser.isEmpty()) {
      PelicanCategory cat = new PelicanCategory();
      cat.setDeprecated(false);
      cat.setDisposable(false);
      cat.setDisposableCapacity(50);
      cat.setDisposableDone(10);
      cat.setHeader(true);
      cat.setScore(100);
      cat.setName("Simple parent Category");
      cat.setSimple(false);
      cat.setUser(userRepository.findById(id));
      this.addCategory(cat);
      catByUser.add(cat);
    }
    return catByUser;
  }

  @Override
  public boolean addCategory(PelicanCategory category) {
    repository.save(category);
    return true;
  }

  @Override
  public PelicanCategory updateCategory(PelicanCategory category) {
    PelicanUser user = category.getUser();

    //нельзя установить парента саму себя
    if (category.getParent() != null) {
      if (category.getParent().getId().equals(category.getId())) {
        return category;
      }
    }

    //нельзя назначать задаче парента, которая является парентом
    if (category.getParent() != null) {
      if (repository.findByUserId(user.getId()).stream().anyMatch(e -> {
        PelicanCategory parent = e.getParent();
        if (parent != null) {
          return parent.getId().equals(category.getId());
        }
        return false;
      })) {
        return category;
      }
    }
    return repository.save(category);
  }

  @Override
  public void deleteCategory(Integer id) {
    repository.delete(id);
  }

}
