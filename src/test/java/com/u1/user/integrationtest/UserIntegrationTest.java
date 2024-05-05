package com.u1.user.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest//結合テストがメインで使われる！単体だと非効率！！
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 全てのユーザー取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //レスポンスボディの検証
                .andExpect(MockMvcResultMatchers.content().json(
                        //text block便利！
                        """
                                [
                                     {
                                         "id": 1,
                                         "name": "yuichi",
                                         "birthday": "1984-07-03"
                                     },
                                     {
                                         "id": 2,
                                         "name": "kana",
                                         "birthday": "1993-12-31"
                                     }
                                ]
                                """
                ));

    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 指定したIDで存在するユーザーを取得すること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.content().json(
                        //text block便利！
                        """
                                     {
                                         "id": 1,
                                         "name": "yuichi",
                                         "birthday": "1984-07-03"
                                     }
                                """
                ));
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 指定した名前で存在するユーザーを取得すること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/names?name=y"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                        {
                                                 "id": 1,
                                                 "name": "yuichi",
                                                 "birthday": "1984-07-03"
                                        }
                        """
                ));
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 存在しない名前を指定したとき例外処理を返すこと() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/names?name=unknownUser"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found containing name: " + "unknownUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/users/names"));

    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 存在しないIDを指定したとき例外処理を返すこと() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found with id: " + 0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/users/0"));

    }

}
