package it.polimi.db2_project.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "dirtyWord", schema = "db2_app")
@NamedQuery(name = "DirtyWords.CheckSentence", query = "SELECT d FROM DirtyWord d WHERE d.word in ?1")
public class DirtyWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wordId;

    private String word;

}