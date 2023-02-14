package com.blogging.blogapplication.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    private Long id;

    private String name;

}
