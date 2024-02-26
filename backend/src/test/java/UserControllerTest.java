import com.theboys.contollers.UserController;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import testhelpers.SecurityTestConfigurationHelper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {UserController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application-test.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @WithAnonymousUser
    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "username":"cringe",
                            "password":"moreCringe"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        """
                        {
                            "status":"OK"
                        }
                        """));
    }

    @WithAnonymousUser
    @Test
    public void testRegisterInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "username":"",
                            "password":""
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = {"HERO", "SCIENTIST", "MANAGER", "USER", "CANDIDATE", "CUSTOMER"})
    @Test
    public void testRegisterForbidden() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "username":"cringe",
                            "password":"moreCringe"
                        }
                        """))
                .andExpect(status().isForbidden());
    }
}