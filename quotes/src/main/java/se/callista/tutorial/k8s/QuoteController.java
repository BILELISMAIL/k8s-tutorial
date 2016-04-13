package se.callista.tutorial.k8s;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuoteController {

    private static final Log LOG = LogFactory.getLog(QuoteController.class);
    
	List<String> quotes;
	Random random = new Random();
	
	public QuoteController() {
		quotes = Arrays.<String>asList("To be or not to be", "You, too, Brutus?", "Champagne should be cold, dry and free");
	}
    
    @RequestMapping("/quote")
    public Quote quote(@RequestParam(value="language", defaultValue="en") String language) {
        if (QuotesHealthIndicator.isAlive) {
            String s = quotes.get(random.nextInt(quotes.size()));
            Quote quote = new Quote();
            quote.setQuote(s);
            quote.setLanguage(language);
            LOG.info("Delivered quote: '" + s + "'");
            return quote;
        } else {
            return null;
        }
    }

    @RequestMapping("/poison")
    public String poison() {
        QuotesHealthIndicator.isAlive = false;
        LOG.info("Took posion");
        return "Ouch!";
    }
}