package jp.techcof.ITive.general.config;

import jp.techcof.ITive.common.config.BooksImageConfig;
import jp.techcof.ITive.common.util.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Optional;

/**
 * ブック画像設定
 */
@ConfigurationProperties("general-config.image.books")
@Component
@Setter
public class BooksImageConfigImpl implements BooksImageConfig {
    /** ベースURL */
    @Getter
    private String baseUrl;
    /** 一時ベースURL */
    @Getter
    private String tempBaseUrl;
    /** アップロード先ディレクトリ */
    private String uploadDir;
    /** 一時アップロード先ディレクトリ */
    private String tempUploadDir;
    /** パス構成の階層 */
    @Getter
    private Integer dirHierarchy;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Path> getUploadDir() {
        if (Strings.isBlank(this.uploadDir)) {
            return Optional.empty();
        }
        return Optional.of(Path.of(this.uploadDir));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Path> getTempUploadDir() {
        if (Strings.isBlank(this.tempUploadDir)) {
            return Optional.empty();
        }
        return Optional.of(Path.of(this.tempUploadDir));
    }
}
