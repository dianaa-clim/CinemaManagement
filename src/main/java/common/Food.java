package common;

import java.math.BigDecimal;

public class Food {

    private int idFood;
    private String name;
    private String description;
    private BigDecimal price;

    public Food() {}

    public Food(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Food(int idFood, String name, String description, BigDecimal price) {
        this.idFood = idFood;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
