package com.levimartines.cursomc.handlers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ValidationError extends StandardError {

    @JsonProperty("errors")
    List<FieldMessage> list = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public void addError(String fieldName, String message){
        list.add(new FieldMessage(fieldName,message));
    }

}
