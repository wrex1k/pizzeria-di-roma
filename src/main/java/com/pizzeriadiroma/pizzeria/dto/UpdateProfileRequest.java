    package com.pizzeriadiroma.pizzeria.dto;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;

    public class UpdateProfileRequest {

        @NotBlank(message = "{validation.firstName.required}")
        @Size(min = 2, max = 20, message = "{validation.firstName.size}")
        private String firstName;

        @NotBlank(message = "{validation.lastName.required}")
        @Size(min = 2, max = 40, message = "{validation.lastName.size}")
        private String   lastName;

        @NotBlank(message = "{validation.email.required}")
        @Email(message = "{validation.email.invalid}")
        @Size(max = 255, message = "{validation.email.size}")
        private String email;

        @NotBlank(message = "{validation.phone.required}")
        @Pattern(regexp = "^\\+[0-9]{1,3}[\\s0-9]{7,15}$", message = "{validation.phone.invalid}")
        @Size(min = 9, max = 20, message = "{validation.phone.size}")
        private String phone;

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }