package com.example.helloworld.service;

import com.example.helloworld.model.Player;
import com.example.helloworld.repository.PlayerRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository) throws FileNotFoundException {
        this.repository = repository;
        parseCSV();
    }


    // find all records
    @Cacheable(cacheNames = {"playerCache"}, key = "#playerId")
    public List<Player> getAllPlayers() {
        // simulating backend querying delay
        simulateBackendCall();
        return repository.findAll();
    }

    // find existing record
    @Cacheable(cacheNames = {"playerCache"}, key = "#playerId")
    public Player findPlayer(String playerId) {
        // simulating backend querying delay
        simulateBackendCall();
        return repository.findById(playerId).get();
    }

    // this method will pause main thread for 5 seconds
    public void simulateBackendCall() {
        try {
            System.out.println("------------- Going to sleep for 5 seconds to simulate Backend Delay -----------");
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void parseCSV() throws FileNotFoundException {
        String fileName = "/workspaces/spring-boot-hello-world/src/main/resources/Player.csv";
        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        CsvToBean csv = new CsvToBean();
        csv.setCsvReader(csvReader);
        csv.setMappingStrategy(setColumMapping());

        List<Player> list = csv.parse();
        this.repository.saveAll(list);
        /*
        for (Object object : list) {
        Player player = (Player) object;
        System.out.println(player);
        }
        */
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
  private static ColumnPositionMappingStrategy setColumMapping() {
    ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
    strategy.setType(Player.class);
    String[] columns = new String[]{ "playerID", "birthYear", "birthMonth", "birthDay", "birthCountry", "birthState", "birthCity", "deathYear",
                        "deathMonth", "deathDay", "deathCountry", "deathState", "deathCity", "nameFirst", "nameLast", "nameGiven",
                        "weight", "height", "bats", "thrower", "debut", "finalGame", "retroID", "bbrefID" };
    strategy.setColumnMapping(columns);
    return strategy;
  }
}