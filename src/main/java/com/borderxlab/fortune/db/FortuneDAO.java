package com.borderxlab.fortune.db;

import java.util.*;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.borderxlab.fortune.core.FortuneMapper;
import com.borderxlab.fortune.core.Fortune;

public interface FortuneDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS fortune (id int primary key, content text)")
    void createTableIfNotExists();

    @SqlUpdate("INSERT INTO fortune (id, content) VALUES (:id, :content)")
    void insertFortune(@Bind("id") int id, @Bind("content") String content);

    @SqlQuery("SELECT id, content FROM fortune")
    @RegisterMapper(FortuneMapper.class)
    List<Fortune> getFortunes();

    @SqlUpdate("DELETE FROM fortune WHERE id = :id")
    void deleteFortune(@Bind("id") int id);

}
