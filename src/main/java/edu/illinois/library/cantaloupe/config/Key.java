package edu.illinois.library.cantaloupe.config;

/**
 * <p>To add a key:</p>
 *
 * <ol>
 *     <li>Add a value for it here.</li>
 *     <li>Add it to the sample config file.</li>
 *     <li>Document the addition in the upgrade guide.</li>
 *     <li>Add a form element to the Control Panel to set its value.</li>
 *     <li>Test the above.</li>
 * </ol>
 */
public enum Key {

    ACCESS_LOG_CONSOLEAPPENDER_ENABLED("log.access.ConsoleAppender.enabled"),
    ACCESS_LOG_FILEAPPENDER_ENABLED("log.access.FileAppender.enabled"),
    ACCESS_LOG_FILEAPPENDER_PATHNAME("log.access.FileAppender.pathname"),
    ACCESS_LOG_ROLLINGFILEAPPENDER_ENABLED("log.access.RollingFileAppender.enabled"),
    ACCESS_LOG_ROLLINGFILEAPPENDER_PATHNAME("log.access.RollingFileAppender.pathname"),
    ACCESS_LOG_ROLLINGFILEAPPENDER_POLICY("log.access.RollingFileAppender.policy"),
    ACCESS_LOG_ROLLINGFILEAPPENDER_FILENAME_PATTERN("log.access.RollingFileAppender.TimeBasedRollingPolicy.filename_pattern"),
    ACCESS_LOG_ROLLINGFILEAPPENDER_MAX_HISTORY("log.access.RollingFileAppender.TimeBasedRollingPolicy.max_history"),
    ACCESS_LOG_SYSLOGAPPENDER_ENABLED("log.access.SyslogAppender.enabled"),
    ACCESS_LOG_SYSLOGAPPENDER_HOST("log.access.SyslogAppender.host"),
    ACCESS_LOG_SYSLOGAPPENDER_PORT("log.access.SyslogAppender.port"),
    ACCESS_LOG_SYSLOGAPPENDER_FACILITY("log.access.SyslogAppender.facility"),
    ADMIN_ENABLED("endpoint.admin.enabled"),
    ADMIN_SECRET("endpoint.admin.secret"),
    ADMIN_USERNAME("endpoint.admin.username"),
    API_ENABLED("endpoint.api.enabled"),
    API_SECRET("endpoint.api.secret"),
    API_USERNAME("endpoint.api.username"),
    APPLICATION_LOG_LEVEL("log.application.level"),
    APPLICATION_LOG_CONSOLEAPPENDER_ENABLED("log.application.ConsoleAppender.enabled"),
    APPLICATION_LOG_FILEAPPENDER_ENABLED("log.application.FileAppender.enabled"),
    APPLICATION_LOG_FILEAPPENDER_PATHNAME("log.application.FileAppender.pathname"),
    APPLICATION_LOG_ROLLINGFILEAPPENDER_ENABLED("log.application.RollingFileAppender.enabled"),
    APPLICATION_LOG_ROLLINGFILEAPPENDER_PATHNAME("log.application.RollingFileAppender.pathname"),
    APPLICATION_LOG_ROLLINGFILEAPPENDER_POLICY("log.application.RollingFileAppender.policy"),
    APPLICATION_LOG_ROLLINGFILEAPPENDER_FILENAME_PATTERN("log.application.RollingFileAppender.TimeBasedRollingPolicy.filename_pattern"),
    APPLICATION_LOG_ROLLINGFILEAPPENDER_MAX_HISTORY("log.application.RollingFileAppender.TimeBasedRollingPolicy.max_history"),
    APPLICATION_LOG_SYSLOGAPPENDER_ENABLED("log.application.SyslogAppender.enabled"),
    APPLICATION_LOG_SYSLOGAPPENDER_HOST("log.application.SyslogAppender.host"),
    APPLICATION_LOG_SYSLOGAPPENDER_PORT("log.application.SyslogAppender.port"),
    APPLICATION_LOG_SYSLOGAPPENDER_FACILITY("log.application.SyslogAppender.facility"),
    AZURESTORAGECACHE_ACCOUNT_KEY("AzureStorageCache.account_key"),
    AZURESTORAGECACHE_ACCOUNT_NAME("AzureStorageCache.account_name"),
    AZURESTORAGECACHE_CONTAINER_NAME("AzureStorageCache.container_name"),
    AZURESTORAGECACHE_OBJECT_KEY_PREFIX("AzureStorageCache.object_key_prefix"),
    AZURESTORAGERESOLVER_ACCOUNT_KEY("AzureStorageResolver.account_key"),
    AZURESTORAGERESOLVER_ACCOUNT_NAME("AzureStorageResolver.account_name"),
    AZURESTORAGERESOLVER_CONTAINER_NAME("AzureStorageResolver.container_name"),
    AZURESTORAGERESOLVER_LOOKUP_STRATEGY("AzureStorageResolver.lookup_strategy"),
    BASE_URI("base_uri"),
    BASIC_AUTH_ENABLED("endpoint.public.auth.basic.enabled"),
    BASIC_AUTH_SECRET("endpoint.public.auth.basic.secret"),
    BASIC_AUTH_USERNAME("endpoint.public.auth.basic.username"),
    CACHE_SERVER_PURGE_MISSING("cache.server.purge_missing"),
    CACHE_SERVER_RESOLVE_FIRST("cache.server.resolve_first"),
    CACHE_WORKER_ENABLED("cache.server.worker.enabled"),
    CACHE_WORKER_INTERVAL("cache.server.worker.interval"),
    CLIENT_CACHE_ENABLED("cache.client.enabled"),
    CLIENT_CACHE_MAX_AGE("cache.client.max_age"),
    CLIENT_CACHE_MUST_REVALIDATE("cache.client.must_revalidate"),
    CLIENT_CACHE_NO_CACHE("cache.client.no_cache"),
    CLIENT_CACHE_NO_STORE("cache.client.no_store"),
    CLIENT_CACHE_NO_TRANSFORM("cache.client.no_transform"),
    CLIENT_CACHE_PRIVATE("cache.client.private"),
    CLIENT_CACHE_PROXY_REVALIDATE("cache.client.proxy_revalidate"),
    CLIENT_CACHE_PUBLIC("cache.client.public"),
    CLIENT_CACHE_SHARED_MAX_AGE("cache.client.shared_max_age"),
    DELEGATE_METHOD_INVOCATION_CACHE_ENABLED("delegate_script.cache.enabled"),
    DELEGATE_SCRIPT_ENABLED("delegate_script.enabled"),
    DELEGATE_SCRIPT_PATHNAME("delegate_script.pathname"),
    DERIVATIVE_CACHE("cache.server.derivative"),
    DERIVATIVE_CACHE_ENABLED("cache.server.derivative.enabled"),
    DERIVATIVE_CACHE_TTL("cache.server.derivative.ttl_seconds"),
    ERROR_LOG_FILEAPPENDER_ENABLED("log.error.FileAppender.enabled"),
    ERROR_LOG_FILEAPPENDER_PATHNAME("log.error.FileAppender.pathname"),
    ERROR_LOG_ROLLINGFILEAPPENDER_ENABLED("log.error.RollingFileAppender.enabled"),
    ERROR_LOG_ROLLINGFILEAPPENDER_PATHNAME("log.error.RollingFileAppender.pathname"),
    ERROR_LOG_ROLLINGFILEAPPENDER_POLICY("log.error.RollingFileAppender.policy"),
    ERROR_LOG_ROLLINGFILEAPPENDER_FILENAME_PATTERN("log.error.RollingFileAppender.TimeBasedRollingPolicy.filename_pattern"),
    ERROR_LOG_ROLLINGFILEAPPENDER_MAX_HISTORY("log.error.RollingFileAppender.TimeBasedRollingPolicy.max_history"),
    FFMPEGPROCESSOR_PATH_TO_BINARIES("FfmpegProcessor.path_to_binaries"),
    FILESYSTEMCACHE_DIRECTORY_DEPTH("FilesystemCache.dir.depth"),
    FILESYSTEMCACHE_DIRECTORY_NAME_LENGTH("FilesystemCache.dir.name_length"),
    FILESYSTEMCACHE_PATHNAME("FilesystemCache.pathname"),
    FILESYSTEMRESOLVER_LOOKUP_STRATEGY("FilesystemResolver.lookup_strategy"),
    FILESYSTEMRESOLVER_PATH_PREFIX("FilesystemResolver.BasicLookupStrategy.path_prefix"),
    FILESYSTEMRESOLVER_PATH_SUFFIX("FilesystemResolver.BasicLookupStrategy.path_suffix"),
    GRAPHICSMAGICKPROCESSOR_PATH_TO_BINARIES("GraphicsMagickProcessor.path_to_binaries"),
    HEAPCACHE_PATHNAME("HeapCache.persist.filesystem.pathname"),
    HEAPCACHE_PERSIST("HeapCache.persist"),
    HEAPCACHE_TARGET_SIZE("HeapCache.target_size"),
    HTTP_ACCEPT_QUEUE_LIMIT("http.accept_queue_limit"),
    HTTP_ENABLED("http.enabled"),
    HTTP_HTTP2_ENABLED("http.http2.enabled"),
    HTTP_HOST("http.host"),
    HTTP_MAX_THREADS("http.max_threads"),
    HTTP_MIN_THREADS("http.min_threads"),
    HTTP_PORT("http.port"),
    HTTPRESOLVER_BASIC_AUTH_SECRET("HttpResolver.auth.basic.secret"),
    HTTPRESOLVER_BASIC_AUTH_USERNAME("HttpResolver.auth.basic.username"),
    HTTPRESOLVER_LOOKUP_STRATEGY("HttpResolver.lookup_strategy"),
    HTTPRESOLVER_REQUEST_TIMEOUT("HttpResolver.request_timeout"),
    HTTPRESOLVER_TRUST_ALL_CERTS("HttpResolver.trust_all_certs"),
    HTTPRESOLVER_URL_PREFIX("HttpResolver.BasicLookupStrategy.url_prefix"),
    HTTPRESOLVER_URL_SUFFIX("HttpResolver.BasicLookupStrategy.url_suffix"),
    HTTPS_ENABLED("https.enabled"),
    HTTPS_HOST("https.host"),
    HTTPS_HTTP2_ENABLED("https.http2.enabled"),
    HTTPS_KEY_PASSWORD("https.key_password"),
    HTTPS_KEY_STORE_PASSWORD("https.key_store_password"),
    HTTPS_KEY_STORE_PATH("https.key_store_path"),
    HTTPS_KEY_STORE_TYPE("https.key_store_type"),
    HTTPS_PORT("https.port"),
    IIIF_1_ENDPOINT_ENABLED("endpoint.iiif.1.enabled"),
    IIIF_2_ENDPOINT_ENABLED("endpoint.iiif.2.enabled"),
    IIIF_2_RESTRICT_TO_SIZES("endpoint.iiif.2.restrict_to_sizes"),
    IIIF_CONTENT_DISPOSITION("endpoint.iiif.content_disposition"),
    IIIF_MIN_SIZE("endpoint.iiif.min_size"),
    IIIF_MIN_TILE_SIZE("endpoint.iiif.min_tile_size"),
    IMAGEMAGICKPROCESSOR_PATH_TO_BINARIES("ImageMagickProcessor.path_to_binaries"),
    INFO_CACHE_ENABLED("cache.server.info.enabled"),
    JDBCCACHE_CONNECTION_TIMEOUT("JdbcCache.connection_timeout"),
    JDBCCACHE_DERIVATIVE_IMAGE_TABLE("JdbcCache.derivative_image_table"),
    JDBCCACHE_INFO_TABLE("JdbcCache.info_table"),
    JDBCCACHE_JDBC_URL("JdbcCache.url"),
    JDBCCACHE_PASSWORD("JdbcCache.password"),
    JDBCCACHE_USER("JdbcCache.user"),
    JDBCRESOLVER_CONNECTION_TIMEOUT("JdbcResolver.connection_timeout"),
    JDBCRESOLVER_JDBC_URL("JdbcResolver.url"),
    JDBCRESOLVER_PASSWORD("JdbcResolver.password"),
    JDBCRESOLVER_USER("JdbcResolver.user"),
    KAKADUPROCESSOR_PATH_TO_BINARIES("KakaduProcessor.path_to_binaries"),
    MAX_PIXELS("max_pixels"),
    OPENJPEGPROCESSOR_PATH_TO_BINARIES("OpenJpegProcessor.path_to_binaries"),
    OVERLAY_ENABLED("overlays.enabled"),
    OVERLAY_IMAGE("overlays.BasicStrategy.image"),
    OVERLAY_INSET("overlays.BasicStrategy.inset"),
    OVERLAY_OUTPUT_HEIGHT_THRESHOLD("overlays.BasicStrategy.output_height_threshold"),
    OVERLAY_OUTPUT_WIDTH_THRESHOLD("overlays.BasicStrategy.output_width_threshold"),
    OVERLAY_POSITION("overlays.BasicStrategy.position"),
    OVERLAY_STRATEGY("overlays.strategy"),
    OVERLAY_STRING_BACKGROUND_COLOR("overlays.BasicStrategy.string.background.color"),
    OVERLAY_STRING_COLOR("overlays.BasicStrategy.string.color"),
    OVERLAY_STRING_FONT("overlays.BasicStrategy.string.font"),
    OVERLAY_STRING_FONT_MIN_SIZE("overlays.BasicStrategy.string.font.min_size"),
    OVERLAY_STRING_FONT_SIZE("overlays.BasicStrategy.string.font.size"),
    OVERLAY_STRING_FONT_WEIGHT("overlays.BasicStrategy.string.font.weight"),
    OVERLAY_STRING_GLYPH_SPACING("overlays.BasicStrategy.string.glyph_spacing"),
    OVERLAY_STRING_STRING("overlays.BasicStrategy.string"),
    OVERLAY_STRING_STROKE_COLOR("overlays.BasicStrategy.string.stroke.color"),
    OVERLAY_STRING_STROKE_WIDTH("overlays.BasicStrategy.string.stroke.width"),
    OVERLAY_TYPE("overlays.BasicStrategy.type"),
    PRINT_STACK_TRACE_ON_ERROR_PAGES("print_stack_trace_on_error_pages"),
    PROCESSOR_BACKGROUND_COLOR("processor.background_color"),
    PROCESSOR_DOWNSCALE_FILTER("processor.downscale_filter"),
    PROCESSOR_DPI("processor.dpi"),
    PROCESSOR_FALLBACK("processor.fallback"),
    PROCESSOR_FALLBACK_RETRIEVAL_STRATEGY("processor.fallback_retrieval_strategy"),
    PROCESSOR_JPG_PROGRESSIVE("processor.jpg.progressive"),
    PROCESSOR_JPG_QUALITY("processor.jpg.quality"),
    PROCESSOR_LIMIT_TO_8_BITS("processor.limit_to_8_bits"),
    PROCESSOR_NORMALIZE("processor.normalize"),
    PROCESSOR_PRESERVE_METADATA("processor.metadata.preserve"),
    PROCESSOR_RESPECT_ORIENTATION("processor.metadata.respect_orientation"),
    PROCESSOR_SHARPEN("processor.sharpen"),
    PROCESSOR_TIF_COMPRESSION("processor.tif.compression"),
    PROCESSOR_UPSCALE_FILTER("processor.upscale_filter"),
    REDACTION_ENABLED("redaction.enabled"),
    REDISCACHE_DATABASE("RedisCache.database"),
    REDISCACHE_HOST("RedisCache.host"),
    REDISCACHE_PASSWORD("RedisCache.password"),
    REDISCACHE_PORT("RedisCache.port"),
    REDISCACHE_SSL("RedisCache.ssl"),
    RESOLVER_DELEGATE("resolver.delegate"),
    RESOLVER_STATIC("resolver.static"),
    S3CACHE_ACCESS_KEY_ID("S3Cache.access_key_id"),
    S3CACHE_BUCKET_NAME("S3Cache.bucket.name"),
    S3CACHE_ENDPOINT("S3Cache.endpoint"),
    S3CACHE_MAX_CONNECTIONS("S3Cache.max_connections"),
    S3CACHE_OBJECT_KEY_PREFIX("S3Cache.object_key_prefix"),
    S3CACHE_SECRET_KEY("S3Cache.secret_key"),
    S3RESOLVER_ACCESS_KEY_ID("S3Resolver.access_key_id"),
    S3RESOLVER_BUCKET_NAME("S3Resolver.bucket.name"),
    S3RESOLVER_ENDPOINT("S3Resolver.endpoint"),
    S3RESOLVER_LOOKUP_STRATEGY("S3Resolver.lookup_strategy"),
    S3RESOLVER_MAX_CONNECTIONS("S3Resolver.max_connections"),
    S3RESOLVER_PATH_PREFIX("S3Resolver.BasicLookupStrategy.path_prefix"),
    S3RESOLVER_PATH_SUFFIX("S3Resolver.BasicLookupStrategy.path_suffix"),
    S3RESOLVER_SECRET_KEY("S3Resolver.secret_key"),
    SLASH_SUBSTITUTE("slash_substitute"),
    SOURCE_CACHE("cache.server.source"),
    SOURCE_CACHE_ENABLED("cache.server.source.enabled"),
    SOURCE_CACHE_TTL("cache.server.source.ttl_seconds"),
    STREAMPROCESSOR_RETRIEVAL_STRATEGY("StreamProcessor.retrieval_strategy"),
    TEMP_PATHNAME("temp_pathname");

    private String key;

    Key(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public String toString() {
        return key();
    }

}
