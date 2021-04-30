package it.polimi.db2_project.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.*;
import java.util.*;

@Entity
@Table(name = "product", schema = "db2_app")
@NamedQuery(name = "Product.getProductDummy", query = "SELECT p FROM Product p WHERE p.name = ?1")
@NamedQuery(name = "Product.getProductOfTheDay", query = "SELECT p FROM Product p WHERE p.date = ?1")
@NamedQuery(name = "Product.getProduct", query = "SELECT p FROM Product p  WHERE p.productId = ?1")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    //auto-incremented id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotNull
    private String name;

    private String description;
    @Lob
    private byte[] image;


    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public Product(){

    }
    @OneToMany(mappedBy = "product") //amount of questions is limited
    private List<Question> questions;

    @OneToMany(mappedBy = "reviewedProduct")  // Nel mappedBy metto il nome dell'attributo nella classe che ha relazione
    private List<Review> reviews;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question q){
        questions.add(q);
    }

    public void removeQuestion(Question q){
        questions.remove(q);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

}