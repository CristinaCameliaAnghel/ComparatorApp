package etti.comparator.dto;

import jakarta.validation.constraints.NotEmpty;

public class ServiceDto {
    @NotEmpty(message = "Mandatory field")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
