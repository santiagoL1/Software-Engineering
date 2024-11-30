package com.example.randomquotedisplay.quoteDisplayController;

import com.example.randomquotedisplay.quoteDisplayModel.Quote;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
@Controller
public class QuoteDisplayController {
private List<Quote> quotes = Arrays.asList(
new Quote("To be or not to be, that is the question.", "William Shakespeare"),
new Quote("Knowledge is power.", "Francis Bacon"),
new Quote("Life is what happens when you're busy making other plans.", "JohnLennon")
);
@GetMapping("/")
public String displayRandomQuote(Model model) {
Random rand = new Random();
Quote randomQuote = quotes.get(rand.nextInt(quotes.size()));
model.addAttribute("quote", randomQuote);
return "quotes"; // Name of the HTML view
}
}
