package com.ybyc.skids.charge.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybyc.skids.charge.common.model.AccessToken;
import com.ybyc.skids.charge.common.model.EncryptRequestData;
import com.ybyc.skids.charge.common.model.RequestData;
import com.ybyc.skids.charge.helper.RewriteServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/charge/exchange/*")
public class AccessFilter implements Filter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    Operator operator;

    private boolean isQueryToken(HttpServletRequest request) {
        PathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/charge/exchange/query_token", request.getRequestURI());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        RewriteServletRequest rewriteServletRequest = new RewriteServletRequest(request);
        EncryptRequestData encryptRequestData = objectMapper.readValue(rewriteServletRequest.getOriginInputStream(),EncryptRequestData.class);

        if(!operator.isPlatform(encryptRequestData.getOperatorID())){
            throw new IllegalArgumentException("不允许访问的平台");
        }

        if(!operator.getSignHelper().check(encryptRequestData)){
            throw new IllegalArgumentException("签名错误");
        }

        if(!isQueryToken(request)){
            if(!AccessTokenContext.contain(encryptRequestData.getOperatorID())){
                throw new IllegalArgumentException("token错误");
            }
            //校验token
            String token = request.getHeader("Authorization");
            AccessToken accessToken = AccessTokenContext.get(encryptRequestData.getOperatorID());
            if(!Objects.equals(accessToken.getAccessToken(),token)){
                throw new IllegalArgumentException("token错误");
            }
        }

        String data = operator.getAesCodec().cbcDecrypt(encryptRequestData.getData());
        RequestData requestData = new RequestData();
        requestData.setOperatorID(encryptRequestData.getOperatorID());
        requestData.setData(objectMapper.readTree(data));

        objectMapper.writeValue(rewriteServletRequest.getOutputStream(),requestData);

        filterChain.doFilter(rewriteServletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
