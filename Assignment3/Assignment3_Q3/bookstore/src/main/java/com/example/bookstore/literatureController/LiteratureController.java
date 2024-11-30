package com.example.bookstore.literatureController;

import com.example.bookstore.literatureModel.Literature;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LiteratureController { // Updated to follow proper naming conventions

    @GetMapping("/")
    public String displayLiterature(@RequestParam(required = false) String genre, Model model) {
        // Sample data
        List<Literature> allLiterature = Arrays.asList(
            new Literature("1984", "George Orwell", "Dystopian", 8.99),
            new Literature("The Hobbit", "J.R.R. Tolkien", "Fantasy", 6.99),
            new Literature("Pride and Prejudice", "Jane Austen", "Classic", 5.99),
            new Literature("Neuromancer", "William Gibson", "Sci-Fi", 7.99)
        );

        List<Literature> literatureToDisplay;

        if (genre == null || genre.isBlank()) {
            // If no genre is specified, display all literature
            literatureToDisplay = allLiterature;
        } else {
            // Filter literature by genre
            literatureToDisplay = allLiterature.stream()
                .filter(l -> l.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
        }

        // Add the list to the model
        model.addAttribute("literature", literatureToDisplay);
        return "literature"; // View name
    }
}

