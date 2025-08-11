package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.cart.AddRequest;
import com.leonbros.drac.dto.request.cart.DeleteRequest;
import com.leonbros.drac.dto.response.cart.AddResponse;
import com.leonbros.drac.dto.response.cart.CartItemResponse;
import com.leonbros.drac.dto.response.cart.CartResponse;
import com.leonbros.drac.dto.response.cart.DeleteResponse;
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
import com.leonbros.drac.repository.SizeRepository;
import com.leonbros.drac.repository.UserRepository;
import com.leonbros.drac.service.utils.ServiceUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

  @Autowired
  public CartService(CartRepository cartRepository, UserRepository userRepository,
      ItemRepository itemRepository, SizeRepository sizeRepository, ColorRepository colorRepository,
      CartItemRepository cartItemRepository) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
    this.sizeRepository = sizeRepository;
    this.colorRepository = colorRepository;
    this.cartItemRepository = cartItemRepository;
  }

  @Transactional
  public AddResponse addToCart(HttpServletRequest req, HttpServletResponse resp, AddRequest add) {
    final Cart cart = getOrCreateCart(req, resp);
    final Item item = itemRepository.getByCod(add.getProductId());
    final Size size = sizeRepository.getByCod(add.getSizeId());
    final Color color = colorRepository.getByCod(add.getColorId());

    final CartItem cartItem =
        CartItem.builder().item(item).cart(cart).selectedSize(size).selectedColor(color)
            .quantity(add.getQuantity()).build();

    final CartItem existingCartItem =
        cartItemRepository.getByCartAndItemAndSelectedSizeAndSelectedColor(cart, item, size, color);

    if (existingCartItem != null) {
      existingCartItem.setQuantity(existingCartItem.getQuantity() + add.getQuantity());
      cartItemRepository.save(existingCartItem);
      return new AddResponse(AddResponse.Status.MERGED);
    }

    cartItemRepository.save(cartItem);
    return new AddResponse(AddResponse.Status.SUCCESS);
  }

  @Transactional
  public DeleteResponse delete(HttpServletRequest req, HttpServletResponse resp,
      DeleteRequest del) {
    final Cart cart = getOrCreateCart(req, resp);
    final CartItem cartItem =
        cart.getCartItems().stream().filter(item -> item.getCod().equals(del.getProductId()))
            .findFirst().orElse(null);
    if (cartItem == null) {
      return new DeleteResponse(DeleteResponse.Status.UNEXPECTED_ERROR);
    }
    cart.getCartItems().remove(cartItem);
    cartItemRepository.deleteByCod(cartItem.getCod());
    return new DeleteResponse(DeleteResponse.Status.SUCCESS);
  }

  @Transactional(readOnly = true)
  public CartResponse getCart(HttpServletRequest req, HttpServletResponse resp) {
    final Cart cart = getOrCreateCart(req, resp);
    final List<CartItem> cartItems = cart.getCartItems();
    // TODO: Find a better integration for the total computation.
    final double subtotal = cartItems.stream()
        .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getItem().getPrice()).sum();
    final double shipment = 5;
    final double total = subtotal + shipment;
    return new CartResponse(CartResponse.Status.SUCCESS, String.format("%.2f €", subtotal),
        String.format("%.2f €", shipment), String.format("%.2f €", total),
        computeCartItems(cartItems));
  }

  private List<CartItemResponse> computeCartItems(List<CartItem> cartItems) {
    final List<CartItemResponse> result = new ArrayList<>();
    if (CollectionUtils.isEmpty(cartItems)) {
      return result;
    }
    for (CartItem item : cartItems) {
      final List<ItemColor> itemColors = (item.getItem().getItemColors() != null ?
          item.getItem().getItemColors() :
          Collections.emptyList());
      final List<ItemSize> itemSizes = item.getItem().getItemColors() != null ?
          item.getItem().getItemSizes() :
          Collections.emptyList();
      final List<String> colors =
          itemColors.stream().map(itemColor -> itemColor.getColor().getColor()).toList();
      final List<String> sizes =
          itemSizes.stream().map(itemSize -> itemSize.getSize().getSize()).toList();
      final Long id = item.getCod();
      final Long itemId = item.getItem().getCod();
      final String url = item.getItem().getItemImages().stream()
          .filter(itemImage -> Objects.nonNull(itemImage.getColor()))
          .filter(itemImage -> itemImage.getColor().equals(item.getSelectedColor())).findFirst()
          .orElse(item.getItem().getItemImages().getFirst()).getUrl();
      final String title = item.getItem().getTitle();
      final String selectedColor =
          Optional.ofNullable(item.getSelectedColor()).map(Color::getColor).orElse(null);
      final String selectedSize =
          Optional.ofNullable(item.getSelectedSize()).map(Size::getSize).orElse(null);
      final int quantity = item.getQuantity();
      final String price = String.format("%.2f €", item.getItem().getPrice());
      result.add(
          new CartItemResponse(id, itemId, url, title, selectedColor, selectedSize, quantity, price,
              colors, sizes));
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
    newCart.setCartItems(new ArrayList<>());
    if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
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
