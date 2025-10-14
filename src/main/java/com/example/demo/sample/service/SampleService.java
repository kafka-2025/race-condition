package com.example.demo.sample.service;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.sample.entity.Sample;
import com.example.demo.sample.repository.SampleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public List<Sample> findAll() {
        return sampleRepository.findAll();
    }

    public Sample insert(Sample sample) {
        return sampleRepository.insert(sample);
    }

    public Sample update(Sample sample) {
        return sampleRepository.update(sample);
    }

    public void delete(Long id) {
        sampleRepository.delete(id);
    }

    public Sample findById(Long id) {
        return sampleRepository.findById(id).orElseThrow(() -> new NotFoundException("Sample not found with id:" + id));
    }
}
