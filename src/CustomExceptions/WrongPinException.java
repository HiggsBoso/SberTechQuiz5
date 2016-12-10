package CustomExceptions;

/**
 * Created by Someone on 04.12.2016.
 */
public class WrongPinException extends Exception {
    @Override
    public String toString() {
        return "Ошибка возникла из-за неправильного PIN-кода карты";
    }
}
