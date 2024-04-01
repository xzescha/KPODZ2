CREATE TABLE menu_manager.menu(
    id UUID primary key,
    name varchar(50) not null unique,
    quantity integer,
    price numeric,
    min2cook integer,
    create_user varchar(36) not null,
    create_time_ms bigint not null,
    last_modify_user varchar(36) not null,
    last_modify_time_ms bigint not null
);
COMMENT ON TABLE menu_manager.menu IS 'Таблица для хранения блюд в меню';
COMMENT ON COLUMN menu_manager.menu.id IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN menu_manager.menu.name IS 'Наименование блюда';
COMMENT ON COLUMN menu_manager.menu.quantity IS 'Количество блюд, которые доступны к заказу';
COMMENT ON COLUMN menu_manager.menu.price IS 'Стоимость блюда';
COMMENT ON COLUMN menu_manager.menu.min2cook IS 'Время на приготовление блюда в минутах';
COMMENT ON COLUMN menu_manager.menu.create_user IS 'Идентификатор пользователя, создавшего запись';
COMMENT ON COLUMN menu_manager.menu.create_time_ms IS 'Unix timestamp создания записи в ms';
COMMENT ON COLUMN menu_manager.menu.last_modify_user IS 'Идентификатор пользователя, последним изменившего запись';
COMMENT ON COLUMN menu_manager.menu.last_modify_time_ms IS 'Unix timestamp последнего изменения записи в ms';
