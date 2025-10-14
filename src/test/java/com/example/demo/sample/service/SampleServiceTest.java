package com.example.demo.sample.service;

import com.example.demo.sample.entity.Sample;
import com.example.demo.sample.repository.SampleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleServiceTest {

    @Mock
    private SampleRepository sampleRepository;

    @InjectMocks
    private SampleService sampleService;

    @Test
    void findAll_정상조회() {
        when(sampleRepository.findAll())
                .thenReturn(
                        List.of(new Sample(1, "홍길동"),
                                new Sample(2, "둘리")
                        )
                ); // Mock 동작 정의(stub)

        List<Sample> samples = sampleService.findAll(); // 메서드 호출(act)

        assertThat(samples)
                .extracting("name")
                .containsExactly("홍길동", "둘리"); // 결과 검증 (assert)

        verify(sampleRepository, times(1)).findAll(); // mock을 한번만 호출했는지 확인. (verify)
    }

    @Test
    void insert_성공() {

        when(sampleRepository.insert(any(Sample.class)))
                .thenReturn(new Sample(1,"홍길동")); // Mock 동작 정의(stub)

        Sample sample = sampleService.insert(new Sample(null,"홍길동")); // 메서드 호출(act)

        assertThat(sample.getId()).isEqualTo(1);
        assertThat(sample.getName()).isEqualTo("홍길동"); // 결과 검증 (assert)

        verify(sampleRepository, times(1))
                .insert(any(Sample.class)); // mock을 한번만 호출했는지 확인 (verify)

    }

    @Test
    void update_성공() {
        when(sampleRepository.update(any(Sample.class)))
                .thenReturn(new Sample(1, "둘리"));

        Sample sample = sampleService.update(new Sample(1, "둘리"));

        assertThat(sample.getId()).isEqualTo(1);
        assertThat(sample.getName()).isEqualTo("둘리");

        verify(sampleRepository, times(1)).update(any(Sample.class));
    }

    @Test
    void delete_성공() {
        // 반환값이 없으므로 stub 필요 없음.

        Integer id = 1;
        sampleService.delete(id);
        verify(sampleRepository, times(1)).delete(id);
    }

    @Test
    void delete_실패() {

        doThrow(new RuntimeException("Failed to delete sample"))
                .when(sampleRepository)
                .delete(9999); // Mock 동작 정의 (stub)

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sampleService.delete(9999)); // 예외 발생 검증

        assertThat(exception.getMessage()).isEqualTo("Failed to delete sample");
    }

    @Test
    void findById() {
        when(sampleRepository.findById(anyInt())).thenReturn(Optional.of(new Sample(1, "홍길동")));
        Sample sample = sampleService.findById(1);
        assertThat(sample.getId()).isEqualTo(1);
        assertThat(sample.getName()).isEqualTo("홍길동");
        verify(sampleRepository, times(1)).findById(anyInt());
    }

}