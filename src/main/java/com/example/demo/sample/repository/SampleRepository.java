package com.example.demo.sample.repository;

import com.example.demo.sample.entity.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SampleRepository {

    private final Logger log = LoggerFactory.getLogger(SampleRepository.class);

    private final DataSource dataSource;

    public SampleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Sample> findAll() {

        List<Sample> samples = new ArrayList<>();
        String sql = "select id, name from tb_sample";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Sample sample = new Sample(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                samples.add(sample);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return samples;
    }

    public Sample insert(Sample sample) {

        String sql = "insert into tb_sample(name) values(?)";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, sample.getName());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    sample.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sample;
    }

    public Sample update(Sample sample) {
        String sql = "update tb_sample set name=? where id=?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, sample.getName());
            ps.setInt(2, sample.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Failed to update sample");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sample;
    }

    public void delete(Integer id) {
        String sql = "delete from tb_sample where id=?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Failed to delete sample");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Sample> findById(Integer id) {
        String sql = "select id, name from tb_sample where id=?";
        Sample sample = null;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);

        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Sample(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
