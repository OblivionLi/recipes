package recipes.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RecipeResponse {
    private String name;
    private String category;
    private LocalDateTime date;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
}
