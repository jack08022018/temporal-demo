package com.camel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@OptimisticLocking(type = OptimisticLockType.DIRTY)
//@DynamicUpdate
@Entity
@Table(name = "actor")
//@NamedEntityGraph(
//    name = "ActorEntity",
//    attributeNodes = {
//        @NamedAttributeNode("films"),
//    }
//)
public class ActorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Integer actorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Version
    @JsonIgnore
    @Column(name = "version")
    private Integer version;

}
