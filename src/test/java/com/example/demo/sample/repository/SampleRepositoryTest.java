package com.example.demo.sample.repository;

import com.example.demo.sample.entity.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest  // JDBC 관련 설정만 로드
@Import(SampleRepository.class)  // 테스트 대상 명시적으로 등록
class SampleRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SampleRepository sampleRepository;

    @BeforeEach
    void setUpDatabase() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS tb_sample");
            statement.executeUpdate("CREATE TABLE tb_sample (id INT PRIMARY KEY AUTO_INCREMENT, name varchar(255))");
            statement.executeUpdate("INSERT INTO tb_sample (name) VALUES ('홍길동')");
            statement.executeUpdate("INSERT INTO tb_sample (name) VALUES ('둘리')");
        }
    }

    @Test
    void findAll_정상조회DB() {
        List<Sample> samples = sampleRepository.findAll();
        assertThat(samples).extracting("name").containsExactly("홍길동", "둘리");
    }

    @Test
    void insert_성공DB() {
        Sample sample = new Sample(null, "철수");
        sampleRepository.insert(sample);
        List<Sample> samples = sampleRepository.findAll();

        assertThat(samples)
                .extracting("name")
                .containsExactly("홍길동", "둘리", "철수");
    }

    @Test
    void update_성공DB() {
        Sample sample = new Sample(1L, "홍길동2");
        sampleRepository.update(sample);
        List<Sample> samples = sampleRepository.findAll();

        assertThat(samples)
                .extracting("name")
                .containsExactly("홍길동2", "둘리");
    }

    @Test
    void findById_정상조회DB() {
        Optional<Sample> sample = sampleRepository.findById(1L);

        assertThat(sample).isPresent();
        assertThat(sample.get().getId()).isEqualTo(1L);
        assertThat(sample.get().getName()).isEqualTo("홍길동");
    }

}