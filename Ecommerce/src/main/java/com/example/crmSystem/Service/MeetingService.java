package com.example.crmSystem.Service;


import com.example.crmSystem.Repository.MeetingRepository;
import com.example.crmSystem.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    public Meeting findById(Long id) {
        return meetingRepository.findById(id).orElse(null);
    }

    public List<Meeting> findByUserId(Long userId) {
        return meetingRepository.findByUserId(userId);
    }

    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public void deleteById(Long id) {
        meetingRepository.deleteById(id);
    }
}
