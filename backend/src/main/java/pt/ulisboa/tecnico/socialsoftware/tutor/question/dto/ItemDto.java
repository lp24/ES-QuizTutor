package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDto implements Serializable {
    private Integer id;
    private ArrayList<Integer> connections = new ArrayList<>();
    private String content;

    public ItemDto() { }

    public ItemDto(int id) {
        this.id = id;
    }

    public ItemDto(Item item) {
        this.id = item.getId();
        this.content = item.getContent();
        setConnections(item.getConnections());
    }

    public void addConnection(Integer id) {
        connections.add(id);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConnections(List<Integer> connections) {
        this.connections = (ArrayList<Integer>) connections;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<Integer> getConnections() {
        return connections;
    }

    public boolean checkConnection(int connection) {
        for (int element : this.connections) {
            if (element == connection) {
                return true;
            }
        }
        return false;
    }
}
