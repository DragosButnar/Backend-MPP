package a2.A2.Service;

import a2.A2.Model.Movie;
import a2.A2.Repository.IFranchiseRepository;
import a2.A2.Repository.IMovieRepository;
import a2.A2.exceptions.FranchiseNotFoundException;
import a2.A2.exceptions.MovieDuplicateException;
import a2.A2.exceptions.MovieLogicException;
import a2.A2.exceptions.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
    @Autowired
    private final IMovieRepository repository;

    @Autowired
    private final IFranchiseRepository franchiseRepository;

    public MovieController(IMovieRepository repository, IFranchiseRepository franchiseRepository) {
        this.repository = repository;
        this.franchiseRepository = franchiseRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/movies")
    public @ResponseBody List<Movie> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/movies")
    public @ResponseBody String newMovie(@RequestBody Movie newMovie) {
        if(repository.findAll().stream().anyMatch(movie-> Objects.equals(movie.getTitle(), newMovie.getTitle())))
        {
            throw new MovieDuplicateException(newMovie.getTitle());
        }
        if(newMovie.getYear() < 0){
            throw new MovieLogicException(newMovie.getTitle());
        }
        if(!franchiseRepository.existsById(newMovie.getFranchise())){
            throw new FranchiseNotFoundException(newMovie.getFranchise());
        }
        repository.save(newMovie);
        return "Saved: " + newMovie.getId();
    }

    // Single item

    @GetMapping("/movies/{title}")
    public Optional<Movie> one(@PathVariable String title) {
        var movie = repository.findByTitle(title);
        if(movie.isEmpty())
            throw new MovieNotFoundException(title);
        return movie.map(value -> repository.findById(value.getId())).get();
    }

    @PutMapping("/movies/{title}")
    Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable String title) {
        var m =  repository.findByTitle(title);
        if(m.isEmpty())
            throw new MovieNotFoundException(title);
        return m
                .map(movie -> {
                    movie.setTitle(newMovie.getTitle());
                    movie.setYear(newMovie.getYear());
                    movie.setGenre(newMovie.getGenre());
                    movie.setFranchise(newMovie.getFranchise());
                    return repository.save(movie);
                })
                .orElseGet(() -> {
                    newMovie.setId(m.get().getId());
                    return repository.save(newMovie);
                });
    }

    @DeleteMapping("/movies/{title}")
    void deleteMovie(@PathVariable String title) {
        repository.findByTitle(title).orElseThrow(() -> new MovieNotFoundException(title));
        var m = repository.findByTitle(title);
        repository.deleteById(m.get().getId());
    }

}
