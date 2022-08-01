package jana60.controller;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jana60.model.Ingrediente;
import jana60.repository.IngredientiRepository;

@Controller
@RequestMapping("/ingrediente")
public class IngredientiController {

	@Autowired
	private IngredientiRepository repo;

	@GetMapping("/ingrediente'")
	public String pizza(Model model) {
		List<Ingrediente> IngredienteList = (List<Ingrediente>) repo.findAll();
		model.addAttribute("IngredienteList", IngredienteList);
		return "/ingrediente";
	}

	@GetMapping
	public String pizzaForm(Model model) {
		model.addAttribute("ingrediente", repo.findAllByOrderByNome());
		model.addAttribute("newIngrediente", new Ingrediente());
		return "/ingrediente";
	}

	@PostMapping("/save")
	public String saveIngrediente(@Valid @ModelAttribute("newIngrediente") Ingrediente formIngrediente,
			BindingResult br, Model model) {
		if (br.hasErrors()) {
			
			model.addAttribute("ingrediente", repo.findAllByOrderByNome());
			return "redirect/ingrediente";

		} else {
			
			repo.save(formIngrediente);
			return "redirect:/ingrediente";
		}
	}
	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") Integer ingredienteId, Model model) {
		Optional<Ingrediente> result = repo.findById(ingredienteId);
		if (result.isPresent()) {
			model.addAttribute("newIngrediente", result.get());
			repo.save(result.get());
			return "/ingrediente";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Ingrediente con id " + ingredienteId + "Non esiste");
		}

	}
}
	