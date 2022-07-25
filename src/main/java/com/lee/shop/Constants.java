package com.lee.shop;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Constants {

    public static final String RU = "ru";
    public static final String EN = "en";
    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList(RU, EN);
    public static final String DEFAULT_LANGUAGE = EN;

    public static final String CATEGORY_LIST = "CATEGORY_LIST";
    public static final String CURRENT_SESSION_USER = "CURRENT_SESSION_USER";
    public static final String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";
    public static final String CURRENT_SHOPPING_CART = "CURRENT_SHOPPING_CART";
    public static final String ERROR_MAP = "ERROR_MAP";
    public static final String DTO = "DTO";
    public static final String ENCODED_CURRENT_URL = "ENCODED_CURRENT_URL";
    public static final String UTF_8 = "UTF-8";
    public static final String BACK_URL = "backUrl";

    public static final class Url {

        // Common actions:
        public static final String SHOW_PRODUCTS = "/show-products";
        public static final String SHOW_PRODUCTS_BY_CATEGORY = "/show-products-by-category";
        public static final String SIGN_IN = "/sign-in";
        public static final String SIGN_UP = "/sign-up";
        public static final String SIGN_OUT = "/sign-out";

        // User actions:
        private static final String USER_PREFIX = "/user";
        public static final String USER_ADD_TO_SHOPPING_CART = USER_PREFIX + "/add-to-shopping-cart";
        public static final String USER_SHOPPING_CART = USER_PREFIX + "/shopping-cart";
        public static final String USER_REMOVE_FROM_SHOPPING_CART = USER_PREFIX + "/remove-from-shopping-cart";
        public static final String USER_MAKE_ORDER = USER_PREFIX + "/make-order";
        public static final String USER_MY_ORDERS = USER_PREFIX + "/my-orders";
        public static final String USER_GET_ORDER = USER_PREFIX + "/get-order";
        public static final String USER_MY_ACCOUNT = USER_PREFIX + "/my-account";

        // Admin actions
        private static final String ADMIN_PREFIX = "/admin";
        public static final String ADMIN_SHOW_ALL_USERS = ADMIN_PREFIX + "/all-users";
        public static final String LOCK_USER = ADMIN_PREFIX + "/lock";
        public static final String UNLOCK_USER = ADMIN_PREFIX + "/un-lock";
        public static final String ADMIN_ALL_ORDERS = ADMIN_PREFIX + "/all-orders";
        public static final String ADMIN_GET_ORDER = ADMIN_PREFIX + "/get-order";
        public static final String ADMIN_SET_ORDER_STATUS = ADMIN_PREFIX + "/set-order-status";
    }

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
}
