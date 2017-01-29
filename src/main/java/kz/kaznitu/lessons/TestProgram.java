package kz.kaznitu.lessons;


import kz.kaznitu.lessons.interfaces.EmployeeDAO;
import kz.kaznitu.lessons.models.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestProgram {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-context.xml") ;
        EmployeeDAO employeeDAO = (EmployeeDAO)context.getBean("employeeDAO") ;

        Employee employee1 = new Employee("Daulet", "teacher") ;
        Employee employee2 = new Employee("Samgar", "developer") ;

        employeeDAO.save(employee1);
        employeeDAO.save(employee2);

        Employee employee3 = employeeDAO.getById(2) ;
        System.out.println(employee3.getName());

        employee1.setRole("designer");
        employeeDAO.update(employee1);

        List<Employee> employees = employeeDAO.getAll() ;
        System.out.println(employees);

        employeeDAO.deleteById(2);

    }
}
