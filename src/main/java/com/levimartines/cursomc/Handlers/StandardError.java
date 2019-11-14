package com.levimartines.cursomc.Handlers;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
    private Integer status;
    private String msg;
    private Long timeStamp;
}
