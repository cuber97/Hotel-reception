package pl.edu.wat.backend.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.backend.entities.ComplaintEntity;

@Repository
public interface ComplaintRepository extends CrudRepository<ComplaintEntity, Integer> {
}
