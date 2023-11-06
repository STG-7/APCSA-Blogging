---
toc: true
comments: false
layout: post
title: Final Trimester Reflection
description: Final Trimester Reflectoin - Individua Review
type: hacks
courses: { 'csa': {'week':12} }
---

# Individual Code

## Backend - Cancer API and Quotes API

### Cancer.java

```
package com.nighthawk.spring_portfolio.mvc.cancer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cancer {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String cancerType;

    // Attributes
    
    private int numOfPeopleAffected;
    private double deathRate;
    private int averageRecoveryTime;
    private String symptoms;

    // Array Setup
    public static Cancer[] init() {
        final Cancer[] cancersArray = {
            new Cancer("Breast", 276480, 0.142, 4, "Lump in the breast, change in breast size or shape, nipple discharge, skin changes"),
            new Cancer("Lung", 235760, 0.254, 5, "Persistent cough, chest pain, shortness of breath, coughing up blood"),
            new Cancer("Kidney", 76120, 0.044, 5, "Blood in urine, back pain, weight loss, fatigue"),
            new Cancer("Colon", 149500, 0.090, 6, "Change in bowel habits, rectal bleeding, abdominal pain, fatigue"),
            new Cancer("Leukemia", 60960, 0.183, 3, "Fatigue, frequent infections, easy bleeding or bruising, weight loss"),
            new Cancer("Prostate", 248530, 0.041, 5, "Frequent urination, difficulty starting or stopping urination, blood in urine or semen"),
            new Cancer("Thyroid", 41120, 0.007, 4, "Neck lump, hoarseness, difficulty swallowing, unexplained weight loss"),
            new Cancer("Liver", 42820, 0.162, 7, "Jaundice, abdominal pain, unexplained weight loss, fatigue"),
            new Cancer("Stomach", 26950, 0.098, 5, "Indigestion, abdominal pain, unexplained weight loss, nausea"),
            new Cancer("Skin", 106110, 0.015, 4, "Changes in the skin, moles, or birthmarks, sores that won't heal, unusual bleeding or changes in existing moles"),
            new Cancer("Pancreatic", 60940, 0.077, 6, "Jaundice, abdominal pain, unexplained weight loss, fatigue"),
            new Cancer("Ovarian", 219440, 0.061, 5, "Abdominal bloating, pelvic pain, feeling full quickly, frequent urination"),
            new Cancer("Bladder", 83460, 0.030, 4, "Blood in urine, frequent urination, pain during urination, back or pelvic pain"),
            new Cancer("Small Intestine", 12340, 0.057, 5, "Abdominal pain, unexplained weight loss, blood in stool, fatigue"),
            new Cancer("Brain Cancer", 24260, 0.158, 4, "Headaches, seizures, changes in vision, difficulty with balance or walking")
        };
        return cancersArray;
    }
    


    public Cancer(String cancerType, int numOfPeopleAffected, double deathRate, int averageRecoveryTime, String symptoms) {
        this.cancerType = cancerType;
        this.numOfPeopleAffected = numOfPeopleAffected;
        this.deathRate = deathRate;
        this.averageRecoveryTime = averageRecoveryTime;
        this.symptoms = symptoms;
    }
}
```

### CancerApiController.java

