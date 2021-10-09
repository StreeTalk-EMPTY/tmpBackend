package team.streetalk.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Industry extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bigIndustry;

    @Column
    private String middleIndustry;

    @OneToMany(mappedBy = "industry")
    private List<User> users = new ArrayList<>();

}
