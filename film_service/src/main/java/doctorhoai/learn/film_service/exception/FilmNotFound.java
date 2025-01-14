package doctorhoai.learn.film_service.exception;

public class FilmNotFound extends RuntimeException {
  public FilmNotFound(String message) {
    super(message);
  }
}
