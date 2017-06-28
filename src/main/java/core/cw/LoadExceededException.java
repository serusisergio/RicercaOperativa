package core.cw;

/**
 * Created by stefano on 13/06/17.
 */
public class LoadExceededException extends RuntimeException {
    public LoadExceededException(){
        super("Cannot merge routes, load exceeded");
    }
}
