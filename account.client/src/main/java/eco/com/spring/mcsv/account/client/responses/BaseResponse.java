package eco.com.spring.mcsv.account.client.responses;

/**
 * @author edisoncsi on 14/3/24
 * @project TestBackendJava
 */

public class BaseResponse {
    private String message;

    public BaseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
