package org.cmc.curtaincall.web.security;

import org.cmc.curtaincall.web.common.RestDocsConfig;
import org.cmc.curtaincall.web.security.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@Import({RestDocsConfig.class})
@WebMvcTest(AuthDocsController.class)
@AutoConfigureRestDocs
class AuthDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @Test
    @WithMockUser
    void loginResponse() throws Exception {
        mockMvc.perform(get("/docs/auth/login-response"))
                .andDo(document("auth-LoginResponse",
                        responseFields(
                                fieldWithPath("accessToken").description("엑세스 토큰")
                        )
                ));
    }

}
