/*
 *  Copyright (c) 2019 Wangshuwei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package club.srpeadme.test.database;

import club.spreadme.database.core.datasource.SpreadDataSource;
import club.spreadme.database.core.grammar.Record;
import club.spreadme.database.dao.support.CommonDao;
import club.spreadme.database.plugin.paginator.Page;
import club.spreadme.database.plugin.paginator.Paginator;
import club.spreadme.database.plugin.paginator.dialect.MySQLPaginationDialect;
import club.spreadme.database.core.tableinfo.TableInfoAcquirer;
import club.srpeadme.test.domain.Movie;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoTest.class);

    private static final String URL = "jdbc:mysql://192.168.52.128:3306/imdb?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    private CommonDao commonDao;
    private DataSource dataSource;

    @Before
    public void initTesEnv() {
        dataSource = new SpreadDataSource(URL, USERNAME, PASSWORD);
        commonDao = CommonDao.getInstance().use(dataSource);
        Paginator paginator = new Paginator();
        paginator.addDialect(MySQLPaginationDialect.class);
        commonDao.addInterceptor(paginator);
    }

    @Test
    public void testCommonDAO() {
        List<Record> records = commonDao.query("select * from movies where id = ?", "tt0468569");
        LOGGER.info(records.toString());
    }

    @Test
    public void testStreamDao() {
        try (Stream<Record> stream = commonDao.withStream().fetchSize(Integer.MIN_VALUE).query("select * from movies order by id desc")) {
            List<Record> records = stream.limit(10).collect(Collectors.toList());
            records.forEach(item -> LOGGER.info(item.toString()));
        }
    }

    @Test
    public void testAsyncDao() throws InterruptedException {
        commonDao.withAsync().query("select * from movies where id = ?", Movie.class, "tt0468569")
                .whenCompleteAsync((movies, throwable) -> movies.forEach(item -> LOGGER.info(item.toString())));
        LOGGER.info("Doing");

        Thread.sleep(10 * 1000);
    }

    @Test
    public void testDao() {
        MovieDao movieDao = commonDao.getDao(MovieDao.class);
        LOGGER.info(movieDao.getMovieById("tt0468569", "movie", 9.0).toString());
        LOGGER.info(movieDao.getMovieById("tt0468569", "movie", 9.9).toString());
    }

    @Test
    public void testPagination() {
        MovieDao movieDao = commonDao.getDao(MovieDao.class);
        LOGGER.info(movieDao.getMoviesByName("The Dark Knight", "movie", new Page<Movie>().setPageSize(5).setPageNum(1)).toString());
    }

    @Test
    public void testDaoUpdate() {
        MovieDao movieDao = commonDao.getDao(MovieDao.class);
        commonDao.getTransactionExecutor().execute(() -> {
            Movie movied = movieDao.getMovieById("tt0000000");
            movieDao.delete(movied.getId());

            Movie movie = new Movie();
            movie.setId("tt0000000");
            movie.setOriginalTitle("Test");
            movie.setPrimaryTitle("Test");
            movie.setType("movie");
            movieDao.insert(movie);

            movie.setEndYear(2020);
            return movieDao.update(movie);
        });
    }

    @Test
    public void testTableinfoAcquirer() {
        TableInfoAcquirer tableInfoAcquirer = TableInfoAcquirer.getInstance().use(dataSource);
        tableInfoAcquirer.getTableInfo("", "", "movies", "%")
                .forEach(System.out::println);
    }
}