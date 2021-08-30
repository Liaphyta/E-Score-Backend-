package esportapplication.code.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Graph {

  List<Edge> edges;
  List<Node> nodes;

  public Graph()
  {
    this.edges=new ArrayList<>();
    this.nodes=new ArrayList<>();
  }
}
