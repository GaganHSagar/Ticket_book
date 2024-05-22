package com.hashedin.huSpark.service;

import com.hashedin.huSpark.model.Waitlist;
import com.hashedin.huSpark.repository.WaitlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitlistService {
    @Autowired
    private WaitlistRepository waitlistRepository;

    public List<Waitlist> getWaitlistByTrain(Long trainId) {
        return waitlistRepository.findByTrainId(trainId);
    }
}