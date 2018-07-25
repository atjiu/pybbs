package co.yiiu.web.front;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tomoya at 2018/7/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicControllerTest {

  private final static String REMEMBERMECOOKIE = "Y2YwYTg0NDMtNmI4Ny00ZGU1LTg1ZTItMDI2YzYwNjY1N2Vk";
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void detail() throws Exception {
    MvcResult result = mockMvc.perform(get("/topic/1").header("Cookie", "remember-me=" + REMEMBERMECOOKIE))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void create() throws Exception {
    MvcResult result = mockMvc.perform(get("/topic/create").header("Cookie", "remember-me=" + REMEMBERMECOOKIE))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void edit() throws Exception {
    MvcResult result = mockMvc.perform(get("/topic/edit?id=1").header("Cookie", "remember-me=" + REMEMBERMECOOKIE))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void delete() throws Exception {
    MvcResult result = mockMvc.perform(get("/topic/delete?id=10000000").header("Cookie", "remember-me=" + REMEMBERMECOOKIE))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void tagTopics() throws Exception {
    MvcResult result = mockMvc.perform(get("/topic/tag/字子?pageNo=1"))
        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andReturn();
//    System.out.println(result.getResponse().getContentAsString());
  }
}