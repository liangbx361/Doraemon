package com.wish.doraemon.test;

import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public abstract class CrudControllerTest<T> extends BaseControllerTest {

    protected abstract T createModel();

    protected abstract String getRestfulApiPath();

    protected void doLogin() throws Exception {

    }

    public abstract void testCrud() throws Exception;

    protected void executeCrud() throws Exception {
        doLogin();

        T model = createModel();

        Integer id = create(model);
        setModelId(id, model);

        read(id);

        update(id, model);

        remove(id);
    }

    protected Integer create(T model) throws Exception {
        AtomicReference<Integer> id = new AtomicReference<>();

        getMockMvc().perform(
            post(getRestfulApiPath())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(model))
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2wiOiJhZG1pbiIsImlzcyI6ImRvcmFlbW9uIiwiaWF0IjoxNTc3NTQ4MTEyLCJzdWIiOiJhZG1pbiIsImV4cCI6MTU3ODE1MjkxMn0.haLPHIxUh6loKePBCMGaZER4gxDcI9hu8-SYeDq1kHU")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> {
                String content = result.getResponse().getContentAsString();
                id.set(getModelId(content));
            })
            .andReturn();

        return id.get();
    }

    protected void read(Integer id) throws Exception {
        getMockMvc().perform(
            get(getRestfulApiPath() + "/" + id)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        getMockMvc().perform(
            get(getRestfulApiPath() + "?pageNo=0&pageSize=10")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    protected void update(Integer id, T model) throws Exception {
        getMockMvc().perform(
            put(getRestfulApiPath() + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(model))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    protected void remove(Integer id) throws Exception {
        getMockMvc().perform(
            delete(getRestfulApiPath() + "/" + id)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    protected Integer getModelId(String content) throws JSONException {
        org.json.JSONObject jsonObject = new org.json.JSONObject(content);
        return Integer.valueOf(jsonObject.getString("id"));
    }

    protected void setModelId(Integer id, T model) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = model.getClass().getDeclaredMethod("setId", Integer.class);
        method.setAccessible(true);
        method.invoke(model, id);
    }
}
