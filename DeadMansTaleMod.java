import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

@ModEntry
public class DeadMansTaleMod {

    public void init(){
        ItemRegistry.registerItem("deadmanstale", new DeadMansTale(), 400, true);
    }

    public void postInit(){
        Recipes.registerModRecipe(new Recipe(
                "deadmanstale",
                1,
                RecipeTechRegistry.NONE,
                new Ingredient[]{
                        new Ingredient("oaklog", 1)
                }
        ).showAfter("woodboat"));

        Recipes.registerModRecipe(new Recipe(
                "simplebullet",
                1000,
                RecipeTechRegistry.NONE,
                new Ingredient[]{
                        new Ingredient("sprucelog", 1)
                }
        ).showAfter("woodboat"));
    }
}
