package com.girish.api.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.girish.api.models.CatalogItem;
import com.girish.api.models.Food;
import com.girish.api.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		/*
		 * List<CatalogItem> list = new ArrayList<CatalogItem>(); list.add(new
		 * CatalogItem("Chicken rice", "Good Spice ans Good Taste", 3)); list.add(new
		 * CatalogItem("Egg rice", "Good Spice ans Good Taste", 4)); list.add(new
		 * CatalogItem("Veg rice", "Good Spice ans Good Taste", 4));
		 * return list;
		 */
    	
    	UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Food movie = restTemplate.getForObject("http://food-info-service/foods/" + rating.getFoodId(), Food.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());
    }
}