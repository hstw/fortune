package com.borderxlab.fortune.core;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;;


public class FortuneMapper implements ResultSetMapper<Fortune> {
    public Fortune map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Fortune(r.getInt("id"), r.getString("content"));
    }
}
