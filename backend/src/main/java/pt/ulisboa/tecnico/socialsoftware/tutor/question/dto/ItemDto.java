package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDto implements Serializable {
    private Integer id;
    private ArrayList<Integer> connections = new ArrayList<>();
    private String content;

    public ItemDto() { }

    public void addConnection(int _id) {
        connections.add(_id);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getConnections() {
        return connections;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
