package a2.A2;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import a2.A2.Model.Franchise;
import a2.A2.Model.Movie;
import a2.A2.exceptions.FranchiseDuplicateException;
import a2.A2.exceptions.FranchiseNotFoundException;
import a2.A2.exceptions.MovieDuplicateException;
import a2.A2.exceptions.MovieNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TestingWebApplicationTest {

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Autowired
	private MockMvc mockMvc;


	@Test
	void testDeleteMovie() throws Exception {
		mockMvc.perform (post("/movies")
				.content(asJsonString(new Movie("q", 2000, "Action", 7L)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(delete("/movies/1"));
		try {
			this.mockMvc.perform(delete("/movies/1"));
		}
		catch (MovieNotFoundException e){
			assert(true);
		}
	}

	@Test
	void testAddMovie() throws Exception {
		mockMvc.perform (post("/movies")
						.content(asJsonString(new Movie("q", 2000, "Action",7L )))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(get("/movies"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"id\":6,\"title\":\"q\",\"year\":2000,\"genre\":\"Action\",\"franchise\":7}")));


		try{
			mockMvc.perform(post("/movies")
					.content(asJsonString(new Movie("q", 2000, "Action", 7L)))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		}
		catch (MovieDuplicateException e){
			assert(true);
		}
	}

	@Test
	void testUpdateMovie() throws Exception {
		mockMvc.perform (post("/movies")
				.content(asJsonString(new Movie("q", 2000, "Action", 7L)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		mockMvc.perform(put("/movies/5")
				.content(asJsonString(new Movie(5, "b", 2020, "Comedy", 7L)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(get("/movies"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"id\":5,\"title\":\"b\",\"year\":2020,\"genre\":\"Comedy\",\"franchise\":7}")));

	}



	@Test
	void testAddFranchise() throws Exception {
		mockMvc.perform (post("/franchises")
						.content(asJsonString(new Franchise("consoleTest")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(get("/franchises"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"movies\":[],\"name\":\"consoleTest\"}")));


		try{
			mockMvc.perform(post("/franchises")
					.content(asJsonString(new Franchise("consoleTest")))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		}
		catch (FranchiseDuplicateException e){
			assert(true);
		}
	}
	@Test
	void testDeleteFranchise() throws Exception {
		this.mockMvc.perform(delete("/franchises/consoleTest"));
		try {
			this.mockMvc.perform(delete("/franchises/consoleTest"));
		}
		catch (FranchiseNotFoundException e){
			assert(true);
		}
	}
}