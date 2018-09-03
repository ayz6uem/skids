package com.ybyc.skids.charge.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybyc.skids.charge.common.model.AccessToken;
import com.ybyc.skids.charge.common.model.EncryptRequestData;
import com.ybyc.skids.charge.common.model.EncryptResponseData;
import com.ybyc.skids.charge.common.model.RequestData;
import com.ybyc.skids.charge.helper.JsonHelper;
import com.ybyc.skids.charge.helper.RewriteServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
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
            doResponse(servletResponse,"不允许访问的平台");
            return;
        }

        if(!operator.getSignHelper().check(encryptRequestData)){
            doResponse(servletResponse,"签名错误");
            return;
        }

        if(!isQueryToken(request)){
            if(!AccessTokenContext.contain(encryptRequestData.getOperatorID())){
                doResponse(servletResponse,2,"token错误");
                return;
            }
            //校验token
            String token = request.getHeader("Authorization");
            AccessToken accessToken = AccessTokenContext.get(encryptRequestData.getOperatorID());
            if(!Objects.equals("Bearer "+accessToken.getAccessToken(),token)){
                doResponse(servletResponse,2,"token错误");
                return;
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

    /**
     * 封装响应请求参数
     * @return
     */
    public void doResponse(ServletResponse response,int ret,  String msg) throws IOException {
        String result = JsonHelper.toJson(Response.fail(ret,msg));
        log.info("filter response:{}",result);
        response.getWriter().write(result);
    }

    /**
     * 封装响应请求参数
     * @return
     */
    public void doResponse(ServletResponse response, String msg) throws IOException {
        doResponse(response,500,msg);
    }
}
