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
import com.lee.shop.exception.ApplicationException;
import com.lee.shop.jdbc.JdbcConnectionPool;
import com.lee.shop.security.MD5PasswordEncoder;
import com.lee.shop.security.PasswordEncoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ComponentFactory {

    private static final Logger LOGGER = Logger.getLogger(ComponentFactory.class);

    public static ComponentFactory getInstance() {
        return ComponentFactoryHolder.INSTANCE;
    }

    private final JdbcConnectionPool jdbcConnectionPool;
    private final CategoryDao categoryDao;
    private final Map<String, Action> actionMap;

    private ComponentFactory() {
        Properties applicationProperties = createApplicationProperties();
        jdbcConnectionPool = new JdbcConnectionPool(applicationProperties);
        PasswordEncoder passwordEncoder = new MD5PasswordEncoder();

        categoryDao = new CategoryDaoImpl(jdbcConnectionPool);
        ProductDao productDao = new ProductDaoImpl(jdbcConnectionPool);
        UserDao userDao = new UserDaoImpl(jdbcConnectionPool);
        ShopOrderDao shopOrderDao = new ShopOrderDaoImpl(jdbcConnectionPool);

        actionMap = new HashMap<>();

        // Common actions
        actionMap.put("GET " + Constants.Url.SHOW_PRODUCTS, new ShowProductsAction(productDao));
        actionMap.put("GET " + Constants.Url.SHOW_PRODUCTS_BY_CATEGORY, new ShowProductsByCategoryAction(productDao));
        actionMap.put("GET " + Constants.Url.SIGN_IN, new ShowSignInFormAction());
        actionMap.put("POST " + Constants.Url.SIGN_IN, new SignInAction(userDao, passwordEncoder));
        actionMap.put("GET " + Constants.Url.SIGN_UP, new ShowSignUpFormAction());
        actionMap.put("POST " + Constants.Url.SIGN_UP, new SignUpAction(userDao, passwordEncoder));
        actionMap.put("GET " + Constants.Url.SIGN_OUT, new SignOutAction());

        // User actions
        actionMap.put("GET " + Constants.Url.USER_ADD_TO_SHOPPING_CART, new ShowAddToShoppingCartFormAction(productDao));
        actionMap.put("POST " + Constants.Url.USER_ADD_TO_SHOPPING_CART, new AddToShoppingCartAction(productDao));
        actionMap.put("GET " + Constants.Url.USER_SHOPPING_CART, new ShowShoppingCartAction());
        actionMap.put("GET " + Constants.Url.USER_REMOVE_FROM_SHOPPING_CART, new RemoveFromShoppingCartAction());

        actionMap.put("POST " + Constants.Url.USER_MAKE_ORDER, new MakeOrderAction(shopOrderDao));
        actionMap.put("GET " + Constants.Url.USER_MY_ORDERS, new MyOrdersAction(shopOrderDao));
        actionMap.put("GET " + Constants.Url.USER_GET_ORDER, new GetOrderAction(shopOrderDao));
        actionMap.put("GET " + Constants.Url.USER_MY_ACCOUNT, new ShowMyAccountAction());
        actionMap.put("POST " + Constants.Url.USER_MY_ACCOUNT, new UpdateMyAccountAction(userDao, passwordEncoder));

        // Admin actions
        actionMap.put("GET " + Constants.Url.ADMIN_SHOW_ALL_USERS, new ShowAllUsersAction(userDao));
        actionMap.put("GET " + Constants.Url.LOCK_USER, new LockUserAction(userDao));
        actionMap.put("GET " + Constants.Url.UNLOCK_USER, new UnLockUserAction(userDao));

        actionMap.put("GET " + Constants.Url.ADMIN_ALL_ORDERS, new ShowAllOrdersAction(shopOrderDao));
        actionMap.put("GET " + Constants.Url.ADMIN_GET_ORDER, new com.lee.shop.action.admin.GetOrderAction(shopOrderDao));
        actionMap.put("GET " + Constants.Url.ADMIN_SET_ORDER_STATUS, new SetOrderStatusAction(shopOrderDao));

        LOGGER.info("ComponentFactory created");
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

    private Properties createApplicationProperties() {
        Properties properties = new Properties();
        try {
            properties.load(ComponentFactory.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException exception) {
            throw new ApplicationException("Can't load properties from classpath:/application.properties", exception);
        }
        return properties;
    }


    private static final class ComponentFactoryHolder {

        private static final ComponentFactory INSTANCE = new ComponentFactory();
    }
}
