package ru.usque.pelican.entities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pelican_plan")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NONE)
public class PelicanPlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PelicanUser user;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "is_grand")
    private Boolean isGrand;

    @Column(name = "score")
    private Integer score;

    @Column(name = "is_finished")
    private Boolean isFinished;

    public Boolean getIsFinished() {
        if (isFinished != null) {
            return isFinished;
        }
        return false;
    }

    public Boolean getIsGrand() {
        if (isGrand == null) {
            return false;
        }
        return isGrand;
    }
}
