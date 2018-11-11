package ru.usque.pelican.entities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pelican_category")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NONE)
public class PelicanCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "category_parent_id")
    private Integer categoryParentId;

    @Column(name = "name")
    private String name;

    // простая? (выполнение булевое) - планка.
    @Column(name = "simple")
    private Boolean simple;

    // % в день от планки ?
    @Column(name = "score")
    private Integer score;

    // имеет ли лимит (книга)
    @Column(name = "disposable")
    private Boolean disposable;

    // total limit
    @Column(name = "disposable_capacity")
    private Integer disposableCapacity;

    // сколько сделано из
    @Column(name = "disposable_done")
    private Integer disposableDone;

    //задача выполнена?
    @Column(name = "deprecated")
    private Boolean deprecated;

    @Column(name = "header")
    private Boolean header;
}
