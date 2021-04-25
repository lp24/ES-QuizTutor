package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Item;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Association;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDto implements Serializable {
    private Integer id;
    private ArrayList<AssociationDto> connections = new ArrayList<>();
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
        connections.add(new AssociationDto(this.id, id));
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConnections(List<Association> associations) {
        if (associations != null) {
            for (Association connection : associations) {
                addConnection(connection.getItemTwo());
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<AssociationDto> getConnections() {
        return connections;
    }

    public boolean checkConnection(AssociationDto connection) {
        for (AssociationDto association : this.connections) {
            if (association == connection) {
                return true;
            }
        }
        return false;
    }
}