```
package com.nighthawk.spring_portfolio.mvc.cancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable CORS for all origins and headers
@RestController
@RequestMapping("/api/cancers")
public class CancerApiController {

    @Autowired
    private CancerJpaRepository repository;

    // Retrieve all cancer records
    @GetMapping("/")
    public ResponseEntity<List<Cancer>> getCancers() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    // Retrieve cancers by death rate
    @GetMapping("/byDeathRate/{rate}")
    public ResponseEntity<List<Cancer>> getCancersByDeathRate(@PathVariable double rate) {
        List<Cancer> cancers = repository.findByDeathRate(rate);
        if (cancers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cancers, HttpStatus.OK);
    }

    // Retrieve cancers by cancer type (case-insensitive)
    @GetMapping("/byCancerType/{type}")
    public ResponseEntity<List<Cancer>> getCancersByCancerType(@PathVariable String type) {
        List<Cancer> cancers = repository.findByCancerTypeIgnoreCase(type);
        if (cancers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cancers, HttpStatus.OK);
    }

    // Retrieve cancers by the number of people affected
    @GetMapping("/byNumOfPeopleAffected/{num}")
    public ResponseEntity<List<Cancer>> getCancersByNumOfPeopleAffected(@PathVariable int num) {
        List<Cancer> cancers = repository.findByNumOfPeopleAffected(num);
        if (cancers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cancers, HttpStatus.OK);
    }

    // Retrieve cancers by average recovery time
    @GetMapping("/byAverageRecoveryTime/{time}")
    public ResponseEntity<List<Cancer>> getCancersByAverageRecoveryTime(@PathVariable int time) {
        List<Cancer> cancers = repository.findByAverageRecoveryTime(time);
        if (cancers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cancers, HttpStatus.OK);
    }

    // Retrieve cancers by symptoms
    @GetMapping("/bySymptoms/{symptoms}")
    public ResponseEntity<List<Cancer>> getCancersBySymptoms(@PathVariable String symptoms) {
        List<Cancer> cancers = repository.findBySymptoms(symptoms);
        if (cancers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cancers, HttpStatus.OK);
    }

    // Add a new cancer record
    @PostMapping("/add")
    public ResponseEntity<Cancer> addCancer(@RequestBody Cancer cancer) {
        repository.save(cancer);
        return new ResponseEntity<>(cancer, HttpStatus.CREATED);
    }

    // Update an existing cancer record by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Cancer> updateCancer(@PathVariable long id, @RequestBody Cancer updatedCancer) {
        Optional<Cancer> optional = repository.findById(id);
        if (optional.isPresent()) {
            Cancer existingCancer = optional.get();
            // Update the existing cancer record
            existingCancer.setCancerType(updatedCancer.getCancerType());
            existingCancer.setNumOfPeopleAffected(updatedCancer.getNumOfPeopleAffected());
            existingCancer.setDeathRate(updatedCancer.getDeathRate());
            existingCancer.setAverageRecoveryTime(updatedCancer.getAverageRecoveryTime());
            existingCancer.setSymptoms(updatedCancer.getSymptoms());

            repository.save(existingCancer);
            return new ResponseEntity<>(existingCancer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a cancer record by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCancer(@PathVariable long id) {
        if (repository.existsById(id)) {
            // Delete the cancer record
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

```

### CancerJpaRepository.java

```
package com.nighthawk.spring_portfolio.mvc.cancer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CancerJpaRepository extends JpaRepository<Cancer, Long> {
      // Custom query method to find by cancer type ignoring case

    List<Cancer> findByDeathRate(double deathRate);

    List<Cancer> findByNumOfPeopleAffected(int numOfPeopleAffected);

    List<Cancer> findByAverageRecoveryTime(int averageRecoveryTime);

    List<Cancer> findBySymptoms(String symptoms);

    List<Cancer> findByCancerTypeIgnoreCase(String cancerType);

    // You can add more custom query methods as needed for your application.
}
```

### Quotes.java

```
package com.nighthawk.spring_portfolio.mvc.quotes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String quote;




    
    public static String[] init() {
        final String[] quotesArray = {
            "Every day is a gift, and even in the toughest times, there are still reasons to smile.",
            "You are stronger than you think, and braver than you know. Keep fighting.",
            "'Never, never, never give up.' - Winston Churchill",
            "'The human spirit is stronger than anything that can happen to it.' - C.C. Scott",
            "You were given this life because you are strong enough to live it.",
            "Strength doesn't come from what you can do; it comes from overcoming the things you once thought you couldn't.",
            "You are not alone on this journey. We are here to support and love you every step of the way.",
            "You are an inspiration to everyone who knows you. Your strength is a beacon of hope.",
            "Hope is the only thing stronger than fear.",
            "Your journey is tough, but it's making you a warrior. Keep going.",
            "'In the middle of every difficulty lies opportunity.' - Albert Einstein",
            "You have a 100% track record of getting through difficult days. You're doing great.",
            "Life is a collection of moments. Make each one beautiful, no matter the circumstances.",
            "Every accomplishment starts with the decision to try.",
            "The strongest people are not those who show strength in front of us but those who win battles we know nothing about.",
            "The best view comes after the hardest climb.",
            "No matter how much it hurts now, someday you will look back and realize your struggles changed your life for the better.",
            "Every day is a new beginning. Take a deep breath, smile, and start again.",
            "'You are the master of your fate; you are the captain of your soul.' - Invictus",
            "The sun is a daily reminder that we too can rise again from the darkness, that we too can shine our own light."
        };
        return quotesArray;
    }
}
```

