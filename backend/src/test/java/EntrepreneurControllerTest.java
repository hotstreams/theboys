import com.theboys.contollers.EntrepreneurController;
import com.theboys.contollers.HeroController;
import com.theboys.data.enums.OrderStatus;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.OrderService;
import com.theboys.to.OrderResponseTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {EntrepreneurController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application-test.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class EntrepreneurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PersistentUserManager userManager;

    @WithMockUser(roles = {"CUSTOMER"})
    @Test
    public void testGetOrders() throws Exception {
        List<OrderResponseTO> orders = new ArrayList<>();
        when(orderService.getOrders(anyInt())).thenReturn(orders);
        mockMvc.perform(get("/entrepreneurs/rents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        orders.add(new OrderResponseTO(1, 2, "Something", "Big guy", "Gang fight", "2020-01-01", "2020-01-03", "2019-10-01", OrderStatus.PENDING));
        mockMvc.perform(get("/entrepreneurs/rents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{
                            "orderId": 1,
                            "enterpreneurId": 2,
                            "name": "Something",
                            "heroDescription":"Big guy",
                            "requestDescription":"Gang fight",
                            "startDate":"2020-01-01",
                            "endDate":"2020-01-03",
                            "date":"2019-10-01",
                            "status": "PENDING"
                        }]
                        """));

    }

    @WithAnonymousUser
    @Test
    public void testGetOrdersUnauthorized() throws Exception {
        List<OrderResponseTO> orders = new ArrayList<>();
        when(orderService.getOrders(anyInt())).thenReturn(orders);
        mockMvc.perform(get("/entrepreneurs/rents"))
                .andExpect(status().isUnauthorized());
        orders.add(new OrderResponseTO(1, 2, "Something", "Big guy", "Gang fight", "2020-01-01", "2020-01-03", "2019-10-01", OrderStatus.PENDING));
        mockMvc.perform(get("/entrepreneurs/rents"))
                .andExpect(status().isUnauthorized());

    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testUpdateOrderStatus() throws Exception {
        mockMvc.perform(patch("/entrepreneurs/1/rents/1").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "status":"WAITING_FOR_CUSTOMER_APPROVAL"
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

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testUpdateOrderStatusInvalidRequestBody() throws Exception {
        mockMvc.perform(patch("/entrepreneurs/1/rents/1").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "status":"WRONG_STATUS"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = {"HERO"})
    @Test
    public void testUpdateOrderStatusForbidden() throws Exception {
        mockMvc.perform(patch("/entrepreneurs/1/rents/1").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "status":"WAITING_FOR_CUSTOMER_APPROVAL"
                        }
                        """))
                .andExpect(status().isForbidden());
    }
}