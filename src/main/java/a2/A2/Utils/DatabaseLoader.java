package a2.A2.Utils;

import a2.A2.Repository.IMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseLoader {
    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);
    @Bean
    CommandLineRunner initDatabase(IMovieRepository repository) {

        return args -> {
/*            log.info("Preloading " + repository.save(new Movie("a", 2020, "Western", null)));
            log.info("Preloading " + repository.save(new Movie("b", 2021, "Family", null)));
            log.info("Preloading " + repository.save(new Movie("c", 2022, "Action", null)));
            log.info("Preloading " + repository.save(new Movie("d", 1999, "Holiday", null)));
            log.info("Preloading " + repository.save(new Movie("e", 1987, "Family", null)));
            log.info("Preloading " + repository.save(new Movie("f", 1984, "Horror", null)));
            log.info("Preloading " + repository.save(new Movie("g", 1964, "Comedy", null)));
            log.info("Preloading " + repository.save(new Movie("h", 1964, "Horror", null)));
            log.info("Preloading " + repository.save(new Movie("i", 2000, "Romance", null)));
            log.info("Preloading " + repository.save(new Movie("j", 2003, "Drama", null)));*/
        };
    }
}

