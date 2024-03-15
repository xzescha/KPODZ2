package ru.xzescha.restaurant.menumanager.menu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.xzescha.restaurant.menumanager.api.model.Menu;
import ru.xzescha.restaurant.menumanager.api.model.MenuFullInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuFindService {
    private static final Logger logger = LoggerFactory.getLogger(MenuFindService.class);

    public MenuFullInfo findByName(String name){
        logger.info("Dish search service by name ({})",name);
        MenuFullInfo menuFullInfo = new MenuFullInfo("018c21f9-2033-79cf-97bc-7d7ae6e8a8d7",
                "fgggffg",15, BigDecimal.valueOf(154.5),456);
        logger.info("Success in search of service by name ({})",name);
        return menuFullInfo;
    }
    public List<MenuFullInfo> findAll() {
        logger.info("Search service for all dishes available for ordering");

        MenuFullInfo menuFullInfo = new MenuFullInfo("018c21f9-2033-79cf-97bc-7d7ae6e8a8d7",
                "asdasd",15, BigDecimal.valueOf(500), 45);
        List<MenuFullInfo> list = new ArrayList<>();
        list.add(0,menuFullInfo);
        logger.info("Success in search of all availible dishes (Number of availible dishes: {})",list.size());
        return list;
    }

    public Menu findByID(String id) {
        logger.info("Service for searching for a dish by its ID ({})",id);
        logger.info("Success in search of a dish by its ID ({})",id);
        return new Menu("name_example",16, BigDecimal.valueOf(5000), 90);
        //Для дальнейшей реализации надо бд. Плюс, прописать исключения на различные кейсы
    }


}
