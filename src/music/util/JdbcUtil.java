package music.util;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtil {
	public PreparedStatement preparedStatement;
	public ResultSet resultSet;
    public JdbcUtil() {
    	super();
    }

    /**
     * Lấy đối tượng kết nối cơ sở dữ liệu
     * @return
     */
    private static Connection connection = null;
    public static Connection getConnection() throws SQLException {
        
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=UTF-8","root","091102");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return connection;
    }
    
    /**
     * Thực thi thêm, xóa, sửa câu lệnh sql
     * @param sql Câu lệnh sql sẽ được thực thi
     * @param params danh sách tham số
     * @returnThực hiện trả về thành công true,nếu không trở lại false
     * @throws SQLException
     */
    public boolean updateByPreparedStatement(String sql, List<?> params) throws SQLException {
        int result = -1;    // Cho biết số lượng hàng trong cơ sở dữ liệu bị ảnh hưởng khi người dùng thực hiện thêm, xóa và sửa đổi
        preparedStatement = connection.prepareStatement(sql);
        int index = 1;
        if (params != null && !params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        result = preparedStatement.executeUpdate();
        return result > 0 ? true : false;
    }
    
    /**
     * Thực hiện kiểm tra
     * @param sql Câu lệnh sql sẽ được thực thi
     * @param params danh sách tham số
     * @return Trả về danh sách Bản đồ bao gồm <tên cột, giá trị>
     * @throws SQLException
     */
    public List<Map<String, Object>> findResult(String sql, List<?> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        preparedStatement = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }
    
    /**
     * Giải phóng tài nguyên
     */
    public void releaseConn(){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
