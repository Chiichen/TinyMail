package edu.scut.tinymail.domain.entry;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




/**
 * (Userauth)表实体类
 *
 * @author makejava
 * @since 2023-05-27 00:45:02
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userauth  {
    
    private Integer id;
    
    private String username;
    
    private String nickname;
    
    private String password;



}

