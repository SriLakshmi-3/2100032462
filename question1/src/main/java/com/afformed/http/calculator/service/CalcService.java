package com.afformed.http.calculator.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.afformed.http.calculator.dto.CalcEntityResponse;
import com.afformed.http.calculator.dto.CalcResponse;

@Service
public class CalcService {

	
	@Value("${test.server.url}")
    private String testServerUrl;

    @Value("${window.size}")
    private int windowSize;

    @Value("${bearer.token}")
    private String bearerToken;

    private final Set<Integer> numberWindow = new LinkedHashSet<>();
    
    
    private final RestTemplate restTemplate;
    
    @Autowired
    public CalcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CalcResponse getNumbers(String t) {
        
    	t= t.equalsIgnoreCase("e")?"even":t.equalsIgnoreCase("p")?"primes":t.equalsIgnoreCase("f")?"fibo":"Invalid Input";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
        	ResponseEntity<CalcEntityResponse> response = restTemplate.exchange(
        
                testServerUrl + t,
                HttpMethod.GET,
                entity,
                CalcEntityResponse.class);

        List<Integer> numbers = response.getBody().getNumbers();

        List<Integer> prevState = new ArrayList<>(numberWindow);
        
        List<Integer> newNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (numberWindow.add(number)) {
                newNumbers.add(number);
            }
        }

        while (numberWindow.size() > windowSize) {
            Iterator<Integer> iterator = numberWindow.iterator();
            iterator.next();
            iterator.remove(); 
        }

       
        List<Integer> currState = new ArrayList<>(numberWindow);

        double average = 0;
        if (!numberWindow.isEmpty()) {
            int sum = 0;
            for (Integer number : numberWindow) {
                sum += number;
            }
            average = (double) sum / numberWindow.size();
        }
        return new CalcResponse(newNumbers, prevState, currState, average);
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                throw new RuntimeException("Unauthorized access - please check your Bearer token.");
            } else if (e.getStatusCode().value() == 408) {
                throw new RuntimeException("Request timeout - the server took too long to respond.");
            }
        } catch (Exception e) {
           
            throw new RuntimeException("Error occurred while fetching numbers from the server: " + e.getMessage());
        }
        
        return new CalcResponse(null, null, null,0.0 );
    }
}
