package com.example.week4.postapplication.Controller;

import com.example.week4.postapplication.Entities.Post;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/audit")
public class AuditController {

    private final EntityManagerFactory entityManagerFactory;

    AuditController( EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory=entityManagerFactory;
    }

    @GetMapping(path = "/posts/{postid}")
    List<Post> getPostRevisionOfASinglePost(@PathVariable(name = "postid") Long postId){

        AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());

        List<Number> revisions = reader.getRevisions(Post.class,postId);

        return revisions.stream()
                .map(revisionNum -> reader.find(Post.class,postId,revisionNum))
                .collect(Collectors.toList());
        //returns a List<Post>, which represent all the versions of 1 particular postId
    }
}
