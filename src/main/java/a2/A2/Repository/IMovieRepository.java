package a2.A2.Repository;
import a2.A2.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}