package kz.kaznitu.lessons.impls;


import kz.kaznitu.lessons.interfaces.EmployeeDAO;
import kz.kaznitu.lessons.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmployeeDAOImpl  implements EmployeeDAO{
    private JdbcTemplate jdbcTemplate ;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource) ;
    }

    public void save(Employee employee) {
        String sql = "INSERT INTO employee (name, role) VALUES (?, ?)" ;
        Object [] objects = new Object[]{employee.getName(), employee.getRole()} ;
        jdbcTemplate.update(sql, objects) ;
    }

    public Employee getById(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?" ;
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Employee>() {
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                Employee employee = new Employee() ;
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setRole(resultSet.getString("role"));
                return employee;
            }
        }) ;
    }

    public void update(Employee employee) {
        String sql = "UPDATE employee SET name = ?, role = ? WHERE id = ?" ;
        Object [] objects = new Object[]{employee.getName(), employee.getRole(),
            employee.getId()} ;
        jdbcTemplate.update(sql, objects) ;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM employee WHERE id = ?" ;
        jdbcTemplate.update(sql, id) ;

    }

    public List<Employee> getAll() {
        String sql = "SELECT * FROM employee" ;
        List<Employee> employees = new ArrayList<Employee>() ;

        List<Map<String, Object>> empRows = jdbcTemplate.queryForList(sql) ;
        for(Map<String, Object> empRow : empRows){
            Employee emp = new Employee();
            emp.setId(Integer.parseInt(String.valueOf(empRow.get("id"))));
            emp.setName(String.valueOf(empRow.get("name")));
            emp.setRole(String.valueOf(empRow.get("role")));
            employees.add(emp);
        }
        return employees;
    }
}
