package recipes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import recipes.model.recipe.Recipe;
import recipes.model.recipe.RecipeCreateRequest;
import recipes.model.recipe.RecipeResponse;
import recipes.model.user.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Map<String, Long>> createRecipe(RecipeCreateRequest request, Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Recipe recipe = new Recipe(
                request.getName(),
                request.getCategory(),
                request.getDescription(),
                request.getIngredients(),
                request.getDirections()
        );

        recipe.setDate(LocalDateTime.now());
        recipe.setUser(user);
        recipe = recipeRepository.save(recipe);

        user.getRecipes().add(recipe);
        userRepository.save(user);

        Map<String, Long> response = new HashMap<>();
        response.put("id", recipe.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Void> updateRecipe(long id, RecipeCreateRequest request, Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if (existingRecipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!existingRecipe.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Recipe recipe = existingRecipe.get();
        recipe.setName(request.getName());
        recipe.setCategory(request.getCategory());
        recipe.setDescription(request.getDescription());
        recipe.setDirections(request.getDirections());
        recipe.setIngredients(request.getIngredients());

        recipe.setDate(LocalDateTime.now());

        recipeRepository.save(recipe);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<RecipeResponse> getRecipe(long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        RecipeResponse response = new RecipeResponse(
                recipe.get().getName(),
                recipe.get().getCategory(),
                recipe.get().getDate(),
                recipe.get().getDescription(),
                recipe.get().getIngredients(),
                recipe.get().getDirections()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<List<RecipeResponse>> searchRecipe(String category, String name) {
        if (category == null && name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<Recipe> recipes = new ArrayList<>();
        if (category != null) {
            recipes = recipeRepository.findByCategoryDateDesc(category.toLowerCase());
        }

        if (name != null) {
            recipes = recipeRepository.findByNameDateDesc(name.toLowerCase());
        }

        List<RecipeResponse> response = new ArrayList<>();
        if (recipes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        for (Recipe recipe : recipes) {
            response.add(new RecipeResponse(
                    recipe.getName(),
                    recipe.getCategory(),
                    recipe.getDate(),
                    recipe.getDescription(),
                    recipe.getIngredients(),
                    recipe.getDirections()
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> deleteRecipe(long id, Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!recipe.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        recipeRepository.deleteById(recipe.get().getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
