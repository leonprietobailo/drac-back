package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.cart.AddRequest;
import com.leonbros.drac.dto.response.cart.AddResponse;
import com.leonbros.drac.dto.response.cart.CartItemResponse;
import com.leonbros.drac.dto.response.cart.CartResponse;
import com.leonbros.drac.entity.cart.Cart;
import com.leonbros.drac.entity.cart.CartItem;
import com.leonbros.drac.entity.item.Color;
import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.ItemColor;
import com.leonbros.drac.entity.item.ItemSize;
import com.leonbros.drac.entity.item.Size;
import com.leonbros.drac.repository.CartItemRepository;
import com.leonbros.drac.repository.CartRepository;
import com.leonbros.drac.repository.ColorRepository;
import com.leonbros.drac.repository.ItemColorRepository;
import com.leonbros.drac.repository.ItemRepository;
import com.leonbros.drac.repository.ItemSizeRepository;
import com.leonbros.drac.repository.SizeRepository;
import com.leonbros.drac.repository.UserRepository;
import com.leonbros.drac.service.utils.ServiceUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService {

  private static final String CART_COOKIE = "CART_COOKIE";

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final SizeRepository sizeRepository;
  private final ColorRepository colorRepository;
  private final CartItemRepository cartItemRepository;
  private final ItemSizeRepository itemSizeRepository;
  private final ItemColorRepository itemColorRepository;

  @Autowired
  public CartService(CartRepository cartRepository, UserRepository userRepository,
      ItemRepository itemRepository, SizeRepository sizeRepository, ColorRepository colorRepository,
      CartItemRepository cartItemRepository, ItemSizeRepository itemSizeRepository,
      ItemColorRepository itemColorRepository) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
    this.sizeRepository = sizeRepository;
    this.colorRepository = colorRepository;
    this.cartItemRepository = cartItemRepository;
    this.itemSizeRepository = itemSizeRepository;
    this.itemColorRepository = itemColorRepository;
  }

  @Transactional
  public AddResponse addToCart(HttpServletRequest req, HttpServletResponse resp,
      AddRequest add) {
    final Cart cart = getOrCreateCart(req, resp);
    final Item item = itemRepository.getByCod(add.getProductId());
    final Size size = sizeRepository.getByCod(add.getSizeId());
    final Color color = colorRepository.getByCod(add.getColorId());

    final CartItem cartItem =
        CartItem.builder().item(item).cart(cart).selectedSize(size).selectedColor(color).quantity(add.getQuantity())
            .build();

    final CartItem existingCartItem =
        cartItemRepository.getByCartAndItemAndSizeAndColor(cart, item, size, color);

    if (existingCartItem != null) {
      existingCartItem.setQuantity(existingCartItem.getQuantity() + add.getQuantity());
      cartItemRepository.save(existingCartItem);
      return new AddResponse(AddResponse.Status.MERGED);
    }

    cartItemRepository.save(cartItem);
    return new AddResponse(AddResponse.Status.SUCCESS);
  }

  @Transactional(readOnly = true)
  public CartResponse getCart(HttpServletRequest req, HttpServletResponse resp) {
    final Cart cart = getOrCreateCart(req, resp);
    return new CartResponse(CartResponse.Status.SUCCESS, computeCartItems(cart.getCartItems()));
  }

  private List<CartItemResponse> computeCartItems(List<CartItem> cartItems) {
    final List<CartItemResponse> result = new ArrayList<>();
    for (CartItem item : cartItems) {
       final List<ItemColor> itemColors
           = (item.getItem().getItemColors() != null ?
          item.getItem().getItemColors() :
          Collections.emptyList());
       final List<ItemSize> itemSizes = item.getItem().getItemColors() != null ?
          item.getItem().getItemSizes() :
          Collections.emptyList();
      final List<String> colors = itemColors.stream().map(itemColor -> itemColor.getColor().getColor()).toList();
      final List<String> sizes = itemSizes.stream().map(itemSize -> itemSize.getSize().getSize()).toList();
      final Long id = item.getCod();
      final String url = item.getItem().getItemImages().stream()
          .filter(itemImage -> itemImage.getColor().equals(item.getSelectedColor())).findFirst()
          .orElseThrow().getUrl();
      final String title = item.getItem().getTitle();
      final String selectedColor = item.getSelectedColor().getColor();
      final String selectedSize = item.getSelectedSize().getSize();
      final int quantity = item.getQuantity();
      final String price = String.format("%.2f", item.getItem().getPrice());
      result.add(new CartItemResponse(id, url, title, selectedColor, selectedSize, quantity, price, colors, sizes));
    }
    return result;
  }

  private Cart getOrCreateCart(HttpServletRequest request, HttpServletResponse response) {
    final Optional<String> cookie = ServiceUtils.readCookie(request, CART_COOKIE);
    Cart cookieCart = null;
    Cart userCart = null;
    if (cookie.isPresent()) {
      cookieCart = cartRepository.findCartByToken(cookie.get());
    }
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.isAuthenticated()) {
      userCart = cartRepository.findCartByUser_Email(auth.getName()).orElse(null);
    }

    if (cookieCart != null && userCart != null) {
      if (userCart.equals(cookieCart)) {
        return userCart;
      }
      log.error("Duplicated cart associated to cookie and user detected.");
    }

    if (userCart != null) {
      return userCart;
    }

    if (cookieCart != null) {
      return cookieCart;
    }

    final Cart newCart = new Cart();

    if (auth.isAuthenticated()) {
      newCart.setUser(userRepository.getUserByEmail(auth.getName()).orElseThrow());
      cartRepository.save(newCart);
      return newCart;
    }

    // Finally, record cookie and return cart.
    cartRepository.save(newCart);
    setCartCookie(response, newCart.getToken());
    return newCart;
  }

  public void setCartCookie(HttpServletResponse response, String token) {
    ResponseCookie cookie =
        ResponseCookie.from(CART_COOKIE, token).httpOnly(true).secure(true).sameSite("Lax")
            .path("/").maxAge(Duration.ofDays(30)).build();
    response.addHeader("Set-Cookie", cookie.toString());
  }

}
