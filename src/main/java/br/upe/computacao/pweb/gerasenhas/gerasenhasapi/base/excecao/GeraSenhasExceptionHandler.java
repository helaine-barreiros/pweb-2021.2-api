package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.base.excecao;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeraSenhasExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NaoEncontradoException.class)
  public ResponseEntity<GeraSenhasErroDTO> handleNaoEncontradoException(Exception exception,
      WebRequest request) {

    GeraSenhasErroDTO detalhe = GeraSenhasErroDTO.builder()
        .titulo("Ocorreu um erro ao processar a solicitação").status(HttpStatus.NOT_FOUND.value())
        .horario(LocalDateTime.now()).erro(exception.getMessage()).build();

    return new ResponseEntity<GeraSenhasErroDTO>(detalhe, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(GeraSenhasException.class)
  public ResponseEntity<GeraSenhasErroDTO> handleGeraSenhasException(Exception exception,
      WebRequest request) {

    GeraSenhasErroDTO detalhe =
        GeraSenhasErroDTO.builder().titulo("Ocorreu um erro ao processar a solicitação")
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value()).horario(LocalDateTime.now())
            .erro(exception.getMessage()).build();

    return new ResponseEntity<GeraSenhasErroDTO>(detalhe, new HttpHeaders(),
        HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<GeraSenhasErroDTO> handleValidationException(Exception exception,
      WebRequest request) {
    return new ResponseEntity<GeraSenhasErroDTO>(this.extrairMensagemViolacao(exception),
        new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {

    return new ResponseEntity<>(this.extrairMensagemViolacao(exception), new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }

  private GeraSenhasErroDTO extrairMensagemViolacao(Exception exception) {
    log.warn("Uma exceção de validação foi capturada", exception);

    String erros = "";

    if (exception instanceof ConstraintViolationException) {
      erros = ((ConstraintViolationException) exception).getConstraintViolations().stream()
          .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
          .collect(Collectors.joining(", "));

    } else {
      erros = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors()
          .stream().map(cv -> cv == null ? "null" : cv.getDefaultMessage())
          .collect(Collectors.joining(", "));
    }

    return GeraSenhasErroDTO.builder().titulo("Os dados da requisição são inválidos")
        .status(HttpStatus.BAD_REQUEST.value()).horario(LocalDateTime.now()).erro(erros).build();
  }
}
