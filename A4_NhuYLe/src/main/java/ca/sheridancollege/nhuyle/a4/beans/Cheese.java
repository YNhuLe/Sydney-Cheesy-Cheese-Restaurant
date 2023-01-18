package ca.sheridancollege.nhuyle.a4.beans;

/* Programmer: NhuY Le
 * Assignment 1: Sydney's Cheesey Cheese 2.2
 * Program: Computer System Technology - Software Development 
 * and Network Engineering
 * 
 * Cheese class that contain object cheese and variables for cheese such as
 * name, quantity, weight, price. Clients can fill in the form to add cheese to
 * the inventory ( the weight of cheese, price, quantity, units), the program
 * will calculate the amount.After submitting the form the program will show the 
 * total value and all the data that clients just put in the form in the table. 
 * */
import java.io.Serializable;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
 
/**
 * An item of cheese in inventory. Cheeses are measured in units, usually in
 * wheels or fractions of a wheel, but also in squares or tommes.
 * 
 * @author Wendi Jollymore
 *
 */
@NoArgsConstructor

@Data
public class Cheese implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    /**
     * Unique ID for this cheese item.
     * 
     * @return this cheese's unique ID or primary key value
     */
    private int id = 1;
 
    /**
     * The name of this cheese.
     * 
     * @return this cheese's name
     */
    @NonNull
    private String name = "tbd";
 
    /**
     * The amount of this cheese in inventory.
     * 
     * @return the quantity of this cheese in inventory
     */
    private int quantity = 1;
 
    /**
     * The weight of this unit of cheese.
     * 
     * @return the weight of this unit of cheese
     */
    private double weight = 1;
 
    /**
     * The type of unit of this cheese item.
     * 
     * @return this cheese's unit of measurement
     */
    private int unitsId = 1;
 
    /**pcheese.
     * 
     * @return this cheese's price
     */
    private double price = 25.0;
 
    /**
     * The URL where this cheese's specifications sheet can be found.
     * 
     * @return this cheese's specifications URL
     * @param specSheet the new spec sheet URL
     */
    private String specSheet = "";
 
    /**
     * Construct a new cheese item with initial values.
     * 
     * @param id     the cheese's unique ID
     * @param name   the cheese's name
     * @param qty    the quantity of this cheese
     * @param weight the weight of this cheese's unit
     * @param units  the unit of measurement for this cheese
     * @param price  the price of this cheese unit
     * @param spec   the spec sheet URL
     */
    public Cheese(int id, String name, int qty, double weight, int units,
            double price, String spec) {
    setId(id);
    setName(name);
    setQuantity(qty);
    setWeight(weight);
    setUnitsId(units);
    setPrice(price);
    setSpecSheet(spec);
    }
 
    /**
     * Constructs a new cheese with specific values but no ID. This constructor
     * can be used when an primary key value is not yet known or unavailable.
     * 
     * @param name   the cheese's name
     * @param qty    the quantity of this cheese
     * @param weight the weight of this cheese's unit
     * @param units  the unit of measurement for this cheese
     * @param price  the price of this cheese unit
     * @param spec   the spec sheet URL
     */
    public Cheese(String name, int qty, double weight, int units, double price,
            String spec) {
    setName(name);
    setQuantity(qty);
    setWeight(weight);
    setUnitsId(units);
    setPrice(price);
    setSpecSheet(spec);
    }
 
    /**
     * Places a non-empty non-null value into this cheese's name.
     * 
     * @param name the new name for this cheese
     */
    public void setName(String name) {
    if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("Cheese name can't be empty.");
    } else {
        this.name = name;
    }
    }
 
    /**
     * Places a valid quantity into this cheese's quantity field.
     * 
     * @param qty the new quantity value
     */
    public void setQuantity(int qty) {
    if (qty > 0)
        this.quantity = qty;
    else
        throw new IllegalArgumentException(
                "Quantity must be 0 or greater.");
    }
 
    /**
     * Places a valid weight into this cheese's weight field.
     * 
     * @param weight the new weight
     */
    public void setWeight(double weight) {
    if (weight > 0) {
        this.weight = weight;
    } else {
        throw new IllegalArgumentException(
                "Weight must be greater " + "than 0.");
    }
    }
 
    /**
     * Places a valid price into this cheese's price field.
     * 
     * @param price the new price
     */
    public void setPrice(double price) {
    if (price >= 0) {
        this.price = price;
    } else {
        throw new IllegalArgumentException("Price must be 0 or greater.");
    }
    }
 
}
