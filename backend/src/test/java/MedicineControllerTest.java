import com.theboys.contollers.MedicineController;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.MedicineService;
import com.theboys.to.MedicineTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import testhelpers.SecurityTestConfigurationHelper;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {MedicineController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application-test.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicineService medicineService;

    @WithMockUser(roles = {"SCIENTIST"})
    @Test
    public void testGetMedicine() throws Exception {
        ArrayList<MedicineTO> medicine = new ArrayList<>();
        when(medicineService.getMedicines()).thenReturn(medicine);
        mockMvc.perform(get("/medicines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        medicine.add(new MedicineTO(2, "Batino pivo"));
        mockMvc.perform(get("/medicines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{
                            "id": 2,
                            "name": "Batino pivo"
                        }]
                        """));

    }

    @WithMockUser(roles = {"MANAGER", "HERO", "CUSTOMER", "CANDIDATE"})
    @Test
    public void testGetMedicineForbidden() throws Exception {
        mockMvc.perform(get("/medicines"))
                .andExpect(status().isForbidden());
    }

}