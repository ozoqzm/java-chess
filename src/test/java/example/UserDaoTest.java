package example;

import static org.assertj.core.api.Assertions.*;

import example.domain.UserDao;
import org.junit.jupiter.api.Test;
import example.domain.User;

import java.sql.SQLException;

class UserDaoTest {
    private final UserDao userDao = new UserDao();

    // 데이터베이스 접속 테스트
    @Test
    public void connection() throws SQLException {
        try (final var connection = userDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    // user 추가 테스트
    @Test
    public void addUser() {
        final var user = new User("testUserId2", "testUser");
        userDao.addUser(user);
    }

    // user 검색 테스트
    @Test
    public void findByUserId() {
        final var user = userDao.findByUserId("testUserId");
        assertThat(user).isEqualTo(new User("testUserId", "testUser"));
    }
}