package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bingo_card_statement")
public class BingoCardStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bingo_card")
    private BingoCard bingoCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statement")
    private Statement statement;

    @Column(name = "row")
    private int row;

    @Column(name = "col")
    private int col;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BingoCard getBingoCard() {
        return bingoCard;
    }

    public void setBingoCard(BingoCard bingoCard) {
        this.bingoCard = bingoCard;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return col;
    }

    public void setColumn(int column) {
        this.col = column;
    }
}

