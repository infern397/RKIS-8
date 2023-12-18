package ru.sfu.mvc.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * Класс, представляющий мебель.
 */
@Entity
@Table(name = "furniture")
public class Furniture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 1, max = 100, message = "Длина названия должна быть от 1 до 100")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    @Size(min = 1, max = 50, message = "Длина цвета должна быть от 1 до 50")
    private String color;

    @Column(name = "price")
    @Min(value = 0, message = "Цена должна быть неотрецательной")
    private float price;

    @Column(name = "weight")
    @Min(value = 0, message = "Вес мебели должен быть неотрицательным")
    private int weight;

    /**
     * Конструктор с параметрами.
     *
     * @param id          Идентификатор мебели.
     * @param name        Название мебели.
     * @param description Описание мебели.
     * @param color       Цвет мебели.
     * @param price       Цена мебели.
     * @param weight      Вес мебели.
     */
    public Furniture(int id, String name, String description, String color, float price, int weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.price = price;
        this.weight = weight;
    }

    /**
     * Пустой конструктор.
     */
    public Furniture() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Переопределение метода toString().
     */
    @Override
    public String toString() {
        return "Furniture{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", color='" + color + '\'' + ", price=" + price + ", weight=" + weight + '}';
    }

    /**
     * Переопределение метода equals().
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Furniture furniture)) return false;
        return id == furniture.id && Float.compare(furniture.price, price) == 0 && weight == furniture.weight && Objects.equals(name, furniture.name) && Objects.equals(description, furniture.description) && Objects.equals(color, furniture.color);
    }

    /**
     * Переопределение метода hashCode().
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, color, price, weight);
    }
}