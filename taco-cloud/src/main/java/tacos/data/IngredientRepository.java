package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> finadAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);

}
