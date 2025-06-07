package com.example.crmSystem.Service;


import com.example.crmSystem.Repository.ActivityRepository;
import com.example.crmSystem.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Activity findById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    public List<Activity> findByUserId(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }
}
