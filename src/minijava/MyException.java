package minijava;

/**
 * Created by Vc on 2017/3/22.
 */
public class MyException extends RuntimeException {
    public MyException(String msg){
        super(msg);
        printStackTrace();
    }
}
