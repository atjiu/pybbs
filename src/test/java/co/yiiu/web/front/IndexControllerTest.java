package co.yiiu.web.front;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tomoya at 2018/7/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void index() throws Exception {
    MvcResult result = mockMvc.perform(get("/?tab=&p=1"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void search() {
  }

  @Test
  public void tags() throws Exception {
    MvcResult result = mockMvc.perform(get("/tags?p=1"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void top100() throws Exception {
    MvcResult result = mockMvc.perform(get("/top100"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void toLogin() throws Exception {
    MvcResult result = mockMvc.perform(get("/login"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void adminlogin() throws Exception {
    MvcResult result = mockMvc.perform(get("/adminlogin"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void toRegister() throws Exception {
    MvcResult result = mockMvc.perform(get("/register"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void logout() throws Exception {
    MvcResult result = mockMvc.perform(get("/logout"))
        .andExpect(status().is3xxRedirection())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }
}