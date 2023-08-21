package recipes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.model.recipe.RecipeCreateRequest;
import recipes.model.recipe.RecipeResponse;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
@Validated
@Slf4j
public class RecipeController {

    @Autowired
    RecipeService recipeService;
    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> createRecipe(@NotNull @Valid @RequestBody RecipeCreateRequest request, Authentication authentication) {
        return recipeService.createRecipe(request, authentication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable long id, @NotNull @Valid @RequestBody RecipeCreateRequest request, Authentication authentication) {
        return recipeService.updateRecipe(id, request, authentication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable long id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<RecipeResponse>> searchRecipe(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name
    ) {
        return recipeService.searchRecipe(category, name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id, Authentication authentication) {
        return recipeService.deleteRecipe(id, authentication);
    }
}
