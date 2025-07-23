package com.ibooks.common.dto;

import lombok.AllArgsConstructor;

/**
 * アカウント<br>
 * レビュワーサイト、管理者サイトにログインしている使用者情報です
 */
@AllArgsConstructor
public class Account {
    /** ID */
    public final Long id;

    /** コード */
    public final String code;

    /** 名称 */
    public final String name;

    /** サイト識別子 */
    public final String siteIdentifier;

    /**
     * 引数でアップロードアカウントを作成する
     *
     * @param id             ID
     * @param code           コード
     * @param name           名称
     * @param siteIdentifier サイト識別子
     * @return アップロードアカウント
     */
    public static Account of(Long id, String code, String name, String siteIdentifier) {
        return new Account(id, code, name, siteIdentifier);
    }
}
