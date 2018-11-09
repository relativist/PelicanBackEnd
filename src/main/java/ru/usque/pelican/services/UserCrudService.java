package ru.usque.pelican.services;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Service
public class UserCrudService {
    final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserCrudService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void doBd() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from public.users");
        maps.forEach(stringObjectMap -> stringObjectMap.forEach((s, o) -> log.info("{} - {}", s, o)));
    }
}
