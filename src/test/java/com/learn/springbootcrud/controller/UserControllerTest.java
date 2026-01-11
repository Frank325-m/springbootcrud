package com.learn.springbootcrud.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.learn.springbootcrud.common.utils.JsonUtil;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddUser() throws Exception {
        // 1.构造标准的JSON请求体（避免格式问题）
        String userJson = "{\"username\":\"Mock测试\",\"age\":28}";
        
        // 添加。accept(MediaType.APPLICATION_JSON).确保响应格式正确
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
            .andReturn().getResponse().getContentAsString();

        String msgValue = JsonUtil.getValue(response, "msg", String.class);
        System.out.print("\n"+ msgValue + "\n\n");
        Assertions.assertTrue(msgValue.contains("success"));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/user/9999")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.msg", containsString("不存在")))
            .andReturn().getResponse().getContentAsString();

        String msgValue = JsonUtil.getValue(response, "msg", String.class);
        System.out.print("\n"+ msgValue + "\n\n");
        Assertions.assertTrue(msgValue.contains("failed"));
    }
}
