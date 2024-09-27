package com.cr.usuario.adapter.controller;

import brave.Tracer;
import com.cr.usuario.adapter.controller.model.ClassRequestProcessor;
import com.cr.usuario.application.port.in.CreateUserCommand;
import com.cr.usuario.application.port.in.FindUserQuery;
import com.cr.usuario.application.service.UserService;
import com.cr.usuario.config.security.JwtConfig;
import com.cr.usuario.config.security.JwtService;
import com.cr.usuario.config.validation.ValidationConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.cr.usuario.domain.Mock.mockUserRequestModel;
import static com.cr.usuario.domain.Mock.mockUserRequestWithNameNullModel;
import static com.cr.usuario.domain.Mock.mockUserRequestWithoutPhonesModel;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CreateUserControllerAdapterTest {

    private static final String URL_POST_USER = "/api/v1.0/cr/user/create";
    private static final String URL_GET_USER = "/api/v1.0/cr/user/get";

    @MockBean
    private  Tracer tracer;
    @MockBean
    private ClassRequestProcessor processor;
    @MockBean
    private CreateUserCommand createUserCommand;
    @MockBean
    private FindUserQuery findUserQuery;
    @Autowired
    private ValidationConfig validationConfig;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    JwtService jwtService;
    @MockBean
    UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;



    @Test
    @DisplayName("All dependencies are fulfilled")
    void allDependenciesFulfilled() {
        assertThat(mockMvc).isNotNull();
        assertThat(createUserCommand).isNotNull();
        assertThat(processor).isNotNull();
        assertThat(tracer).isNotNull();
    }

    @Test
    @DisplayName("when post is called response 201")
    void whenPostHappyResponseCreated() throws Exception {
        var user = mockUserRequestModel().toDomain();
        when(createUserCommand.execute(any())).thenReturn(user);
        mockMvc.perform(post(URL_POST_USER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUserRequestModel())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when post is called without phones response 201")
    void whenPostWithoutPhonesResponseCreated() throws Exception {
        var user = mockUserRequestWithoutPhonesModel().toDomain();
        when(createUserCommand.execute(any())).thenReturn(user);
        mockMvc.perform(post(URL_POST_USER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUserRequestWithoutPhonesModel())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when post is called with name null response 400")
    void whenPostwhenFieldNullResponseBad() throws Exception {
        var user = mockUserRequestWithNameNullModel().toDomain();
        when(createUserCommand.execute(any())).thenReturn(user);
        mockMvc.perform(post(URL_POST_USER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUserRequestWithNameNullModel())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when get is called response 200")
    void whenGetHappyResponseOk() throws Exception {
        var user = mockUserRequestModel().toDomain();
        var token = jwtService.generateToken(user.getEmail());
        when(findUserQuery.execute(any())).thenReturn(user);
        when(userService.findByEmail(any())).thenReturn(user);
        mockMvc.perform(get(URL_GET_USER)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+token)
                        .header("user-email",user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

