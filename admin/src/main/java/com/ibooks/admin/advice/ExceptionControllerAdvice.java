package com.ibooks.admin.advice;

import com.ibooks.admin.dto.AdminDto;
import com.ibooks.common.annotation.NotLogin;
import com.ibooks.common.exception.DownloadFileNotFoundException;
import com.ibooks.common.exception.EntityNotFoundException;
import com.ibooks.common.exception.RoleException;
import com.ibooks.common.exception.SystemException;
import com.ibooks.common.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 例外エラーハンドリング
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    /** ログ */
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    /** 管理者DTO */
    @Autowired
    private AdminDto adminDto;
    /** エラーメッセージサービス */
    @Autowired
    private MessageService messageService;

    /**
     * 権限エラーをハンドリングする
     *
     * @param e       権限エラー
     * @param model   テンプレートモデル
     * @param request リクエスト
     * @return テンプレートパス
     */
    @ExceptionHandler(RoleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @NotLogin
    public String handleRoleException(RoleException e, Model model, WebRequest request) {
        if (isAjaxAccess(request)) {
            return null;
        }
        List<String> messages = new ArrayList<>();
        messages.add(e.getMessage());
        model.addAttribute("errors", messages);
        model.addAttribute("admin", adminDto);
        return "error/role";
    }

    /**
     * システムエラーをハンドリングする
     *
     * @param e       システムエラー
     * @param request リクエスト
     * @return テンプレートパス
     */
    @ExceptionHandler({SystemException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @NotLogin
    public String handleSystemException(SystemException e, WebRequest request) {
        log.error(e.getMessage());
        if (isAjaxAccess(request)) {
            return null;
        }
        return "error";
    }

    /**
     * エンティティが見つからないエラーをハンドリングする
     *
     * @param e       見つからないエラー
     * @param request リクエスト
     * @return テンプレートパス
     */
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @NotLogin
    public String handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        if (isAjaxAccess(request)) {
            return null;
        }
        return "error/404";
    }

    @ExceptionHandler(DownloadFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @NotLogin
    public void handleDownloadFileNotFoundException(DownloadFileNotFoundException e, WebRequest request) {
    }

    /**
     * リソースが見つからないエラーをハンドリングする
     *
     * @param e       見つからないエラー
     * @param request リクエスト
     * @return テンプレートパス
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @NotLogin
    public String handleNoResourceFoundException(NoResourceFoundException e, WebRequest request) {
        if (isAjaxAccess(request)) {
            return null;
        }
        if (isAcceptHtml(request)) {
            return "error/404";
        }
        return null;
    }

    /**
     * 楽観的ロック例外エラーをハンドリングする
     *
     * @param e       例外エラー
     * @param request リクエスト
     * @return テンプレートパス
     */
    @ExceptionHandler({OptimisticLockingFailureException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @NotLogin
    public String handleOptimisticLockingFailureException(OptimisticLockingFailureException e, WebRequest request) {
        if (isAjaxAccess(request)) {
            return null;
        }
        return "error/lockError";
    }

    /**
     * 例外をハンドリングする
     *
     * @param e       例外エラー
     * @param request リクエスト
     * @return テンプレートパス
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @NotLogin
    public String handleException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        if (isAjaxAccess(request)) {
            return null;
        }
        return "error";
    }

    /**
     * JSONリクエストでの型変換エラーをハンドリングする
     *
     * @param e 例外エラー
     * @return 処理結果
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @NotLogin
    public Map<String, Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        if (e.getMessage().contains("Numeric")) {
            errors.add(messageService.getMessage("errors.requestBody.numeric"));
        } else if (e.getMessage().contains("LocalDate")) {
            errors.add(messageService.getMessage("errors.requestBody.localDate"));
        } else {
            errors.add(messageService.getMessage("errors.requestBody.other"));
        }
        response.put("errors", errors);
        return response;
    }

    /**
     * リクエストが JSON や JavaScript を要求している場合は true を返却する
     *
     * @param request リクエスト
     * @return {@code true} JSON や JavaScript を要求
     */
    private boolean isAjaxAccess(WebRequest request) {
        String[] accept = request.getHeaderValues("Accept");
        if (Objects.isNull(accept)) {
            return false;
        }
        for (String value : accept) {
            if (value.contains("application/json") || value.contains("text/javascript")) {
                return true;
            }
        }
        return false;
    }

    /**
     * リクエストが HTML を許容している場合は true を返却する
     *
     * @param request リクエスト
     * @return {@code true} HTMLを許容
     */
    private boolean isAcceptHtml(WebRequest request) {
        String[] accept = request.getHeaderValues("Accept");
        if (Objects.isNull(accept)) {
            return false;
        }
        for (String value : accept) {
            if (value.contains("text/html")) {
                return true;
            }
        }
        return false;
    }
}
