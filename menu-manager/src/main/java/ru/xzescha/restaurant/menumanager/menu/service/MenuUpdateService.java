package ru.xzescha.restaurant.menumanager.menu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xzescha.restaurant.menumanager.api.model.Menu;
import ru.xzescha.restaurant.menumanager.menu.repository.MenuRepository;
import ru.xzescha.restaurant.menumanager.menu.repository.model.MenuEntity;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuUpdateService {

    @Autowired
    private MenuRepository menuRepository;
    private static final Logger logger = LoggerFactory.getLogger(MenuUpdateService.class);
    public void updateByID(String id, Menu menu) {
        logger.info("Service for updating information about an existing dish by its identifier ({})",id);

        MenuEntity menuEntity = menuRepository.findById(UUID.fromString(id)).orElse(null);
        if (menuEntity == null || !(menuEntity.getQuantity() == 0)){
            logger.warn("Нет активного блюда с названием = {}", menu.getName());
            return;
        }

        Boolean updateNeeded = false;
        if (menuEntity.equals(menu))
        logger.info("Success in updating information about an existing dish by its identifier ({})",id);
        return;
        //Для дальнейшей реализации надо бд. Плюс, прописать исключения на различные кейсы
    }
}
