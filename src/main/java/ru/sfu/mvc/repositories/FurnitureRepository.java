package ru.sfu.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.mvc.models.Furniture;

import java.util.List;

/**
 * Репозиторий для работы с мебелью.
 */
@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {

    /**
     * Поиск мебели по цене, равной или большей заданной.
     *
     * @param price  Цена мебели, относительно которой производится поиск.
     * @return  Список мебели, у которой цена равна или больше заданной.
     */
    List<Furniture> findByPriceGreaterThanEqual(float price);
}
