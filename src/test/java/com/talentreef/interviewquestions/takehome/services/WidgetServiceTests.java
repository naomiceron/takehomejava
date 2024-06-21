package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  @Test
  public void when_getAllWidgets_expect_findAllResult() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    List<Widget> response = List.of(widget);
    when(widgetRepository.findAll()).thenReturn(response);

    List<Widget> result = widgetService.getAllWidgets();

    assertThat(result).isEqualTo(response);
  }

  @Test
  public void when_getWidgetById_expect_correctWidget() throws Exception {
    String mockedName = "Widget von Hammersmark";
    Widget widget = Widget.builder().name(mockedName).build();
    when(widgetRepository.findById(mockedName)).thenReturn(Optional.of(widget));

    Widget result = widgetService.getWidgetById(mockedName);

    assertThat(result).isEqualTo(widget);
  }

  @Test(expected = RuntimeException.class)
  public void when_getWidgetById_withInvalidName_expect_exception() {
    String invalidName = "Invalid Widget Name";
    when(widgetRepository.findById(invalidName)).thenReturn(Optional.empty());

    widgetService.getWidgetById(invalidName);
  }

  @Test
  public void when_createOrUpdateWidget_expect_widgetCreatedOrUpdated() throws Exception {
    String mockedName = "Widget von Hammersmark";
    Widget widget = Widget.builder().name(mockedName).build();
    when(widgetRepository.save(any(Widget.class))).thenReturn(widget);

    Widget result = widgetService.createOrUpdateWidget(widget);

    assertThat(result).isEqualTo(widget);
  }

  @Test
  public void when_deleteWidget_expect_noException() throws Exception {
    String mockedName = "Widget von Hammersmark";
    doAnswer(invocation -> null).when(widgetRepository).deleteById(mockedName);

    widgetService.deleteWidget(mockedName);

    verify(widgetRepository, times(1)).deleteById(mockedName);
  }

  @Test(expected = RuntimeException.class)
  public void when_deleteWidget_withInvalidName_expect_exception() {
    String invalidName = "Invalid Widget Name";
    doThrow(new RuntimeException("Widget not found")).when(widgetRepository).deleteById(invalidName);

    widgetService.deleteWidget(invalidName);
  }

  @Test
  public void when_updateWidget_expect_widgetUpdated() throws Exception {
    String mockedName = "Widget von Hammersmark";
    Widget widget = Widget.builder().name(mockedName).build();
    when(widgetRepository.save(any(Widget.class))).thenReturn(widget);

    Widget result = widgetService.updateWidget(widget);

    assertThat(result).isEqualTo(widget);
  }

}
