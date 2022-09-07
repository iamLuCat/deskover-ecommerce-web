package com.deskover.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deskover.dto.ecommerce.BrandDTO;
import com.deskover.dto.ecommerce.CartLocal;
import com.deskover.dto.ecommerce.Filter;
import com.deskover.dto.ecommerce.Item;
import com.deskover.dto.ecommerce.OrderDetailDTO;
import com.deskover.dto.ecommerce.OrderPage;
import com.deskover.dto.ecommerce.ProductDTO;
import com.deskover.dto.ecommerce.ProductSaleDTO;
import com.deskover.dto.ecommerce.Reviewer;
import com.deskover.dto.ecommerce.Shop;
import com.deskover.entity.Brand;
import com.deskover.entity.Cart;
import com.deskover.entity.Order;
import com.deskover.entity.Product;
import com.deskover.entity.Rating;
import com.deskover.entity.Users;
import com.deskover.reponsitory.BrandRepository;
import com.deskover.reponsitory.CartRepository;
import com.deskover.reponsitory.FlashSaleRepository;
import com.deskover.reponsitory.OrderRepository;
import com.deskover.reponsitory.ProductRepository;
import com.deskover.reponsitory.RatingRepository;
import com.deskover.reponsitory.UserRepository;
import com.deskover.service.ProductService;
import com.deskover.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private FlashSaleRepository flashSaleRepo;

	@Autowired
	private BrandRepository brandRepo;
	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public Shop search(Filter filter) {
		String keyword = filter.getKeyword();
		Pageable pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage());
		
		switch (filter.getSort()) {
		case "1":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("price").ascending());
			break;
		case "2":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("price").descending());
			break;
		case "3":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("averageRating").descending());
			break;
		case "4":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("name").ascending());
			break;
		case "5":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("name").descending());
			break;
		}
		
		Page<Product> products = productRepo.searchPage(keyword, 
				filter.getCategory(), 
				filter.getSubcategory(), 
				filter.getMinPrice(), 
				filter.getMaxPrice(), 
				filter.getBrands(),
				pageable);
		
		return new Shop(products);
	}
	
	@Override
	public ProductDTO getProduct(String slug) {
		Product product = productRepo.findBySlug(slug);
		ProductDTO data = new ProductDTO(product);
		
		return data;
	}

	@Override
	public List<Item> getRecommendList(Long category) {
		Page<Product> items = productRepo.getProductBasedOnCategoryID(category, PageRequest.of(0,20));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<Item> get4TopRate() {
		Page<Product> items = productRepo.getProduct(PageRequest.of(0, 4, Sort.by("averageRating").descending()));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<Item> get4TopSale() {
		Page<Product> items = productRepo.getProduct(PageRequest.of(0, 4, Sort.by("priceSale").descending()));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<Item> get4TopSold() {
		Page<Product> items = productRepo.getProduct(PageRequest.of(0, 4, Sort.by("totalSold").descending()));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}
	
	@Override
	public List<Item> getProductNew() {
	    List<Product> products = productRepo.findByActivedAndQuantityGreaterThanOrderByModifiedAtDesc(true, (long) 0);
		return products.stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<ProductSaleDTO> getFlashSale() {
		List<Product> products = productRepo.findByFlashSaleActivedAndDiscountActived(Boolean.TRUE,Boolean.TRUE);
		
//		if(products == null) {
//			List<Product> products1 = new ArrayList<Product>();
//			Product newPr = new Product();
//			products1.add(newPr);
//			return null;
//		}
		return products.stream().map(product -> new ProductSaleDTO(product)).collect(Collectors.toList());
	}

	@Override
	public List<BrandDTO> getListBrand() {
		List<Brand> b = brandRepo.findByActived(true);
		return b.stream().map(brand -> new BrandDTO(brand)).collect(Collectors.toList());
	}
	
	@Override
	public Reviewer getReviewer(String slug, Integer page) {
		Page<Rating> ratings = ratingRepo.getRating(slug, PageRequest.of(page, 4, Sort.by("modifiedAt").descending()));
		return new Reviewer(ratings);
	}

	@Override
	public List<CartDTO> getCart(List<CartLocal> cartLocal, String username) {
		List<Cart> cart = cartRepo.findByUserUsername(username);
		List<CartDTO> returnCart = new ArrayList<>();
		List<String> items = cartLocal.stream().map(CartLocal::getSlug).collect(Collectors.toList());
		CartLocal itemLocal;
		Product product;
		
		for (Cart item : cart) {
			if(items.contains(item.getProduct().getSlug())) {
				itemLocal = cartLocal.stream().filter(i -> i.getSlug().equals(item.getProduct().getSlug())).findAny().get();

				item.setQuantity(item.getQuantity());
				cartRepo.save(item);
				cartLocal.remove(itemLocal);
				items.remove(item.getProduct().getSlug());
			}
			returnCart.add(new CartDTO(new Item(item.getProduct()), item.getQuantity()));
		}
		
		if(!cartLocal.isEmpty()) {
			Users user = userRepo.findByUsername(username);
			
			for (CartLocal item : cartLocal) {
				product = productRepo.findBySlug(item.getSlug());
				Cart insert = new Cart();
				
				insert.setProduct(product);
				insert.setQuantity(item.getQuantity());
				insert.setUser(user);
				cartRepo.save(insert);
				
				returnCart.add(new CartDTO(new Item(product), item.getQuantity()));
			}
		}
		return returnCart;
	}

	@Override
	public List<CartDTO> deleteCart(String slug, String username) {
		Cart cart = cartRepo.findByProductSlugAndUserUsername(slug,username);
		cartRepo.delete(cart);
		List<Cart> carts = cartRepo.findByUserUsername(username);
		return carts.stream().map(c -> new CartDTO(new Item(c.getProduct()), c.getQuantity())).collect(Collectors.toList());
	}
	
	@Override
	public void deleteAllCart(String username) {
		List<Cart> carts = cartRepo.findByUserUsername(username);
		cartRepo.deleteAll(carts);
	}
	
	@Override
	public List<CartDTO> updateCart(CartLocal item, String username) {
		Cart cart = cartRepo.findByProductSlugAndUserUsername(item.getSlug(),username);
		
		if(Objects.isNull(cart)) {
			Product product = productRepo.findBySlug(item.getSlug());
			Users user = userRepo.findByUsername(username);
			Cart insert = new Cart();
			
			insert.setProduct(product);
			insert.setQuantity(item.getQuantity());
			insert.setUser(user);
			cartRepo.save(insert);
		}else {
			cart.setQuantity(cart.getQuantity()+1);
			cartRepo.save(cart);
		}
		
		List<Cart> carts = cartRepo.findByUserUsername(username);
		return carts.stream().map(c -> new CartDTO(new Item(c.getProduct()), c.getQuantity())).collect(Collectors.toList());
	}

	@Override
	public OrderPage getOrder(String username, Integer page, String filter) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
		
		Page<Order> orders;
		
		switch (filter) {
		case "1":
			orders = orderRepo.getListOrderByUsernameDelivered(username, pageable);
			break;
		case "2":
			orders = orderRepo.getListOrderByUsernameUnDelivered(username, pageable);
			break;
		case "3":
			orders = orderRepo.getListOrderByUsernamePaid(username, pageable);
			break;
		case "4":
			orders = orderRepo.getListOrderByUsernameUnPaid(username, pageable);
			break;
		default:
			orders = orderRepo.getListOrderByUsername(username, pageable);
			break;
		}
		
		
		return new OrderPage(orders);
	}

	@Override
	public OrderDetailDTO getOrderDetail(String username, String id) {
		Order order = orderRepo.findOrderByUsernameAndID(username,id);
		return new OrderDetailDTO(order);
	}

	
	@Override
	public OrderDetailDTO getOrderDetail(String id) {
		Order order = orderRepo.findOrderByID(id);
		return new OrderDetailDTO(order);
	}

}
