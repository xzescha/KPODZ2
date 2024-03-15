package ru.xzescha.restaurant.menumanager.menu.repository.model;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "menu", schema = "menu_manager")
public class MenuEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "min2cook")
    private Integer min2cook;

    @Column(name = "create_user", nullable = false, length = 36)
    private String createUser;

    @Column(name = "create_time_ms", nullable = false)
    private Long createTimeMs;

    @Column(name = "last_modify_user", nullable = false, length = 36)
    private String lastModifyUser;

    @Column(name = "last_modify_time_ms", nullable = false)
    private Long lastModifyTimeMs;

    // Constructors, getters, and setters
    // Пустой конструктор для JPA
    public MenuEntity() {
        // Пустой конструктор без параметров
    }
    // Конструктор для создания нового пользователя
    public MenuEntity(String name, Integer quantity, BigDecimal price,
                      Integer min2cook, String createUser, String lastModifyUser) {
        this.id = UuidCreator.getTimeOrderedEpoch();   // Генерация UUID 7 версии
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.min2cook = min2cook;
        this.createUser = createUser;
        this.createTimeMs = System.currentTimeMillis();
        this.lastModifyUser = lastModifyUser;
        this.lastModifyTimeMs = this.createTimeMs;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMin2cook() {
        return min2cook;
    }

    public void setMin2cook(Integer min2cook) {
        this.min2cook = min2cook;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Long getCreateTimeMs() {
        return createTimeMs;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public Long getLastModifyTimeMs() {
        return lastModifyTimeMs;
    }

    public void setLastModifyTimeMs() {
        this.lastModifyTimeMs = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity that = (MenuEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity) && Objects.equals(price, that.price) && Objects.equals(min2cook, that.min2cook) && Objects.equals(createUser, that.createUser) && Objects.equals(createTimeMs, that.createTimeMs) && Objects.equals(lastModifyUser, that.lastModifyUser) && Objects.equals(lastModifyTimeMs, that.lastModifyTimeMs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, price, min2cook, createUser, createTimeMs, lastModifyUser, lastModifyTimeMs);
    }

    @Override
    public String toString() {
        return "MenuEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", min2cook=" + min2cook +
                ", createUser='" + createUser + '\'' +
                ", createTimeMs=" + createTimeMs +
                ", lastModifyUser='" + lastModifyUser + '\'' +
                ", lastModifyTimeMs=" + lastModifyTimeMs +
                '}';
    }

}
