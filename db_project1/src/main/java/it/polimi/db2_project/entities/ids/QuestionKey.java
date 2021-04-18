package it.polimi.db2_project.entities.ids;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class QuestionKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "questionId")
    private int questionId;

    @Column(name = "productId")
    private int productId;

    @Column(name = "date")
    private Date date;

    public QuestionKey() {

    }

    public QuestionKey(int questionId, Date date,int productId) {
        this.productId = productId;
       this.questionId = questionId;
       this.date = date;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionKey that = (QuestionKey) o;
        return questionId == that.questionId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, productId);
    }
}
