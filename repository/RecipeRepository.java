package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recipes.model.recipe.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT DISTINCT r FROM Recipe r WHERE LOWER(r.category) = LOWER(:category) ORDER BY r.date DESC, LENGTH(r.name) DESC, r.name DESC")
    List<Recipe> findByCategoryDateDesc(@Param("category") String category);

    @Query("SELECT DISTINCT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY r.date DESC")
    List<Recipe> findByNameDateDesc(@Param("name") String name);
}
