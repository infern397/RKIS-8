package ru.sfu.mvc.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sfu.mvc.models.Furniture;
import ru.sfu.mvc.repositories.FurnitureRepository;

import java.util.List;

/**
 * Сервис для работы с мебелью.
 */
@Service
@Transactional
public class FurnitureService {

    private final FurnitureRepository furnitureRepository;

    @Autowired
    public FurnitureService(
            FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    /**
     * Получает все мебельные изделия.
     *
     * @return список мебели.
     */
    public List<Furniture> findAll() {
        return furnitureRepository.findAll();
    }

    /**
     * Находит мебельное изделие по идентификатору.
     *
     * @param id идентификатор мебели.
     * @return найденное изделие или null, если не найдено.
     */
    public Furniture findOne(int id) {
        return furnitureRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет новое мебельное изделие.
     *
     * @param furniture объект мебели.
     */
    @Transactional
    public void save(Furniture furniture) {
        furnitureRepository.save(furniture);
    }

    /**
     * Обновляет информацию о мебельном изделии.
     *
     * @param id идентификатор мебели.
     * @param furniture объект мебели.
     */
    @Transactional
    public void update(int id, Furniture furniture) {
        furniture.setId(id);
        furnitureRepository.save(furniture);
    }

    /**
     * Удаляет мебельное изделие по идентификатору.
     *
     * @param id идентификатор мебели.
     */
    @Transactional
    public void delete(int id) {
        furnitureRepository.deleteById(id);
    }

    /**
     * Фильтрует мебель по цене, равной или большей заданной.
     *
     * @param maxPrice максимальная цена.
     * @return список отфильтрованной мебели.
     */
    public List<Furniture> filterByPrice(float maxPrice) {
        return furnitureRepository.findByPriceGreaterThanEqual(maxPrice);
    }
}
