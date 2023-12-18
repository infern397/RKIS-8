package ru.sfu.mvc.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sfu.mvc.models.Furniture;

import java.util.List;

/**
 * Класс для взаимодействия с REST API мебели.
 */
@Service
public class FurnitureRestClient {

    private static final String BASE_URL = "http://127.0.0.1:8080/api/";

    private final WebClient webClient;

    /**
     * Конструктор класса FurnitureRestClient. Инициализирует WebClient.
     */
    public FurnitureRestClient() {
        this.webClient = WebClient.create();
    }

    /**
     * Выполняет все операции с мебелью.
     */
    public void performAllOperations() {
        createNewFurniture("{\"name\":\"Test sofa\",\"description\":\"Small sofa\",\"color\":\"red\",\"price\":322,\"weight\":165}");
        createNewFurniture("{\"name\":\"Test sofa\",\"description\":\"Big sofa\",\"color\":\"red\",\"price\":690,\"weight\":165}");
        getAllFurniture();
        getAllFurnitureByPrice(500); // Укажите фактическое значение цены
        getFurnitureById(2); // Укажите фактический ID
        updateFurniture(2, "{\"name\":\"Test sofa updated\",\"description\":\"Biiiig sofa\",\"color\":\"red\",\"price\":322,\"weight\":165}");
        getAllFurniture();
        deleteFurniture(1); // Укажите фактический ID для удаления
        getAllFurniture();
    }

    /**
     * Получает список всех мебельных объектов.
     */
    private void getAllFurniture() {
        List<Furniture> allFurniture = webClient.get()
                .uri(BASE_URL)
                .retrieve()
                .bodyToFlux(Furniture.class)
                .collectList()
                .block();

        System.out.println("All Furniture: " + allFurniture);
    }

    /**
     * Получает список мебельных объектов по указанной цене.
     *
     * @param price цена для фильтрации.
     */
    private void getAllFurnitureByPrice(float price) {
        String url = BASE_URL + "?price=" + price;

        List<Furniture> furnitureByPrice = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Furniture.class)
                .collectList()
                .block();

        System.out.println("Furniture by Price: " + furnitureByPrice);
    }

    /**
     * Получает мебельный объект по указанному ID.
     *
     * @param furnitureId ID мебельного объекта.
     */
    private void getFurnitureById(int furnitureId) {
        Furniture furnitureById = webClient.get()
                .uri(BASE_URL + "/" + furnitureId)
                .retrieve()
                .bodyToMono(Furniture.class)
                .block();

        System.out.println("Furniture by ID: " + furnitureById);
    }

    /**
     * Создает новый мебельный объект с использованием JSON-запроса.
     *
     * @param jsonRequestBody JSON-представление мебельного объекта.
     */
    private void createNewFurniture(String jsonRequestBody) {
        webClient.post()
                .uri(BASE_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(jsonRequestBody))
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println("Create Request sent");
    }

    /**
     * Обновляет мебельный объект с указанным ID с использованием JSON-запроса.
     *
     * @param updateFurnitureId ID мебельного объекта для обновления.
     * @param jsonRequestBody    JSON-представление обновленных данных мебельного объекта.
     */
    private void updateFurniture(int updateFurnitureId, String jsonRequestBody) {
        webClient.patch()
                .uri(BASE_URL + "/" + updateFurnitureId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(jsonRequestBody))
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println("Update Request sent");
    }

    /**
     * Удаляет мебельный объект с указанным ID.
     *
     * @param deleteFurnitureId ID мебельного объекта для удаления.
     */
    private void deleteFurniture(int deleteFurnitureId) {
        webClient.delete()
                .uri(BASE_URL + "/" + deleteFurnitureId)
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println("Delete Request sent");
    }
}
