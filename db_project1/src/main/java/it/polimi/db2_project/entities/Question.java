package it.polimi.db2_project.entities;

import it.polimi.db2_project.entities.ids.QuestionKey;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "question", schema = "db2_app")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    //@Id
    //private int productId;

    private String text;

    @NotNull
    private boolean isMandatory;

    private int questionNumber;

    private Date date;


    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;


    //@ManyToMany
    //@JoinTable(name="answer", //come aggiungo gli attributi della relationship?
    //        joinColumns={@JoinColumn(name="questionId", referencedColumnName = "questionId"),
    //                     @JoinColumn(name="date", referencedColumnName = "date"),},
    //        inverseJoinColumns={@JoinColumn(name="userId")})
    //private Set<Answer> answers;



    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }


    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public int getQuestionId() {
        return questionId;
    }

    public Date getDate() {
        return date;
    }

    public QuestionKey getQuestionKey() {
        return new QuestionKey(questionId, date);
    }

    public String getText() {
        return text;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}