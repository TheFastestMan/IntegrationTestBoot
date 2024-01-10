package com.example.integrationtestboot;

import com.example.integrationtestboot.dto.CompanyDTO;
import com.example.integrationtestboot.entity.User;
import com.example.integrationtestboot.service.CompanyService;
import com.example.integrationtestboot.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class IntegrationTestBootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(IntegrationTestBootApplication.class, args);

        CompanyService companyService = context.getBean(CompanyService.class);
        UserService userService = context.getBean(UserService.class);

        //CompanyDTO companyDTO = new CompanyDTO();
        //  companyDTO.setName("Pelmeshki");
        // companyService.updateCompany(1L, companyDTO); // update by ID

        //  companyService.deleteCompaniesStartingWithA(); // delete A

        System.out.println(userService.findAdminsBornBetween1980And1990()); // find


    }

}
