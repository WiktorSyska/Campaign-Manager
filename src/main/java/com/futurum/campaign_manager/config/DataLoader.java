package com.futurum.campaign_manager.config;


import com.futurum.campaign_manager.model.Account;
import com.futurum.campaign_manager.model.Keyword;
import com.futurum.campaign_manager.model.Town;
import com.futurum.campaign_manager.repository.AccountRepository;
import com.futurum.campaign_manager.repository.KeywordRepository;
import com.futurum.campaign_manager.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
/**
 * Initial data loader component that populates the database with sample data.
 * <p>
 * Implements {@link CommandLineRunner} to execute data loading when the application starts.
 * Only loads data if the respective tables are empty, preventing duplicate entries.
 * </p>
 *
 * <p>
 * Loads three types of data:
 * <ul>
 *   <li>Sample cities with postal codes</li>
 *   <li>Common marketing keywords</li>
 *   <li>Initial account with seed balance</li>
 * </ul>
 * </p>
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final TownRepository townRepository;
    private final KeywordRepository keywordRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public DataLoader(TownRepository townRepository,
                      KeywordRepository keywordRepository,
                      AccountRepository accountRepository) {
        this.townRepository = townRepository;
        this.keywordRepository = keywordRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadTowns();
        loadKeywords();
        loadAccount();
    }

    private void loadTowns() {
        if (townRepository.count() == 0) {
            List<Town> towns = Arrays.asList(
                    new Town("London", "SW1A 1AA"),
                    new Town("New York", "10001"),
                    new Town("Paris", "75000"),
                    new Town("Berlin", "10115"),
                    new Town("Tokyo", "100-0001"),
                    new Town("Sydney", "2000"),
                    new Town("Rome", "00100"),
                    new Town("Madrid", "28001"),
                    new Town("Toronto", "M5V 3L9"),
                    new Town("Dubai", "00000"),
                    new Town("Singapore", "018960"),
                    new Town("Los Angeles", "90001"),
                    new Town("Chicago", "60601"),
                    new Town("Hong Kong", "999077"),
                    new Town("Barcelona", "08001")
            );
            townRepository.saveAll(towns);
            System.out.println("Loaded " + towns.size() + " cities");
        }
    }

    private void loadKeywords() {
        if (keywordRepository.count() == 0) {
            List<Keyword> keywords = Arrays.asList(
                    new Keyword("electronics"),
                    new Keyword("smartphones"),
                    new Keyword("computers"),
                    new Keyword("gaming"),
                    new Keyword("fashion"),
                    new Keyword("clothing"),
                    new Keyword("shoes"),
                    new Keyword("accessories"),
                    new Keyword("home"),
                    new Keyword("garden"),
                    new Keyword("furniture"),
                    new Keyword("decor"),
                    new Keyword("sports"),
                    new Keyword("fitness"),
                    new Keyword("bicycles"),
                    new Keyword("books"),
                    new Keyword("education"),
                    new Keyword("courses"),
                    new Keyword("automotive"),
                    new Keyword("cars"),
                    new Keyword("parts"),
                    new Keyword("health"),
                    new Keyword("beauty"),
                    new Keyword("cosmetics"),
                    new Keyword("food"),
                    new Keyword("restaurants"),
                    new Keyword("travel"),
                    new Keyword("vacations"),
                    new Keyword("hotels"),
                    new Keyword("business"),
                    new Keyword("marketing"),
                    new Keyword("finance"),
                    new Keyword("real estate"),
                    new Keyword("rentals"),
                    new Keyword("sales")
            );
            keywordRepository.saveAll(keywords);
            System.out.println("Loaded " + keywords.size() + " keywords");
        }
    }

    private void loadAccount() {
        if (accountRepository.count() == 0) {
            Account emeraldAccount = new Account("Emerald Account", new BigDecimal("10000.00"));
            accountRepository.save(emeraldAccount);
            System.out.println("Created Emerald Account with balance: " + emeraldAccount.getBalance());
        }
    }
}
