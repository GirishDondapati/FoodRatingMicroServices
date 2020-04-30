package com.girish.api.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.girish.api.models.Food;
import com.girish.api.models.FoodSummary;

@RestController
@RequestMapping("/food")
public class FoodResource {
	@Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{foodId}")
    public Food getMovieInfo(@PathVariable("foodId") String foodId) {
        FoodSummary foodSummary = restTemplate.getForObject("https://api.thefooddb.org/3/food/" + foodId + "?api_key=" +  apiKey, FoodSummary.class);
        return new Food(foodId, foodSummary.getTitle(), foodSummary.getOverview());
    	
		/*
		 * List<Food> list = new ArrayList<Food>(); list.add(new Food("1", "Egg Rice",
		 * "egg with rice")); list.add(new Food("2", "Cicken Rice",
		 * "Cicken with rice")); list.add(new Food("3", "Veg Rice", "Veg with rice"));
		 * 
		 * return new Food("3", "Veg Rice", "Veg with rice");
		 */
    	
    	
    	
    }
}
