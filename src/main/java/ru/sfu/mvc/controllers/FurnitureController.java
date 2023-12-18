package ru.sfu.mvc.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.sfu.mvc.messages.MessageProducer;
import ru.sfu.mvc.models.Furniture;
import ru.sfu.mvc.services.FurnitureService;

import java.util.stream.Collectors;

/**
 * Контроллер для работы с мебелью.
 */
@Controller
@RequestMapping("/furniture")
public class FurnitureController {

    private final FurnitureService furnitureService;
    private final MessageProducer messageProducer;

    @Autowired
    public FurnitureController(FurnitureService furnitureService, MessageProducer messageProducer) {
        this.furnitureService = furnitureService;
        this.messageProducer = messageProducer;
    }

    /**
     * Обрабатывает запрос на получение списка мебели.
     *
     * @param price Цена для фильтрации.
     * @param model Объект модели.
     * @return Представление списка мебели.
     */
    @GetMapping()
    public String index(@RequestParam(name = "price", required = false) Float price, Model model) {
        if (price != null) {
            model.addAttribute("furniture", furnitureService.filterByPrice(price));
        } else {
            model.addAttribute("furniture", furnitureService.findAll());
        }
        return "furniture/main";
    }

    /**
     * Обрабатывает запрос на редактирование информации о мебели.
     *
     * @param id    Идентификатор мебели.
     * @param model Объект модели.
     * @return Представление для редактирования.
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("furniture", furnitureService.findOne(id));
        return "furniture/edit";
    }

    /**
     * Обрабатывает запрос на добавление новой мебели.
     *
     * @param furniture Объект мебели для добавления.
     * @return Представление для добавления мебели.
     */
    @GetMapping("/add")
    public String addFurniture(@ModelAttribute("furniture") Furniture furniture) {
        return "furniture/add";
    }

    /**
     * Обрабатывает запрос на создание новой мебели.
     *
     * @param furniture     Объект мебели для создания.
     * @param bindingResult Результаты валидации.
     * @return Представление для добавления мебели или перенаправление на список мебели.
     */
    @PostMapping()
    public String create(
            @ModelAttribute("furniture") @Valid Furniture furniture,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            messageProducer.sendMessage("При добавлении объекта  произошла(и) ошибка(и): " + errors);
            return "furniture/add";
        }
        messageProducer.sendMessage("Был добавлен " + furniture);
        furnitureService.save(furniture);
        return "redirect:/furniture";
    }

    /**
     * Обрабатывает запрос на обновление информации о мебели.
     *
     * @param furniture     Объект мебели для обновления.
     * @param bindingResult Результаты валидации.
     * @param id            Идентификатор мебели.
     * @return Представление для редактирования или перенаправление на список мебели.
     */
    @PatchMapping("/{id}")
    public String update(
            @ModelAttribute("furniture") @Valid Furniture furniture,
            BindingResult bindingResult,
            @PathVariable("id") int id
    ) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            messageProducer.sendMessage("При изменении объекта с id=" + id + " произошла(и) ошибка(и): " + errors);
            return "furniture/edit";
        }
        Furniture updatingFurniture = furnitureService.findOne(id);
        furnitureService.update(id, furniture);
        messageProducer.sendMessage(updatingFurniture + " был изменен на " + furniture);
        return "redirect:/furniture";
    }

    /**
     * Обрабатывает запрос на удаление мебели.
     *
     * @param id Идентификатор мебели.
     * @return Перенаправление на список мебели.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        Furniture deletedFurniture = furnitureService.findOne(id);
        furnitureService.delete(id);
        messageProducer.sendMessage("Был удален: " + deletedFurniture);
        return "redirect:/furniture";
    }

    /**
     * Обрабатывает запрос на покупку мебели.
     *
     * @param id Идентификатор мебели.
     * @return Перенаправление на список мебели.
     */
    @PatchMapping("/{id}/buy")
    public String buy(@PathVariable("id") int id) {
        messageProducer.sendMessage("Был куплен: " + furnitureService.findOne(id));
        return "redirect:/furniture";
    }
}
