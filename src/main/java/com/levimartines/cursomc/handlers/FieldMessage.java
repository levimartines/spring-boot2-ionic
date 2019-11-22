package com.levimartines.cursomc.handlers;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {
    private String fieldName;
    private String msg;
}
