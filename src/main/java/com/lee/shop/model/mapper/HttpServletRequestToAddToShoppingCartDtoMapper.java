package com.lee.shop.model.mapper;

import com.lee.shop.Constants;
import com.lee.shop.model.dto.AddToShoppingCartDto;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpServletRequestToAddToShoppingCartDtoMapper {

    private static final String PRODUCT_ID = "productId";
    private static final String COUNT = "count";

    public AddToShoppingCartDto map(HttpServletRequest request) {
        return new AddToShoppingCartDto(
                Long.parseLong(request.getParameter(PRODUCT_ID)),
                Integer.parseInt(request.getParameter(COUNT)),
                request.getParameter(Constants.BACK_URL)
        );
    }

    public AddToShoppingCartDto map(long productId, HttpServletRequest request, int count) throws UnsupportedEncodingException {
        return new AddToShoppingCartDto(
                productId,
                count,
                URLDecoder.decode(request.getParameter(Constants.BACK_URL), Constants.UTF_8)
        );
    }
}
