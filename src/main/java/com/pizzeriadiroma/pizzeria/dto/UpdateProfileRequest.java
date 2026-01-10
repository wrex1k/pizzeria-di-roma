    package com.pizzeriadiroma.pizzeria.dto;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;

    public class UpdateProfileRequest {

        @NotBlank(message = "First name is required.")
        @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters.")
        private String firstName;

        @NotBlank(message = "Last name is required.")
        @Size(min = 2, max = 40, message = "Last name must be between 2 and 40 characters.")
        private String   lastName;

        @NotBlank(message = "Email is required.")
        @Email(message = "Email should be valid.")
        @Size(max = 255, message = "Email must not exceed 255 characters.")
        private String email;

        @NotBlank(message = "Phone number is required.")
        @Pattern(regexp = "^\\+[0-9]{1,3}[\\s0-9]{7,15}$", message = "Enter a valid phone number (e.g., +421944025567 or +421 944 025 567)")
        @Size(min = 9, max = 20, message = "Phone number must be 9-20 characters")
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