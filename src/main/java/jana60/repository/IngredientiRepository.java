package jana60.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jana60.model.Ingrediente;

@Repository
public interface IngredientiRepository extends CrudRepository<Ingrediente, Integer> {
	public List<Ingrediente> findAllByOrderByNome();
}