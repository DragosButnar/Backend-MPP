package a2.A2.Utils;

import a2.A2.Model.Movie;
import a2.A2.Service.MovieController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {

        return EntityModel.of(movie, //
                linkTo(methodOn(MovieController.class).one(movie.getTitle())).withSelfRel(),
                linkTo(methodOn(MovieController.class).all()).withRel("movies"));
    }
}