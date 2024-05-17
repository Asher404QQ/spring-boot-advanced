package ru.kors.webmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest(ProxyAuthorController.class)
class ProxyAuthorControllerTest {
    @Autowired
    private MockRestServiceServer mockServer;
    @Autowired
    private ProxyAuthorController controller;

    public static final String AUTHORS = """
            [
                {
                    "id": 1,
                    "name": "Tamara27",
                    "email": "Jules_Fisher@hotmail.com"
                },
                {
                    "id": 2,
                    "name": "Cristal4",
                    "email": "Ford.Prosacco@hotmail.com"
                },
                {
                    "id": 3,
                    "name": "Ezequiel.Morar",
                    "email": "Maribel_Barton22@yahoo.com"
                }
            ]
            """;
    public static final String POSTS_JSON = """
            [
              {
                "userId": 1,
                "id": 1,
                "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
              },
              {
                "userId": 1,
                "id": 2,
                "title": "qui est esse",
                "body": "est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla"
              },
              {
                "userId": 1,
                "id": 3,
                "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
                "body": "et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut"
              }
            ]
            """;

    @Test
    void proxyAuthor() {
        mockServer.expect(requestTo("https://localhost:8080/api/v1/authors/clients/1"))
                .andRespond(withSuccess(AUTHORS, MediaType.APPLICATION_JSON));

        mockServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts"))
                .andRespond(withSuccess(POSTS_JSON, MediaType.APPLICATION_JSON));

        var response = controller.findByIdv2(1L);
        var proxyAuthor = response.getBody();
        assertNotNull(proxyAuthor);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(proxyAuthor.getEmail(), "Jules_Fisher@hotmail.com");

        mockServer.verify();
    }
}