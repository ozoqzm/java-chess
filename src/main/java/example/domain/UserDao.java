//package example.domain;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public final class UserDao {
//    private static final String SERVER =
//            "localhost:3306"; // MySQL 서 버 주소
//    private static final String DATABASE =
//            "chess"; // MySQL DATABASE 이 름
//    private static final String OPTION =
//            "?useSSL=false&serverTimezone=UTC";
//    private static final String USERNAME =
//            "root"; // MySQL 서 버 아이디
//    private static final String PASSWORD =
//            "20020923"; // MySQL 서버 비밀번호
//
//    public Connection getConnection() {
//    // 드라이버 연결
//        try {
//            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME,
//                    PASSWORD);
//        } catch (final SQLException e) {
//            System.err.println("DB 연결 오류:" + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // 유저 추가
//    public void addUser(final User user) {
//        final var query =
//                "INSERT INTO user VALUES(?, ?)";
//        try (final var connection = getConnection();
//             final var preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, user.userId());
//            preparedStatement.setString(2, user.name());
//            preparedStatement.executeUpdate();
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // 유저 검색
//    public User findByUserId(final String userId) {
//        final var query =
//                "SELECT * FROM user WHERE user_id = ? ";
//        try (final var connection = getConnection();
//             final var preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, userId);
//            final var resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return new User(
//                        resultSet.getString("user_id"),
//                        resultSet.getString("name")
//                );
//            }
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//}