package tacos.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.IngredientRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo) {

        this.ingredientRepo = ingredientRepo;
    }


    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredientRepo.finadAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        log.info("Returning view name");
        return "design";
    }


    /*
        private List<Ingredient> makeIngredients() {
            List<Ingredient> ingredients = Arrays.asList(
                    new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                    new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                    new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                    new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                    new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                    new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                    new Ingredient("CHED", "Cheddar", Type.CHEESE),
                    new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                    new Ingredient("SLSA", "Salsa", Type.SAUCE),
                    new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
            );

            return ingredients;
        }

    */
    @PostMapping
    public String processDesign(@Valid Taco design, BindingResult errors, Model model) {
        //public String processDesign(@Valid Taco design, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Form has eror " + errors);
            model.addAttribute("design", design);
            return "design";
        }
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
