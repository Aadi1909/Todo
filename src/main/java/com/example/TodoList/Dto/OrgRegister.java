package com.example.TodoList.Dto;


import com.example.TodoList.Model.Organisation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OrgRegister {

    private String orgName;

    private String password;

    private String orgDesc;

    private String orgEmail;

    private String orgPhone;

    public Organisation to() {
        return Organisation.builder()
                .name(this.orgName)
                .password(this.password)
                .description(this.orgDesc)
                .orgEmail(this.orgEmail)
                .orgPhone(this.orgPhone)
                .build();
    }
}
