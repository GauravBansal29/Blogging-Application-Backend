package com.blogging.blogapplication.Entities;

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postid")
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 10000)
    private String content;

    // will contain the image url
    @Column(name = "image")
    private String image;

    @Column(name = "date_created")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    // will also have a one to many for comments -> will make it later
}
