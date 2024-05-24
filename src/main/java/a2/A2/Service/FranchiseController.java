package a2.A2.Service;

import a2.A2.Model.Franchise;
import a2.A2.Repository.IFranchiseRepository;
import a2.A2.exceptions.FranchiseNotFoundException;
import a2.A2.exceptions.MovieDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FranchiseController {
    @Autowired
    private final IFranchiseRepository repository;

    FranchiseController(IFranchiseRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/franchises")
    public @ResponseBody List<Franchise> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/franchises")
    public @ResponseBody String newFranchise(@RequestBody Franchise newFranchise) {
        if(repository.findAll().stream().anyMatch(franchise-> Objects.equals(franchise.getName(), newFranchise.getName())))
        {
            throw new MovieDuplicateException(newFranchise.getName());
        }
        repository.save(newFranchise);
        return "Saved: " + newFranchise.getId();
    }

    // Single item

    @GetMapping("/franchises/{name}")
    Optional<Franchise> one(@PathVariable String name) {
        Optional<Franchise> franchise = repository.findByName(name);
        if(franchise.isPresent()) {
            return repository.findById(franchise.get().getId());
        }
        throw new FranchiseNotFoundException(0L);

    }

    @PutMapping("/franchises/{name}")
    Franchise replaceFranchise(@RequestBody Franchise newFranchise, @PathVariable String name) {
        var f =  repository.findByName(name);
        if(f.isEmpty())
            throw new FranchiseNotFoundException(0L);
        var id = f.get().getId();
        return repository.findById(id)
                .map(franchise -> {
                    franchise.setId(newFranchise.getId());
                    franchise.setName(newFranchise.getName());
                    return repository.save(franchise);
                })
                .orElseGet(() -> {
                    newFranchise.setId(id);
                    return repository.save(newFranchise);
                });
    }

    @DeleteMapping("/franchises/{name}")
    void deleteFranchise(@PathVariable String name) {
        var m =  repository.findByName(name);
        if(m.isEmpty())
            throw new FranchiseNotFoundException(0L);
        var id = m.get().getId();
        repository.findById(id).orElseThrow(() -> new FranchiseNotFoundException(id));
        repository.deleteById(id);
    }

}
