package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    Widget widget2 = Widget.builder().name("Naomi Ceron").build();
    List<Widget> allWidgets = List.of(widget, widget2);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>(){});
    assertThat(parsedResult).isEqualTo(allWidgets);
  }


  @Test
  public void when_getWidgetById_expect_aWidget() throws Exception {
    String mockedName = "Widget von Hammersmark";
    Widget widget = Widget.builder().name(mockedName).build();

    when(widgetService.getWidgetById(mockedName)).thenReturn(widget);

    MvcResult result = mockMvc.perform(get("/v1/widgets/"+ mockedName))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_getWidgetById_withInvalidName_expect_exception() throws Exception {
    String invalidName = "Invalid Widget Name";
    when(widgetService.getWidgetById(invalidName)).thenThrow(new RuntimeException("Widget not found"));

    mockMvc.perform(get("/v1/widgets/" + invalidName))
            .andExpect(status().isNotFound())
            .andDo(print());
  }

  @Test
  public void when_createOrUpdateWidget_expect_widgetCreatedOrUpdated() throws Exception {
    String mockedName = "Widget von Hammersmark";
    Widget widget = Widget.builder().name(mockedName).build();
    when(widgetService.createOrUpdateWidget(any(Widget.class))).thenReturn(widget);

    MvcResult result = mockMvc.perform(post("/v1/widgets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(widget)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_deleteWidget_expect_noContent() throws Exception {
    String mockedName = "Widget von Hammersmark";
    doNothing().when(widgetService).deleteWidget(any(String.class));

    mockMvc.perform(delete("/v1/widgets/" + mockedName))
            .andExpect(status().isNoContent())
            .andDo(print());
  }

  @Test
  public void when_deleteWidget_withInvalidName_expect_exception() throws Exception {
    String invalidName = "Invalid Widget Name";
    doThrow(new RuntimeException("Widget not found")).when(widgetService).deleteWidget(invalidName);

    mockMvc.perform(delete("/v1/widgets/" + invalidName))
            .andExpect(status().isNotFound())
            .andDo(print());
  }

  @Test
  public void when_updateWidget_expect_widgetUpdated() throws Exception {
    String mockedName = "Widget von Hammersmark";
    Widget widget = Widget.builder().name(mockedName).build();
    when(widgetService.updateWidget(any(Widget.class))).thenReturn(widget);

    MvcResult result = mockMvc.perform(patch("/v1/widgets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(widget)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(parsedResult).isEqualTo(widget);
  }
}
