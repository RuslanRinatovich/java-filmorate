package ru.yandex.practicum.filmorate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FilmorateApplicationTests {
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void greetingShouldReturnDefaultMessage() throws Exception {
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/home",
//                String.class)).contains("Приветствуем вас, в приложении ФИльмРате");
//    }
//
//    @Test
//    public void addCorrectFilmReturnCode200() {
//
//        final Film newFilm = new Film();
//        newFilm.setName("Фильм 1");
//        newFilm.setDescription("Фильм 1");
//        newFilm.setDuration(34);
//        newFilm.setReleaseDate(LocalDate.of(2004, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/films", port), newFilm, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//
//    @Test
//    public void addFilmWithoutNameReturnCode400() {
//        final Film newFilm = new Film();
//        newFilm.setName("");
//        newFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»)");
//        newFilm.setDuration(34);
//        newFilm.setReleaseDate(LocalDate.of(2004, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/films", port), newFilm, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void addFilmDescriptionMoreThan200ReturnCode400() {
//        final Film newFilm = new Film();
//        newFilm.setName("Film name");
//        newFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. " +
//                "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, " +
//                "а именно 20 миллионов. о Куглов, " +
//                "который за время «своего отсутствия», стал кандидатом Коломбани.");
//        newFilm.setDuration(34);
//        newFilm.setReleaseDate(LocalDate.of(2004, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/films", port), newFilm, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void addFilmWithNullDescriptionReturnCode200() {
//        final Film newFilm = new Film();
//        newFilm.setName("Film name");
//        newFilm.setDuration(34);
//        newFilm.setReleaseDate(LocalDate.of(2004, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/films", port), newFilm, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//    public void addFilmWithWrongReleaseDateReturnCode400() {
//        final Film newFilm = new Film();
//        newFilm.setName("Film name");
//        newFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»)");
//        newFilm.setDuration(34);
//        newFilm.setReleaseDate(LocalDate.of(1890, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/films", port), newFilm, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void addFilmWithNegativeDurationReturnCode400() {
//        final Film newFilm = new Film();
//        newFilm.setName("Film name");
//        newFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»)");
//        newFilm.setDuration(-34);
//        newFilm.setReleaseDate(LocalDate.of(1990, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/films", port), newFilm, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void addCorrectUserReturnCode200() {
//        final User newUser = new User();
//        newUser.setName("Adam");
//        newUser.setLogin("AdamOne");
//        newUser.setEmail("Adam@hello.ru");
//        newUser.setBirthday(LocalDate.of(1990, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/users", port), newUser, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//    public void addIncorrectLoginReturnCode400() {
//        final User newUser = new User();
//        newUser.setName("AdamLamberd");
//        newUser.setLogin("Adam One");
//        newUser.setEmail("Adam@hello.ru");
//        newUser.setBirthday(LocalDate.of(1990, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/users", port), newUser, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void addIncorrectMailReturnCode400() {
//        final User newUser = new User();
//        newUser.setName("AdamLamberd");
//        newUser.setLogin("AdamOne");
//        newUser.setEmail("hello.ru");
//        newUser.setBirthday(LocalDate.of(1990, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/users", port), newUser, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void addIncorrectBirthdayReturnCode400() {
//        final User newUser = new User();
//        newUser.setName("AdamLamberd");
//        newUser.setLogin("AdamOne");
//        newUser.setEmail("hel@lo.ru");
//        newUser.setBirthday(LocalDate.of(2990, 4, 16));
//        final ResponseEntity<String> response = restTemplate.postForEntity(String.format("http://localhost:%d/users", port), newUser, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
}
