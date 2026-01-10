package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.dto.CheckoutRequest;
import com.pizzeriadiroma.pizzeria.entity.*;
import com.pizzeriadiroma.pizzeria.service.CartService;
import com.pizzeriadiroma.pizzeria.service.CompanyInfoService;
import com.pizzeriadiroma.pizzeria.service.OrderService;
import com.pizzeriadiroma.pizzeria.service.UserAddressService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserAddressService userAddressService;
    private final CompanyInfoService companyInfoService;

    public CheckoutController(CartService cartService, OrderService orderService, UserAddressService userAddressService, CompanyInfoService companyInfoService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userAddressService = userAddressService;
        this.companyInfoService = companyInfoService;
    }

    @GetMapping
    public String showCheckoutPage(@AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getOrCreateCart(user);
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        List<UserAddress> userAddresses = userAddressService.getAddressesByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("userAddresses", userAddresses);
        model.addAttribute("companyInfo", companyInfoService.getCompanyInfo());
        model.addAttribute("deliveryFee", cartService.calculateDeliveryFee(cart));

        model.addAttribute("checkoutForm", prefill(user, userAddresses));

        return "checkout";
    }

    @PostMapping("/process")
    public String processCheckout(@AuthenticationPrincipal User user,
                                  @Valid @ModelAttribute("checkoutForm") CheckoutRequest checkoutRequest,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        Cart cart = cartService.getOrCreateCart(user);
        if (cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        if (checkoutRequest.getDeliveryMethod() == Order.DeliveryMethod.DELIVERY && checkoutRequest.getAddressId() == null) {
            bindingResult.rejectValue("addressId", "required", "Please select a delivery address");
        }

        if (bindingResult.hasErrors()) {
            refillModel(model, user, cart, checkoutRequest);
            return "checkout";
        }

        try {
            UserAddress deliveryAddress = null;
            if (checkoutRequest.getDeliveryMethod() == Order.DeliveryMethod.DELIVERY) {
                deliveryAddress = resolveAddress(user, checkoutRequest.getAddressId());
            }

            Order order = orderService.createOrder(
                    user,
                    cart,
                    deliveryAddress,
                    checkoutRequest.getDeliveryMethod(),
                    checkoutRequest.getPaymentMethod(),
                    checkoutRequest.getDeliveryInstructions()
            );

            cartService.clearCart(user);
            redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
            redirectAttributes.addFlashAttribute("orderNumber", order.getOrderNumber());
            redirectAttributes.addFlashAttribute("orderId", order.getId());
            return "redirect:/checkout/success";

        } catch (Exception e) {
            refillModel(model, user, cart, checkoutRequest);
            model.addAttribute("error", "Failed to process order: " + e.getMessage());
            return "checkout";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "checkout_success";
    }

    private CheckoutRequest prefill(User user, List<UserAddress> addresses) {
        CheckoutRequest checkoutRequest = new CheckoutRequest();

        Long defaultAddressId = addresses.stream()
                .filter(UserAddress::getIsDefault)
                .map(UserAddress::getId)
                .findFirst()
                .orElse(null);

        checkoutRequest.setFullName(user.getFirstName() + " " + user.getLastName());
        checkoutRequest.setEmail(user.getEmail());
        checkoutRequest.setPhone(user.getPhone());

        checkoutRequest.setDeliveryMethod(Order.DeliveryMethod.DELIVERY);
        checkoutRequest.setAddressId(defaultAddressId);

        checkoutRequest.setPaymentMethod(Order.PaymentMethod.CASH);

        return checkoutRequest;
    }

    private void refillModel(Model model, User user, Cart cart, CheckoutRequest checkoutRequest) {
        List<UserAddress> addressesByUser = userAddressService.getAddressesByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("userAddresses", addressesByUser);
        model.addAttribute("companyInfo", companyInfoService.getCompanyInfo());
        model.addAttribute("deliveryFee", cartService.calculateDeliveryFee(cart));

        model.addAttribute("checkoutForm", checkoutRequest);
    }

    private UserAddress resolveAddress(User user, Long addressId) {
        if (addressId == null) {
            throw new IllegalArgumentException("Please select a delivery address");
        }

        UserAddress addr = userAddressService.getAddressById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid address selected"));

        if (!addr.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Invalid address selected");
        }
        return addr;
    }
}
