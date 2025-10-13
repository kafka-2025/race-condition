package com.example.demo.sample.dto;

import com.example.demo.sample.entity.Sample;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SampleDTO(
        @NotNull(groups = ValidationGroups.Update.class, message = "필수 입력값입니다.")
        Integer id,

        @NotBlank(groups = {ValidationGroups.Insert.class, ValidationGroups.Update.class}, message = "필수 입력값입니다.")
        String name) {
    public Sample toEntity() {
        return new Sample(id, name);
    }

    public static SampleDTO fromEntity(Sample sample) {
        return new SampleDTO(sample.getId(), sample.getName());
    }
}
