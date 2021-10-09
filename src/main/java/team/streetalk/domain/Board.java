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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String boardName;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<Post>();
    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes = new ArrayList<BoardLike>();
}
