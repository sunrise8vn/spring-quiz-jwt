package com.cg.model;

import com.cg.model.enums.EUserRole;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
@Accessors(chain = true)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EUserRole name;

    @OneToMany(targetEntity = User.class, mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> users;

}
