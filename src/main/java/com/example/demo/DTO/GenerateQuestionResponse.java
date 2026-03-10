package com.example.demo.DTO;

import java.util.List;

public class GenerateQuestionResponse {
    private int savedCount;
    public GenerateQuestionResponse(int savedCount){
        this.savedCount=savedCount;
    }
    public int getSavedCount(){
        return savedCount;
    }
    public void setSavedCount(int savedCount){
        this.savedCount=savedCount;
    }


}
