package org.example;

import org.example.dao.ApplicationDao;
import org.example.models.CollegeStudent;
import org.example.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /* New for Section 2.2 */
    @Bean(name = "applicationExample")
    ApplicationService getApplicationService() {
        return new ApplicationService();
    }

    /* New for Section 2.2 */
    @Bean(name = "applicationDao")
    ApplicationDao getApplicationDao() {
        return new ApplicationDao();
    }

    @Bean(name = "collegeStudent")
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent() {
        return new CollegeStudent();
    }

}
