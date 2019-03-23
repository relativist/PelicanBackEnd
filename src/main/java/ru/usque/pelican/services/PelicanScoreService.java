package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanScore;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.repository.PelicanScoreRepository;
import ru.usque.pelican.repository.PelicanUserRepository;
import ru.usque.pelican.services.interfaces.IPelicanScoreService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanScoreService implements IPelicanScoreService {
    private final PelicanScoreRepository repository;
    private final PelicanUserRepository userRepository;

    @Autowired
    public PelicanScoreService(PelicanScoreRepository repository,PelicanUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PelicanScore> findAll() {
        List<PelicanScore> scores = new ArrayList<>();
        repository.findAll().forEach(scores::add);
        return scores;
    }

    @Override
    public PelicanScore findByUserId(Integer userId) {
        List<PelicanScore> byUser = repository.findByUserId(userId);
        if (byUser.isEmpty()) {
            PelicanUser user = userRepository.findById(userId);
            return createNew(user);
        }
        return byUser.get(0);
    }

    @Override
    public PelicanScore findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public PelicanScore operateScore(Integer value, Integer userId) {
        PelicanScore byUser = findByUserId(userId);
        byUser.setScore(byUser.getScore() + value);
        repository.save(byUser);
        return byUser;
    }

    private PelicanScore createNew(PelicanUser user) {
        PelicanScore score = new PelicanScore();
        score.setScore(0);
        score.setUser(user);
        return repository.save(score);
    }

}
