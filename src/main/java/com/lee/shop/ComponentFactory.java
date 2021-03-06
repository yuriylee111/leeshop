package com.lee.shop;

import com.lee.shop.action.Action;
import com.lee.shop.action.admin.*;
import com.lee.shop.action.common.*;
import com.lee.shop.action.user.GetOrderAction;
import com.lee.shop.action.user.*;
import com.lee.shop.dao.CategoryDao;
import com.lee.shop.dao.ProductDao;
import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.dao.UserDao;
import com.lee.shop.dao.impl.CategoryDaoImpl;
import com.lee.shop.dao.impl.ProductDaoImpl;
import com.lee.shop.dao.impl.ShopOrderDaoImpl;
import com.lee.shop.dao.impl.UserDaoImpl;
import com.lee.shop.jdbc.JdbcConnectionPool;
import com.lee.shop.model.mapper.*;
import com.lee.shop.security.MD5PasswordEncoder;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.service.ShoppingCartService;
import com.lee.shop.service.impl.ShoppingCartServiceImpl;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ComponentFactory {

    private static final Logger LOGGER = Logger.getLogger(ComponentFactory.class);
    private static final String GET = "GET ";
    private static final String POST = "POST ";
    private static final String COMPONENT_FACTORY_CREATED = "ComponentFactory created";

    public static ComponentFactory getInstance() {
        return ComponentFactoryHolder.INSTANCE;
    }

    private final JdbcConnectionPool jdbcConnectionPool;
    private final CategoryDao categoryDao;
    private final Map<String, Action> actionMap;

    private ComponentFactory() {
        ApplicationPropertiesLoader applicationPropertiesLoader = new ApplicationPropertiesLoader();
        Properties applicationProperties = applicationPropertiesLoader.load();
        jdbcConnectionPool = new JdbcConnectionPool(applicationProperties);
        PasswordEncoder passwordEncoder = new MD5PasswordEncoder();

        categoryDao = new CategoryDaoImpl(jdbcConnectionPool);
        ProductDao productDao = new ProductDaoImpl(jdbcConnectionPool);
        UserDao userDao = new UserDaoImpl(jdbcConnectionPool);
        ShopOrderDao shopOrderDao = new ShopOrderDaoImpl(jdbcConnectionPool);

        HttpServletRequestToSignInDtoMapper httpServletRequestToSignInDtoMapper = new HttpServletRequestToSignInDtoMapper();
        HttpServletRequestToUserDtoMapper httpServletRequestToUserDtoMapper = new HttpServletRequestToUserDtoMapper();
        UserDtoToUserMapper userDtoToUserMapper = new UserDtoToUserMapper(passwordEncoder);
        HttpServletRequestToAddToShoppingCartDtoMapper httpServletRequestToAddToShoppingCartDtoMapper =
                new HttpServletRequestToAddToShoppingCartDtoMapper();
        ShoppingCartToShopOrderMapper shoppingCartToShopOrderMapper = new ShoppingCartToShopOrderMapper();
        UserToUserDtoMapper userToUserDtoMapper = new UserToUserDtoMapper();

        ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();

        actionMap = new HashMap<>();

        // Common actions
        actionMap.put(GET + Constants.Url.SHOW_PRODUCTS, new ShowProductsAction(productDao));
        actionMap.put(GET + Constants.Url.SHOW_PRODUCTS_BY_CATEGORY, new ShowProductsByCategoryAction(productDao));
        actionMap.put(GET + Constants.Url.SIGN_IN, new ShowSignInDtoAction());
        actionMap.put(POST + Constants.Url.SIGN_IN, new SignInAction(httpServletRequestToSignInDtoMapper, userDao, passwordEncoder));
        actionMap.put(GET + Constants.Url.SIGN_UP, new ShowSignUpDtoAction());
        actionMap.put(POST + Constants.Url.SIGN_UP, new SignUpAction(httpServletRequestToUserDtoMapper, userDtoToUserMapper, userDao));
        actionMap.put(GET + Constants.Url.SIGN_OUT, new SignOutAction());

        // User actions
        actionMap.put(GET + Constants.Url.USER_ADD_TO_SHOPPING_CART,
                new ShowAddToShoppingCartDtoAction(httpServletRequestToAddToShoppingCartDtoMapper, productDao));
        actionMap.put(POST + Constants.Url.USER_ADD_TO_SHOPPING_CART,
                new AddToShoppingCartAction(httpServletRequestToAddToShoppingCartDtoMapper, productDao, shoppingCartService));
        actionMap.put(GET + Constants.Url.USER_SHOPPING_CART, new ShowShoppingCartAction());
        actionMap.put(GET + Constants.Url.USER_REMOVE_FROM_SHOPPING_CART, new RemoveFromShoppingCartAction(shoppingCartService));

        actionMap.put(POST + Constants.Url.USER_MAKE_ORDER, new MakeOrderAction(shoppingCartToShopOrderMapper, shopOrderDao));
        actionMap.put(GET + Constants.Url.USER_MY_ORDERS, new MyOrdersAction(shopOrderDao));
        actionMap.put(GET + Constants.Url.USER_GET_ORDER, new GetOrderAction(shopOrderDao));
        actionMap.put(GET + Constants.Url.USER_MY_ACCOUNT, new ShowMyAccountAction(userToUserDtoMapper));
        actionMap.put(POST + Constants.Url.USER_MY_ACCOUNT,
                new UpdateMyAccountAction(httpServletRequestToUserDtoMapper, userDtoToUserMapper, userDao));

        // Admin actions
        actionMap.put(GET + Constants.Url.ADMIN_SHOW_ALL_USERS, new ShowAllUsersAction(userDao));
        actionMap.put(GET + Constants.Url.LOCK_USER, new LockUserAction(userDao));
        actionMap.put(GET + Constants.Url.UNLOCK_USER, new UnLockUserAction(userDao));

        actionMap.put(GET + Constants.Url.ADMIN_ALL_ORDERS, new ShowAllOrdersAction(shopOrderDao));
        actionMap.put(GET + Constants.Url.ADMIN_GET_ORDER, new com.lee.shop.action.admin.GetOrderAction(shopOrderDao));
        actionMap.put(GET + Constants.Url.ADMIN_SET_ORDER_STATUS, new SetOrderStatusAction(shopOrderDao));

        LOGGER.info(COMPONENT_FACTORY_CREATED);
    }

    public JdbcConnectionPool getJdbcConnectionPool() {
        return jdbcConnectionPool;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public Action getAction(String key) {
        return actionMap.get(key);
    }

    private static final class ComponentFactoryHolder {

        private static final ComponentFactory INSTANCE = new ComponentFactory();
    }
}
