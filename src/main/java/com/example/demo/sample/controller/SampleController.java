package com.example.demo.sample.controller;

import com.example.demo.common.dto.ResponseObject;
import com.example.demo.sample.dto.SampleDTO;
import com.example.demo.sample.dto.ValidationGroups;
import com.example.demo.sample.entity.Sample;
import com.example.demo.sample.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SampleController {

    private final Logger log = LoggerFactory.getLogger(SampleController.class);

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/samples")
    public ResponseEntity<ResponseObject<List<Sample>>> getSamples() {
        return ResponseEntity.ok(ResponseObject.success(sampleService.findAll()));
    }

    @PostMapping("/samples")
    public ResponseEntity<ResponseObject<SampleDTO>> registerSample(
            @RequestBody @Validated(ValidationGroups.Insert.class) SampleDTO sampleDTO) {
        return ResponseEntity.ok(ResponseObject.success(SampleDTO.fromEntity(sampleService.insert(sampleDTO.toEntity()))));
    }

    @PutMapping("/samples")
    public ResponseEntity<ResponseObject<SampleDTO>> updateSample(
            @RequestBody @Validated(ValidationGroups.Update.class) SampleDTO sampleDTO) {
        return ResponseEntity.ok(ResponseObject.success(SampleDTO.fromEntity(sampleService.update(sampleDTO.toEntity()))));
    }

    @DeleteMapping("/samples/{id}")
    public ResponseEntity<ResponseObject<Void>> deleteSample(@PathVariable Integer id) {
        sampleService.delete(id);
        return ResponseEntity.ok(ResponseObject.success());
    }

    @GetMapping("/samples/{id}")
    public ResponseEntity<ResponseObject<Sample>> getSample(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseObject.success(sampleService.findById(id)));
    }

}
