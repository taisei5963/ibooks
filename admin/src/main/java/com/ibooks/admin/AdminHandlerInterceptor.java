package com.ibooks.admin;

import com.ibooks.admin.constants.AdminActionRole;
import com.ibooks.admin.dto.AdminDto;
import com.ibooks.common.annotation.NotLogin;
import com.ibooks.common.constants.SystemConst;
import com.ibooks.common.exception.RoleException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 管理サイトハンドラーインタセプター
 */
public class AdminHandlerInterceptor implements HandlerInterceptor {
    /** 管理者DTO */
    @Autowired
    private AdminDto adminDto;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest req, HttpServletResponse resp,
                             @NotNull Object handler) throws Exception {
        if (resp.isCommitted()) {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            NotLogin annotation = AnnotationUtils.findAnnotation(method, NotLogin.class);
            if (Objects.isNull(annotation)) {
                if (!adminDto.isLogin()) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return false;
                }
                checkAccessible(req.getServletPath());
            }
        } else {
            if (!checkPublicContents(req.getRequestURI()) && !adminDto.isLogin()) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp, @NotNull Object handler,
                           ModelAndView modelAndView) {
        if (!Objects.isNull(modelAndView)) {
            modelAndView.addObject("admin", adminDto);
            Map<String, Object> system = new HashMap<>();
            system.put("listParameterDelimiter", SystemConst.LIST_PARAM_DELIMITER);
            system.put("listParameterMax", SystemConst.LIST_PARAM_MAX);
            modelAndView.addObject("system", system);
        }
    }

    /**
     * アクセス先の静的コンテンツが公開コンテンツの場合は true を返却する
     *
     * @param requestUri リクエストURI
     * @return {@code true} 公開コンテンツ
     */
    private boolean checkPublicContents(String requestUri) {
        if (requestUri.contains("/public/css/")) {
            return true;
        } else if (requestUri.contains("/public/js/")) {
            return true;
        } else if (requestUri.contains("/public/fonts/")) {
            return true;
        }
        return requestUri.contains("/public/img/");
    }

    /**
     * 引数のサーブレットパスにアクセスできるかどうかをチェックする<br>
     * アクセスできない場合は例外エラーをスローする
     *
     * @param servletPath サーブレットパス
     */
    private void checkAccessible(String servletPath) {
        String[] tmpPath = servletPath.split("/");
        String action = tmpPath.length < 2 ? "" : tmpPath[1];
        if (action.isEmpty()) {
            return;
        }
        List<String> inAccessiblePaths = adminDto.getInAccessibleActionPathList();
        if (Objects.isNull(inAccessiblePaths) || inAccessiblePaths.isEmpty()) {
            return;
        }
        for (String inAccessiblePath : inAccessiblePaths) {
            AdminActionRole role = AdminActionRole.getEnum(inAccessiblePath);
            if (Objects.isNull(role)) {
                continue;
            }
            List<String> inAccessibleActions = Arrays.asList(role.getActions());
            if (!inAccessibleActions.contains(action)) {
                continue;
            }
            throw new RoleException(role.getDescription() + "に対する権限が有りません。");
        }
    }
}
