package ru.xzescha.restaurant.menumanager.menu.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xzescha.restaurant.menumanager.api.model.Menu;
import ru.xzescha.restaurant.menumanager.api.model.MenuIdInfo;
import ru.xzescha.restaurant.menumanager.menu.repository.MenuRepository;
import ru.xzescha.restaurant.menumanager.menu.repository.model.MenuEntity;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class MenuCreatorService {
    private static final Logger logger = LoggerFactory.getLogger(MenuCreatorService.class);
    @Autowired
    private MenuRepository menuRepository;
    public MenuIdInfo menuCreate(Menu menu) {
        logger.info("Service for creating a dish with specified parameters");
        //todo Подставить имя пользователя
        MenuEntity menuEntity = new MenuEntity(
                menu.getName(),
                menu.getQuantity(),
                menu.getPrice(),
                menu.getMin2cook(),
                "testUser",
                "testUser"
        );
        menuRepository.save(menuEntity);
        logger.info("Success in creating a dish (Id of dish: {})", menuEntity.getId().toString());
        return new MenuIdInfo(menuEntity.getId().toString());
    }

}
