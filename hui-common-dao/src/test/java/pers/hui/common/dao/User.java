package pers.hui.common.dao;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <code>User</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/1/3 14:30.
 *
 * @author Gary.Hu
 */
@Data
@ToString
public class User implements Serializable {
    private String user_id;
    private String userName;
}
