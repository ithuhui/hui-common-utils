package pers.hui.common.model;

/**
 * <code>BusinessException</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 15:22.
 *
 * @author Gary.Hu
 */
public class BusinessException extends RuntimeException{

    private int code;
    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
