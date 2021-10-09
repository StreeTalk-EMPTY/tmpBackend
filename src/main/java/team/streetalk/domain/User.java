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
public class User extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, length = 100)
    private String password;
    @Column(nullable = false, length = 100, unique = true)
    private String phoneNum;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<Post>();

    @OneToMany(mappedBy = "user")
    private List<Reply> replies = new ArrayList<Reply>();
}
