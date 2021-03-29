package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDto implements Serializable {
    private Integer id;
    private final ArrayList<Integer> connections = new ArrayList<>();
    private String content;

    public ItemDto() { }

    public ItemDto(int id) {
        this.id = id;
    }

    public void addConnection(Integer id) {
        connections.add(id);
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
