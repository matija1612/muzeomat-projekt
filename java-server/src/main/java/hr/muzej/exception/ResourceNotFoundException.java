package hr.muzej.exception;
/**
* Exception kada se resource ne pronadje u bazi
*/
public class ResourceNotFoundException extends RuntimeException {
public ResourceNotFoundException(String poruka) {
super(poruka);
}
}