package com.talentreef.interviewquestions.takehome.respositories;

import com.talentreef.interviewquestions.takehome.models.Widget;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class WidgetRepository {

  private List<Widget> table = new ArrayList<>();

  public WidgetRepository(List<Widget> table) {
    this.table = table;
    setWidgetTestData(table);
  }
  private void setWidgetTestData(List<Widget> table){
    table.add(Widget.builder().name("Widgette Nielson").description("Keeps a diary").price(10.2).build());
    table.add(Widget.builder().name("Naomi Ceron").description("Takes home a challenge").price(99.99).build());
    table.add(Widget.builder().name("Jorge Reyes").description("Is a great dev").price(10).build());
  }

  public List<Widget> deleteById(String name) {
    this.table = table.stream()
                      .filter((Widget widget) -> !name.equals(widget.getName()))
                      .collect(Collectors.toCollection(ArrayList::new));
    return table;
  }

  public List<Widget> findAll() {
    return table;
  }

  public Optional<Widget> findById(String name) {
    Optional<Widget> result = table.stream()
                                   .filter((Widget widget) -> name.equals(widget.getName()))
                                   .findAny();
    return result;
  }

  public Widget save(Widget widget) {
    if(findById(widget.getName()).isPresent()){
      deleteById(widget.getName());
    }
    table.add(widget);
    return widget;
  }

}
