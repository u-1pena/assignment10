package com.u1.user.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest//結合テストがメインで使われる// ！単体だと非効率！！
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Nested
    class ReadClass {

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
        void 存在しない名前を指定したとき404エラーを返すこと() throws Exception {

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
        void 存在しないIDを指定したとき404エラーを返すこと() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/users/0"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found with id: " + 0))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/users/0"));

        }
    }


    @Nested
    class CreateClass {

        @Test
        @DataSet(value = "datasets/users.yml")
        @ExpectedDataSet(value = "datasets/insertUsers.yml", ignoreCols = "id")
        @Transactional
        void 名前と生年月日を未使用のIDで紐づけし登録すること() throws Exception {
            String requestBody = """
                    {
                               "name": "Tom",
                               "birthday": "1990/01/01"
                    }
                    """;
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "user created"
                            }
                            """));
        }

        @Test
        @DataSet(value = "datasets/users.yml")
        @Transactional
        void 不正なフォーマットで登録しようとしたとき400エラーを返すこと() throws Exception {
            String exceptionRequestBody = """
                    {
                               "name": "",
                               "birthday": ""
                    }
                    """;
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exceptionRequestBody))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            """
                                    {
                                        "status": "BAD_REQUEST",
                                        "message": "validation error",
                                        "errors": [
                                            {
                                                "field": "birthday",
                                                "message": "YYYY/MM/ddで入力して下さい"
                                            },
                                            {
                                                "field": "name",
                                                "message": "must not be blank"
                                            }
                                        ]
                                    }
                                                """));

        }
    }

    @Nested
    class DeleteClass {

        @Test
        @DataSet(value = "datasets/users.yml")
        @ExpectedDataSet(value = "datasets/deleteusers.yml")
        @Transactional
        void 指定したIDと紐づいた存在するユーザーを削除すること() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "message": "a deleted user!"
                            }
                            """));
        }

        @Test
        @DataSet(value = "datasets/users.yml")
        @Transactional
        void 存在しないIDでユーザーを削除を行おうとしたとき404エラーを返すこと() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/0"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found with id: " + 0))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/users/0"));

        }
    }
}
