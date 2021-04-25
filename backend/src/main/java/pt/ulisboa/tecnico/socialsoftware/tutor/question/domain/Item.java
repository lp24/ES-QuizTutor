package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.ItemDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_details_id")
    private ItemCombinationQuestion questionDetails;

    private ArrayList<Integer> connections = new ArrayList<>();

    public Item(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Item(ItemDto item) {
        setContent(item.getContent());
        setConnections(item.getConnections());
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConnections(List<Integer> connections) {
        this.connections = (ArrayList<Integer>) connections;
    }

    public void addConnection(int itemId) {
        this.connections.add(itemId);
    }

    public void remove() {
        this.questionDetails = null;
    }

    public boolean checkConnection(int connection) {
        for (int element : this.connections) {
            if (element == connection) {
                return true;
            }
        }
        return false;
    }

    public ItemCombinationQuestion getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(ItemCombinationQuestion question) {
        this.questionDetails = question;
        question.addItem(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitItem(this);
    }
}
