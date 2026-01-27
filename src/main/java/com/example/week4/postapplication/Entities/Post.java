package com.example.week4.postapplication.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity @Getter @Setter @NoArgsConstructor  @AllArgsConstructor
@Table(name = "posts")
@Audited
public class Post extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    private String description;

    //Lifecycle hooks for database, can be useful in some cases, like creating our own auditing
    @PrePersist
    void beforeSave(){}

    @PreUpdate
    void beforeUpdate(){}

    @PreRemove
    void beforeDelete(){}


}
