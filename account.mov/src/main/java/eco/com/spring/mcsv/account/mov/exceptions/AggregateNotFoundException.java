package eco.com.spring.mcsv.account.mov.exceptions;

/**
 * @author edisoncsi on 14/9/23
 * @project banking-account
 */
public class AggregateNotFoundException extends RuntimeException{
    public  AggregateNotFoundException(String message){
        super(message);
    }
}
