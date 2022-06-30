package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RestTemplate restTemplate;

    public MappingJacksonValue getUserInfo(String login) {
        try {
            UserInfoDTO userInfoDTO = restTemplate.getForObject("https://api.github.com/users/" + login, UserInfoDTO.class);

            if (userInfoDTO == null) {
                return null;
            } else if (userInfoDTO.getFollowers() != null && userInfoDTO.getPublicRepos() != null) {
                userInfoDTO.setCalculations(6.0 / userInfoDTO.getFollowers() * (2.0 + userInfoDTO.getPublicRepos()));
            }

            User user = userDAO.findByLogin(login);

            if (user == null) {
                user = new User(login, 0L);
            }

            user.setRequestCount(user.getRequestCount() + 1);
            userDAO.save(user);

            SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                    SimpleBeanPropertyFilter.serializeAllExcept("followers", "public_repos");

            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("outputFilter", simpleBeanPropertyFilter);

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userInfoDTO);
            mappingJacksonValue.setFilters(filterProvider);

            return mappingJacksonValue;
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
        }

        return null;
    }
}
