package com.blogging.blogapplication.Entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "postid")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

}
