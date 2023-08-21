package recipes.model.user;

import lombok.Getter;
import lombok.Setter;
import recipes.model.recipe.Recipe;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
