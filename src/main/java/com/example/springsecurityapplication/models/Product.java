package com.example.springsecurityapplication.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //делаем так чтобы можно было поместить большой текст при помощи настройки columnDefinition = "text"
    //nullable- на стороне бд не может быть пустым а @NotEmpty и @NotNull - на стороне сервера. @NotNull - только к числам
    @Column(name = "title",nullable = false,columnDefinition = "text", unique = true)
    @NotEmpty(message = "Наименование товара не может быть пустым")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Описание товара не может быть пустым")
    private String description;
    @Column(name = "price", nullable = false)
    //здесь @NotNull будет конфликтовать с Min
    @Min(value = 1, message = "Цена не может быть отрицательной и нулевой")
    private float price;
    @Column(name = "warehouse", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Склад товара не может быть пустым")
    private String warehouse;
    @Column(name = "seller", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Продавец товара не может отсуствовать")
    private String seller;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    //при создании когда прописываем List необходимо из списка выбрать {E} util List !
    private List<Image> imageList=new ArrayList<>();
    private LocalDateTime dateTime;
    @ManyToOne(optional = false)
    private Category category;

    //Будем заполнять дату и время при создании объекта класса
    @PrePersist
    private void init(){
        dateTime=LocalDateTime.now();
    }

    @ManyToMany()
    //передача аргументов - тут сначала для joinColumns необходимо ставить product_id а в inverseJoinColumns - person_id! А в модели Person - наоборот!!
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;

    @OneToMany(mappedBy = "product")
    private List<Order> orderList;

    //метод по добавлению фото в лист к текущему продукту
    public void addImageToProduct (Image image){
        image.setProduct(this);
        imageList.add(image);
    }

    public void updateImageToProduct (Image image){
        image.getProduct();
        imageList.add(image);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
