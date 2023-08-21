package recipes.model.recipe;

import lombok.Getter;
import lombok.Setter;
import recipes.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String category;
    @NotNull
    @NotEmpty
    private String description;
    private LocalDateTime date;
    @ElementCollection
    @NotEmpty
    @OrderColumn
    private List<String> ingredients;
    @ElementCollection
    @NotEmpty
    @OrderColumn
    private List<String> directions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe() {
    }

    public Recipe(String name, String category, String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
