package ru.xzescha.restaurant.menumanager.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xzescha.restaurant.menumanager.menu.repository.model.MenuEntity;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью MenuEntity в базе данных.
 * Используется для выполнения операций чтения/записи данных о блюдах.
 */
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {

}
