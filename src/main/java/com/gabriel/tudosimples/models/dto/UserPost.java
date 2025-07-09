package com.gabriel.tudosimples.models.dto;

import com.gabriel.tudosimples.models.User;

public record UserPost(String username, String password, User.Role role) {}