### QuotesApiController.java

```
package com.nighthawk.spring_portfolio.mvc.quotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*") // added this line
@RestController
@RequestMapping("/api/quotes")
public class QuotesApiController {

    @Autowired
    private QuotesJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Quotes>> getQuotes() {
        // ResponseEntity returns List of Quotes provided by JPA findAll()
        List<Quotes> quotes = repository.findAll();
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quotes> getQuoteById(@PathVariable Long id) {
        Optional<Quotes> optional = repository.findById(id);
        return optional.map(quotes -> new ResponseEntity<>(quotes, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

   // This code defines an endpoint that, when accessed with a GET request to "/random," retrieves a random quote from a collection of quotes stored in the database
    @GetMapping("/random")
    public ResponseEntity<Quotes> getRandomQuote() {
        List<Quotes> allQuotes = repository.findAll();
        if (allQuotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Generate a random index to select a random quote
        int randomIndex = (int) (Math.random() * allQuotes.size());
        Quotes randomQuote = allQuotes.get(randomIndex);

        return new ResponseEntity<>(randomQuote, HttpStatus.OK);
    }
}

```

### QuotesJpaRepository.java
```
package com.nighthawk.spring_portfolio.mvc.quotes;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesJpaRepository extends JpaRepository<Quotes, Long> {
    Optional<Quotes> findById(int id);
    Optional<Quotes> findByQuote(String quote);

    // You don't need to define a custom save method, as JpaRepository provides it.
    // You can also remove the other custom methods that are not needed.

    // Additional custom methods can be added as needed.
    List<Quotes> findAllByOrderByQuoteAsc();
    List<Quotes> findByQuoteIgnoreCase(String quote);
}

```


### Modelinit.java

```
package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.jokes.JokesJpaRepository;
import com.nighthawk.spring_portfolio.mvc.note.NoteJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.quotes.Quotes;
import com.nighthawk.spring_portfolio.mvc.quotes.QuotesJpaRepository;
import com.nighthawk.spring_portfolio.mvc.cancer.Cancer;
import com.nighthawk.spring_portfolio.mvc.cancer.CancerJpaRepository;
import com.nighthawk.spring_portfolio.mvc.memorial.Memorial;
import com.nighthawk.spring_portfolio.mvc.memorial.MemorialJpaRepository;
import java.util.Optional;


import java.util.List;

@Component
@Configuration
public class ModelInit {
    @Autowired
    JokesJpaRepository jokesRepo;
    @Autowired
    NoteJpaRepository noteRepo;
    @Autowired
    PersonDetailsService personService;
    @Autowired
    CancerJpaRepository cancerRepo;
    @Autowired
    MemorialJpaRepository memorialRepo; // Assuming you have a MemorialJpaRepository

    @Bean
    CommandLineRunner init() {
        return args -> {
            // Joke database is populated with starting jokes (as you did before)

            // Person database is populated with test data (as you did before)

            // Initialize the cancer database with "get" methods for each attribute
            Cancer[] cancersArray = Cancer.init();
            for (Cancer cancer : cancersArray) {
                List<Cancer> cancerFound = cancerRepo.findByCancerTypeIgnoreCase(cancer.getCancerType());
               // The purpose of this if statement is to check if a record with the same cancerType (a property of the Cancer class) does not already exist in the database. If no record with the same cancerType is found, it creates a new Cancer object and saves it in the database. This ensures that there are no duplicate records with the same cancerType.
                if (cancerFound.isEmpty()) {
                    Cancer newCancer = new Cancer();
                    newCancer.setCancerType(cancer.getCancerType());
                    newCancer.setNumOfPeopleAffected(cancer.getNumOfPeopleAffected());
                    newCancer.setDeathRate(cancer.getDeathRate());
                    newCancer.setAverageRecoveryTime(cancer.getAverageRecoveryTime());
                    newCancer.setSymptoms(cancer.getSymptoms());
                    cancerRepo.save(newCancer);
                }
            }

            // Initialize the memorial database with "get" methods for each attribute
            Memorial[] memorialsArray = Memorial.init();
            for (Memorial memorial : memorialsArray) {
                List<Memorial> memorialFound = memorialRepo.findByCancerTypeIgnoreCase(memorial.getCancerType());
                // Same thing for memorials
                if (memorialFound.isEmpty()) {
                    Memorial newMemorial = new Memorial();
                    newMemorial.setName(memorial.getName());
                    newMemorial.setAge(memorial.getAge());
                    newMemorial.setCancerType(memorial.getCancerType());
                    newMemorial.setFavoriteMemory(memorial.getFavoriteMemory());
                    newMemorial.setTreatmentType(memorial.getTreatmentType());
                    memorialRepo.save(newMemorial);
                }
            }
        };
    }

    @Autowired
    QuotesJpaRepository quotesRepo; // Assuming you have a QuotesJpaRepository

    @Bean
    CommandLineRunner initQuotes() {
        return args -> {
            // Initialize the quotes database with some quotes
            String[] quotesArray = Quotes.init();
            for (String quote : quotesArray) {
                Optional<Quotes> existingQuote = quotesRepo.findByQuote(quote);
                if (!existingQuote.isPresent()) {
                    Quotes newQuote = new Quotes();
                    newQuote.setQuote(quote);
                    quotesRepo.save(newQuote);
                }
            }
        };
    }
}


```

