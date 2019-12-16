package com.wish.doraemon.user.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginUser {

    private String username;

    private String password;
}
