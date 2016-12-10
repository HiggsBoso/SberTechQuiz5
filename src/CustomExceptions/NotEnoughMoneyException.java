package CustomExceptions;

/**
 * Created by Someone on 04.12.2016.
 */
public class NotEnoughMoneyException extends Exception {
    @Override
    public String toString() {
        return "Ошибка возникла из-за того, что с карты пытались снять большую сумму, чем была на карте";
    }
}
