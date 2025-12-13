package jp.blue_dolphin.ibooks.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.blue_dolphin.ibooks.admin.constants.AdminActionRole;
import jp.blue_dolphin.ibooks.admin.dto.AdminDto;
import jp.blue_dolphin.ibooks.common.annotation.UnLogin;
import jp.blue_dolphin.ibooks.common.constant.SystemConst;
import jp.blue_dolphin.ibooks.common.exception.RoleException;
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
 * 管理サイトハンドラーインターセプター
 */
public class AdminHandlerInterceptor implements HandlerInterceptor {
    /** 管理者DTO */
    @Autowired
    private AdminDto adminDto;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {
        if (resp.isCommitted()) {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            UnLogin annotation = AnnotationUtils.findAnnotation(method, UnLogin.class);
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
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler,
                           ModelAndView modelAndView) {
        if (Objects.nonNull(modelAndView)) {
            modelAndView.addObject("admin", adminDto);
            Map<String, Object> system = new HashMap<>();
            system.put("listParameterDelimiter", SystemConst.LIST_PARAMETER_DELIMITER);
            system.put("listParameterMax", SystemConst.LIST_PARAMETER_MAX);
            modelAndView.addObject("system", system);
        }
    }

    /**
     * アクセス先の静的コンテンツが公開コンテンツの場合は true を返却する
     *
     * @param reqUrl リクエストURI
     * @return {@code true} 公開コンテンツ
     */
    private boolean checkPublicContents(String reqUrl) {
        if (reqUrl.contains("/public/css/")) {
            return true;
        } else if (reqUrl.contains("/public/js/")) {
            return true;
        } else if (reqUrl.contains("/public/fonts/")) {
            return true;
        }
        return reqUrl.contains("/public/img/");
    }

    /**
     * 引数のサーブレットパスにアクセスできるかどうかを確認する<br>
     * アクセスできない場合は例外をスローする
     *
     * @param servletPath サーブレットパス
     */
    private void checkAccessible(String servletPath) {
        String[] tmpPaths = servletPath.split("/");
        String action = tmpPaths.length < 2 ? "" : tmpPaths[1];
        if (action.isEmpty()) {
            return;
        }
        List<String> inAccessiblePaths = adminDto.getInaccessibleActionPaths();
        if (Objects.isNull(inAccessiblePaths) || inAccessiblePaths.isEmpty()) {
            return;
        }
        for (String inAccessiblePath : inAccessiblePaths) {
            AdminActionRole role = AdminActionRole.getEnum(inAccessiblePath);
            if (Objects.isNull(role)) {
                continue;
            }
            List<String> inaccessibleActions = Arrays.asList(role.getActions());
            if (!inaccessibleActions.contains(action)) {
                continue;
            }
            throw new RoleException(role.getDescription() + "に対する権限がありません。");
        }
    }
}
