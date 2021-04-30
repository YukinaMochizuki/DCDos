package tw.yukina.dcdos.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
public class AbstractEntity {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
}