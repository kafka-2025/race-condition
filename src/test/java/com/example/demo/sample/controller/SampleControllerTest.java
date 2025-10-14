package com.example.demo.sample.controller;


import com.example.demo.sample.dto.SampleDTO;
import com.example.demo.sample.entity.Sample;
import com.example.demo.sample.service.SampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SampleController.class) // Controller 단위 테스트 도구
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc; // HTTP 요청 시뮬레이터

    @MockitoBean // Spring context에 Mock 객체 등록 + DI 주입
    private SampleService sampleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getSamples_정상조회() throws Exception { // checked 예외 때문에 throws Exception 추가
        // Mock 동작 정의 (stub)
        when(sampleService.findAll())
                .thenReturn(List.of(
                        new Sample(1L, "홍길동"),
                        new Sample(2L, "둘리")
                ));

        // 호출 (ack) + 결과 검증 (assert)
        mockMvc.perform(get("/samples")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("홍길동"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("둘리"))
        ;

        // Mock 을 한번 호출했는지 확인 (verify)
        verify(sampleService, times(1)).findAll();
    }

    @Test
    void registerSample_정상등록() throws Exception {
        // given
        SampleDTO requestDto = new SampleDTO(null, "홍길동");
        Sample insertedSample = new Sample(1L, "홍길동");

        // Mock 동작 정의 (stub)
        when(sampleService.insert(any(Sample.class)))
                .thenReturn(insertedSample);

        // 호출 (ack) + 결과 검증 (assert)
        mockMvc.perform(post("/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("홍길동")
        );

        // 확인
        verify(sampleService, times(1)).insert(any(Sample.class));
    }

    @Test
    void updateSample_정상업데이트() throws Exception {
        // give
        SampleDTO requestDTO = new SampleDTO(1L, "둘리");
        Sample updatedSample = new Sample(1L, "둘리");

        // Mock 동작 정의 (stub)
        when(sampleService.update(any(Sample.class)))
                .thenReturn(updatedSample);


        // when & then
        mockMvc.perform(put("/samples")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("둘리")
                );

        // 확인
        verify(sampleService, times(1)).update(any(Sample.class));
    }

    @Test
    void deleteSample_정상삭제() throws Exception {
        // given
        Long id = 1L;

        // when & then
        mockMvc.perform(delete("/samples/" + id))
                .andExpect(status().isOk())
        ;

        // verify
        verify(sampleService, times(1)).delete(id);
    }

    @Test
    void getSample_정상조회() throws Exception {
        // given
        Long id = 1L;

        // Mock act
        when(sampleService.findById(id))
                .thenReturn(new Sample(1L, "홍길동"));

        // when & then
        mockMvc.perform(get("/samples/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("홍길동"));

        // verify
        verify(sampleService, times(1)).findById(id);
    }
    
    
}