## Frontend - Database

### - Data.html

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Graph Representation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }
        .container {
            display: flex;
            flex-direction: row;
            align-items: center;
        }
        .bar-chart {
            width: 300px;
            height: 200px;
            display: flex;
            align-items: flex-end;
            justify-content: space-around;
        }
        .bar {
            width: 40px;
            background-color: #00bcd4;
            margin: 0 5px;
        }
        .pie-chart {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background: conic-gradient(red, yellow, green, blue);
        }
        select, button {
            margin: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div>
            <select id="cancerType">
                <option value="cancer1">Cancer Type 1</option>
                <option value="cancer2">Cancer Type 2</option>
                <!-- Add other cancer types here -->
            </select>
            <select id="graphType">
                <option value="bar">Bar Chart</option>
                <option value="pie">Pie Chart</option>
            </select>
            <button>Search</button>
        </div>
        
        <div class="bar-chart">
            <!-- Modify the height percentages as per actual data -->
            <div class="bar" style="height: 20%;"></div>
            <div class="bar" style="height: 40%;"></div>
            <div class="bar" style="height: 60%;"></div>
            <div class="bar" style="height: 80%;"></div>
            <div class="bar" style="height: 100%;"></div>
        </div>

        <div class="pie-chart"></div>
    </div>

    <script>
        // You can add interactivity here using JavaScript
    </script>
</body>
</html>

```

# Individual Blogging

## Things I have Learned

I have had some java experience with previous interships/jobs

- Deeper fundamentals of ArrayLists
- Iterating through Arrays/2D Arrays/ArrayLists improved
- Databases & Java- Backend of Project


## Positive Accomplishments

- Able to do the bulk of my team's backend and showcase my expertise in Java
- Stay on top of my assignments, accomplish in class, better than last year


## Things I can learn in future trimesters

Advanced Java Concepts: You can delve deeper into Java by learning more advanced topics such as multithreading, design patterns, and JavaFX for building graphical user interfaces.

Database Management: Since you mentioned working with databases, you can expand your knowledge by learning about more complex database systems, data modeling, and how to optimize database queries for performance.

Web Development: If you're interested in expanding your skill set, you could consider learning web development with Java. This would include technologies like Servlets, JSP (JavaServer Pages), and frameworks like Spring to build web applications.

Version Control: It's crucial to learn how to use version control systems like Git effectively. This skill is essential for collaborative software development.

Software Design and Architecture: Understanding software architecture principles and design patterns can help you create more robust and maintainable code.




## Oppurtunities for Growth

Collaboration: Consider opportunities to work on group projects or collaborate with peers. This will not only enhance your teamwork and communication skills but also provide exposure to different perspectives and problem-solving approaches.

Networking: Building a professional network is important. Attend relevant conferences, meetups, and online forums to connect with others in your field. Networking can open up new opportunities and provide you with valuable insights.

Certifications: Depending on your career goals, you might want to pursue relevant certifications in Java or related technologies. Certifications can demonstrate your expertise to potential employers.

Mentorship: Seek out mentors who can guide you in your career. Experienced professionals can offer valuable advice and help you navigate challenges and opportunities.

Soft Skills: Don't underestimate the importance of soft skills like time management, communication, and problem-solving. Developing these skills can make you a more effective and well-rounded professional.