package ru.xzescha.restaurant.menumanager.menu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xzescha.restaurant.menumanager.api.MenuApi;
import ru.xzescha.restaurant.menumanager.api.model.Menu;
import ru.xzescha.restaurant.menumanager.api.model.MenuFullInfo;
import ru.xzescha.restaurant.menumanager.api.model.MenuIdInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ru.xzescha.restaurant.menumanager.api.model.ErrorResponse;
import ru.xzescha.restaurant.menumanager.menu.service.MenuCreatorService;
import ru.xzescha.restaurant.menumanager.menu.service.MenuFindService;
import ru.xzescha.restaurant.menumanager.menu.service.MenuUpdateService;

@Controller
@RequestMapping("${openapi.base-path:/api/v1}")
public class MenuApiControllerImpl implements MenuApi {
    @Autowired
    MenuFindService menuFindService;
    @Autowired
    MenuCreatorService menuCreatorService;
    @Autowired
    MenuUpdateService menuUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(MenuApiControllerImpl.class);

    /*
    Обработчик ошибок для методов контроллера
    */
    private <T> ResponseEntity<T> handleException(Exception e, HttpStatus httpStatus, String details, Class<T> responseType) {
        if (e != null) logger.error("Exception catched: {}", e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), httpStatus.value(), httpStatus.getReasonPhrase());
        errorResponse.setDetails(details);
        return (ResponseEntity<T>) (ResponseEntity<?>) new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * POST /menu : Создать новое блюдо в меню
     * Регистрирует в системе новое блюдо
     *
     * @param menu Объект для создания блюда в меню (optional)
     * @return Создано. (status code 201)
     * or Что-то пошло не так (status code 200)
     */
    @Override
    public ResponseEntity<MenuIdInfo> createMenu(Menu menu) {
        logger.info("Accepted request to create menu item");
        try{
            MenuIdInfo menuIdInfo = menuCreatorService.menuCreate(menu);
            return new ResponseEntity<>(menuIdInfo, HttpStatus.CREATED);
        } catch (Exception e){
            return handleException(
                    e,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    MenuIdInfo.class
            );
        }
    }

    /**
     * DELETE /menu/{id} : Удаление блюда
     * Блюдо остается доступным, но его количество обнуляется
     *
     * @param id ID блюда, по которому необходимо вернуть информацию (required)
     * @return Успешная операция (status code 204)
     * or Изменения не требуются (status code 304)
     * or Блюдо не найдено (status code 404)
     * or Что-то пошло не так (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteMenuByID(String id) {
        logger.info("Accepted request to delete menu item by its ID ({})", id);
        try{
            Menu menu = new Menu();
            menu = menuFindService.findByID(id);
            menu.quantity(0);
            menuUpdateService.updateByID(id, menu);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return (ResponseEntity<Void>) (ResponseEntity<?>) new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            //return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка", List.class);
        }

    }

    /**
     * GET /menu/findAll : Поиск списка доступных к заказу блюд
     *
     * @return Успешный ответ (status code 200)
     * or Блюдо не найдено (status code 404)
     * or Что-то пошло не так (status code 200)
     */
    @Override
    public ResponseEntity<List<MenuFullInfo>> findMenuAll() {
        logger.info("Accepted request to return accessible menu");
        try {
            // Вызываем сервисный слой для поиска
            List<MenuFullInfo> resultList = menuFindService.findAll();
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return (ResponseEntity<List<MenuFullInfo>>) (ResponseEntity<?>) new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            //return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка", List.class);
        }

    }

    /**
     * GET /menu/findByName : Поиск блюда по наименованию
     * Поиск блюда идет по точному совпадению с учетом регистра
     *
     * @param name Наименование блюда (required)
     * @return Успешный ответ (status code 200)
     * or Блюдо не найдено (status code 404)
     * or Что-то пошло не так (status code 200)
     */
    @Override
    public ResponseEntity<MenuFullInfo> findMenuByName(String name) {
        logger.info("Request for return of dish by name ({}) accepted", name);
        try {
            // Вызываем сервисный слой для поиска
            MenuFullInfo resultMenu= menuFindService.findByName(name);
            return new ResponseEntity<>(resultMenu, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return (ResponseEntity<MenuFullInfo>) (ResponseEntity<?>) new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            //return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка", List.class);
        }
    }

    /**
     * GET /menu/{id} : Инфо о блюде по его id
     * Это может сделать только авторизованный пользователь
     *
     * @param id ID блюда, по которому необходимо вернуть информацию (required)
     * @return Успешный ответ (status code 200)
     * or Блюдо не найдено (status code 404)
     * or Что-то пошло не так (status code 200)
     */
    @Override
    public ResponseEntity<Menu> getMenuByID(String id) {
        logger.info("A request to return a dish by its ID ({}) has been accepted",id);
        try{
            Menu menu = menuFindService.findByID(id);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return (ResponseEntity<Menu>) (ResponseEntity<?>) new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            //return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка", List.class);
        }
    }

    /**
     * PATCH /menu/{id} : Обновить информацию о блюде
     * Это может сделать только авторизованный администратор
     *
     * @param id   ID блюда, по которому необходимо вернуть информацию (required)
     * @param menu Обновить существующего пользователя (optional)
     * @return Успешная операция (status code 204)
     * or Изменения не требуются (status code 304)
     * or Блюдо не найдено (status code 404)
     * or Что-то пошло не так (status code 200)
     */
    @Override
    public ResponseEntity<Void> updateMenuByID(String id, Menu menu) {
        logger.info("Accepted a request to update information about an existing dish by its ID ({})",id);
        try{
            menuUpdateService.updateByID(id, menu);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return handleException(
                    e,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }
    }
}
