package com.doraemon.test;

import com.alibaba.fastjson.JSONObject;
import com.doraemon.user.controller.UserApiPath;
import com.doraemon.user.dao.model.LoginUser;
import com.doraemon.user.dao.model.User;
import com.droaemon.common.util.JsonMapperUtil;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public abstract class CrudControllerTest<T> extends BaseControllerTest {

    private User mUser;
    private String mAuth;

    protected abstract T createModel();

    protected abstract String getRestfulApiPath();

    protected void login() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("admin");
        loginUser.setPassword("123456");
        getMockMvc().perform(post(UserApiPath.ACCOUNT_LOGIN)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(loginUser)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> mAuth = result.getResponse().getHeader("Authorization"));

        getMockMvc().perform(get(UserApiPath.USER + "/current")
            .header("Authorization", mAuth))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> mUser = JsonMapperUtil.stringToObject(result.getResponse().getContentAsString(), User.class));
    }

    protected abstract void testCrud() throws Exception;

    protected void executeCrud() throws Exception {
        login();

        T model = createModel();
        if(model == null) {
            throw new IllegalArgumentException("model 对象不能为空");
        }

        Integer id = create(model);
        setModelId(id, model);
        read(id);
        update(id, model);
        delete(id);
    }

    protected Integer create(T model) throws Exception {
        AtomicReference<Integer> id = new AtomicReference<>();

        System.out.println(JSONObject.toJSONString(model));

        getMockMvc().perform(post(getRestfulApiPath())
                .header("Authorization", mAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(model)))
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
        getMockMvc().perform(get(getRestfulApiPath() + "/" + id)
            .header("Authorization", mAuth))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        getMockMvc().perform(
            get(getRestfulApiPath() + "?pageNo=0&pageSize=10")
                .header("Authorization", mAuth))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    protected void update(Integer id, T model) throws Exception {
        getMockMvc().perform(put(getRestfulApiPath() + "/" + id)
            .header("Authorization", mAuth)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(model)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    protected void delete(Integer id) throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.delete(getRestfulApiPath() + "/" + id)
            .header("Authorization", mAuth))
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

    public User getUser() {
        return mUser;
    }

    public String getAuth() {
        return mAuth;
    }
}
