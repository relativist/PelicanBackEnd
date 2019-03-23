package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanPlan;
import ru.usque.pelican.repository.PelicanPlansRepository;
import ru.usque.pelican.repository.PelicanUserRepository;
import ru.usque.pelican.services.interfaces.IPelicanPlansService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PelicanPlanService implements IPelicanPlansService {
    private final PelicanPlansRepository repository;
    private final PelicanUserRepository userRepository;

    @Autowired
    public PelicanPlanService(PelicanPlansRepository repository, PelicanUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PelicanPlan> findAll() {
        List<PelicanPlan> scores = new ArrayList<>();
        repository.findAll().forEach(scores::add);
        return scores;
    }

    @Override
    public List<PelicanPlan> findByUserId(Integer userId, Boolean isGrand) {
        List<PelicanPlan> byUserId = repository.findByUserId(userId);
        if (isGrand == null) {
            if (byUserId.stream().allMatch(PelicanPlan::getIsFinished)) {
                PelicanPlan plan = createDefault(userId);
                byUserId.add(plan);
            }
            return byUserId;
        }
        List<PelicanPlan> planByUser = byUserId.stream().filter(e -> e.getIsGrand().equals(isGrand)).collect(Collectors.toList());
        if (planByUser.isEmpty()) {
            PelicanPlan plan = createDefault(userId);
            planByUser.add(plan);
        }

        return planByUser;
    }

    private PelicanPlan createDefault(Integer userId) {
        PelicanPlan plan = new PelicanPlan();
        plan.setDate(generateDate());
        plan.setIsFinished(false);
        plan.setName("This is default task.");
        plan.setUser(userRepository.findById(userId));
        plan.setIsGrand(false);
        addPlan(plan);
        return plan;
    }

    private String generateDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        return format.format(new Date());

    }

    @Override
    public PelicanPlan addPlan(PelicanPlan plan) {
        return repository.save(plan);
    }

    @Override
    public PelicanPlan updatePlan(PelicanPlan plan) {
        return repository.save(plan);
    }

    @Override
    public void deletePlan(Integer id) {
        repository.delete(id);
    }

    @Override
    public PelicanPlan findById(Integer id) {
        return repository.findById(id);
    }


}
