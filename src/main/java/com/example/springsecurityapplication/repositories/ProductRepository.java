package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    //ищем титл с игнором регистра по части наименования (наименовали так чтобы было примерно понятно что делаем)
    //ВАЖНО! все наименования должны соответствововать прописанным тегам! например findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual - только так! иначе работать не будет!
    List<Product> findByTitleContainingIgnoreCase(String name);
    //select_from - получить все данные product где where : lower - автоперевод записей в поле через нижний регистр и через LIKE сравниваем значения ; %?1% - проценты по бокам обзначают что по краям могут быть еще символы (введенный кусочек наименования может быть найден в середине целого слова). И далее перебрали вероятность того что слово может начинаться с искомого кусочка, а может им заканчиваться. price >= &2 - условие от введенной 2 второй переменной цены, до 3 переменной price<=&3
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float Do);

    //c сортировкой по возрастанию цены  order by price
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title, float ot, float Do);

    //c сортировкой по убыванию цены  order by price desc
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price desc ", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDest(String title, float ot, float Do);

    //+ по категории по возрастанию цены
    @Query(value = "select * from product where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);

    //+ по категории по убыванию цены
    @Query(value = "select * from product where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price desc ", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);
}
