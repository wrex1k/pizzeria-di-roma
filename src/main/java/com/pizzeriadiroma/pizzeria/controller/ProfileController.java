package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.dto.ChangePasswordRequest;
import com.pizzeriadiroma.pizzeria.dto.UpdateProfileRequest;
import com.pizzeriadiroma.pizzeria.entity.Order;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.entity.UserAddress;
import com.pizzeriadiroma.pizzeria.service.OrderService;
import com.pizzeriadiroma.pizzeria.service.UserAddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pizzeriadiroma.pizzeria.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {

    private final UserService userService;
    private final OrderService orderService;
    private final UserAddressService addressService;

    public ProfileController (UserService userService, OrderService orderService, UserAddressService addressService) {
        this.userService = userService;
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();

        updateProfileRequest.setFirstName(user.getFirstName());
        updateProfileRequest.setLastName(user.getLastName());
        updateProfileRequest.setEmail(user.getEmail());
        updateProfileRequest.setPhone(user.getPhone());

        model.addAttribute("updateProfileRequest", updateProfileRequest);

        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);

        List<UserAddress> addresses = addressService.getAddressesByUser(user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("newAddress", new UserAddress());

        long totalOrders = orderService.countOrdersByUser(user);
        BigDecimal totalSpent = orderService.calculateTotalSpent(user);
        BigDecimal averageOrderValue = orderService.calculateAverageOrderValue(user);

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalSpent", totalSpent);
        model.addAttribute("averageOrderValue", averageOrderValue);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(Model model, @Valid @ModelAttribute("updateProfileRequest") UpdateProfileRequest updateProfileRequest,
                                BindingResult result,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("updateProfileRequest", updateProfileRequest);
            return "profile";
        }

        user.setFirstName(updateProfileRequest.getFirstName());
        user.setLastName(updateProfileRequest.getLastName());
        user.setEmail(updateProfileRequest.getEmail());
        user.setPhone(updateProfileRequest.getPhone());

        userService.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordRequest changePasswordRequest,
                                  BindingResult result,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordError", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/profile";
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("passwordError", "New passwords do not match.");
            return "redirect:/profile";
        }

        String error = userService.changePassword(principal.getName(), 
                                                    changePasswordRequest.getCurrentPassword(),
                                                    changePasswordRequest.getNewPassword());

        if (error != null) {
            redirectAttributes.addFlashAttribute("passwordError", error);
        } else {
            redirectAttributes.addFlashAttribute("passwordSuccess", "Password changed successfully!");
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(@RequestParam("password") String password,
                               Principal principal,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {

        String error = userService.deleteUser(principal.getName(), password);

        if (error != null) {
            redirectAttributes.addFlashAttribute("deleteError", error);
            return "redirect:/profile";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Your account has been successfully deleted.");
        return "redirect:/login";
    }

    @PostMapping("/profile/address/add")
    public String addAddress(@Valid @ModelAttribute("newAddress") UserAddress address,
                            BindingResult bindingResult,
                            Principal principal,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            User user = userService.findByEmail(principal.getName());
            model.addAttribute("user", user);
            
            // Populate UpdateProfileRequest with user data
            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
            updateProfileRequest.setFirstName(user.getFirstName());
            updateProfileRequest.setLastName(user.getLastName());
            updateProfileRequest.setEmail(user.getEmail());
            updateProfileRequest.setPhone(user.getPhone());
            model.addAttribute("updateProfileRequest", updateProfileRequest);
            
            List<Order> orders = orderService.getOrdersByUser(user);
            model.addAttribute("orders", orders);
            
            List<UserAddress> addresses = addressService.getAddressesByUser(user);
            model.addAttribute("addresses", addresses);
            
            long totalOrders = orderService.countOrdersByUser(user);
            BigDecimal totalSpent = orderService.calculateTotalSpent(user);
            BigDecimal averageOrderValue = orderService.calculateAverageOrderValue(user);
            
            model.addAttribute("totalOrders", totalOrders);
            model.addAttribute("totalSpent", totalSpent);
            model.addAttribute("averageOrderValue", averageOrderValue);
            
            return "profile";
        }
        
        User user = userService.findByEmail(principal.getName());
        address.setUser(user);
        address.setIsDefault(false);
        
        addressService.saveAddress(address);
        redirectAttributes.addFlashAttribute("addressSuccess", "Address added successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/profile/address/{id}/set-default")
    public String setDefaultAddress(@PathVariable Long id,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) {
        
        User user = userService.findByEmail(principal.getName());
        addressService.setAsDefault(id, user);
        redirectAttributes.addFlashAttribute("addressSuccess", "Default address updated!");
        return "redirect:/profile";
    }

    @PostMapping("/profile/address/{id}/delete")
    public String deleteAddress(@PathVariable Long id,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        
        User user = userService.findByEmail(principal.getName());
        addressService.getAddressById(id).ifPresent(address -> {
            if (address.getUser().getId().equals(user.getId())) {
                addressService.deleteAddress(id);
                redirectAttributes.addFlashAttribute("addressSuccess", "Address deleted successfully!");
            }
        });
        return "redirect:/profile";
    }

}
