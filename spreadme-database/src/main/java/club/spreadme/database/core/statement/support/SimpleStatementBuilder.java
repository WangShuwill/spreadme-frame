/*
 *  Copyright (c) 2018 Wangshuwei
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package club.spreadme.database.core.statement.support;

import club.spreadme.database.core.statement.WrappedStatement;
import club.spreadme.database.core.statement.wrapper.SimpleWrappedStatement;
import club.spreadme.database.metadata.ConcurMode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleStatementBuilder extends AbstractStatementBuilder {

    private final String sql;
    private ConcurMode concurMode;

    public SimpleStatementBuilder(String sql, ConcurMode concurMode) {
        this.sql = sql;
        this.concurMode = concurMode;
    }

    @Override
    public WrappedStatement doBuild(Statement statement) {
        return new SimpleWrappedStatement(statement, sql);
    }

    @Override
    public Statement createStatement(Connection connection) throws SQLException {
        return connection.createStatement(ResultSet.FETCH_FORWARD, concurMode.getValue());
    }

    @Override
    public String getSql() {
        return sql;
    }
}