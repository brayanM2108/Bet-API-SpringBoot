package com.melo.bets.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto  {

   @NotBlank(message = "the name cannot be blank")
   private String name;

   @NotBlank(message = "the email cannot be blank")
   @Email(message = "the email is not valid")
   private String email;

   @NotBlank(message = "the password cannot be blank")
   private String password;

   @NotBlank(message = "the document cannot be blank")
   private String document;
}
