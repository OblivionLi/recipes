package recipes.model.recipe;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeCreateRequest {
    @NotBlank(message = "Recipe name field cannot be empty!")
    private String name;
    @NotBlank(message = "Recipe description field cannot be empty!")
    private String description;
    @NotBlank(message = "Recipe category field cannot be empty!")
    private String category;
    @NotEmpty(message = "Recipe ingredients field cannot be empty!")
    @Size(min = 1, message = "Recipe must have at least one ingredient!")
    private List<String> ingredients;
    @NotEmpty(message = "Recipe directions field cannot be empty!")
    @Size(min = 1, message = "Recipe must have at least one direction!")
    private List<String> directions;
}
