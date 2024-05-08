INSERT INTO app_user (id, username, password, active, created_at)
VALUES ('7f000001-8a56-11d1-818a-56e25ae30000', 'admin', '{noop}secret', true, NOW());
-- '7f000001-8a56-1695-818a-56687e770000', 'user', '{noop}secret ||
INSERT INTO app_user (id, username, password, active, created_at)
   VALUES ('7f000001-8a56-1695-818a-56687e770000', 'user', '{noop}secret', true, NOW());

INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');

INSERT INTO user_role (user_id, role_id) VALUES ('7f000001-8a56-11d1-818a-56e25ae30000', 1);
INSERT INTO user_role (user_id, role_id) VALUES ('7f000001-8a56-1695-818a-56687e770000', 2);

INSERT INTO authority (id, name) VALUES (1, 'ARTICLE_READ');
INSERT INTO authority (id, name) VALUES (2, 'ARTICLE_WRITE');

-- ADMIN can read and write
INSERT INTO role_authority (role_id, authority_id) VALUES (1, 1);
INSERT INTO role_authority (role_id, authority_id) VALUES (1, 2);

-- USER only can read
INSERT INTO role_authority (role_id, authority_id) VALUES (2, 1);
SELECT * FROM client;

INSERT INTO client(id, authorization_grant_types, client_authentication_methods, client_id, client_id_issued_at, client_name,
                   client_secret, client_secret_expires_at, client_settings, redirect_uris, scopes, token_settings)
VALUES('abbc70f1-fb59-4b42-b1e4-c52fa0080bea', 'refresh_token,authorization_code,grant_password', 'client_secret_basic',
       'demo-client', now(), 'abbc70f1-fb59-4b42-b1e4-c52fa0080bea', '{noop}demo-secret', dateadd('DAY', 10, CURRENT_TIMESTAMP),
       null,
       'http://localhost:8080/auth', null,
       '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,
       "settings.token.access-token-time-to-live":["java.time.Duration",86400.000000000],
       "settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
       "value":"reference"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],
       "settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

-- INSERT INTO client(id, authorization_grant_types, client_authentication_methods, client_id, client_id_issued_at, client_name,
--                    client_secret, client_secret_expires_at, client_settings, redirect_uris, scopes, token_settings)
-- VALUES('abbc70f1-fb59-4b42-b1e4-c52fa0080bea', 'refresh_token,authorization_code,grant_password', 'client_secret_basic',
--        'demo-client', now(), 'abbc70f1-fb59-4b42-b1e4-c52fa0080bea', '{noop}demo-secret', dateadd('DAY', 10, CURRENT_TIMESTAMP),
--        '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":true}',
--        'http://localhost:8080/auth', null,
--        '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,
--        "settings.token.access-token-time-to-live":["java.time.Duration",86400.000000000],
--        "settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
--        "value":"reference"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],
--        "settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');


-- INSERT INTO client(id, authorization_grant_types, client_authentication_methods, client_id, client_id_issued_at, client_name,
--                    client_secret, client_secret_expires_at, client_settings, redirect_uris, scopes, token_settings)
-- VALUES('abbc70f1-fb59-4b42-b1e4-c52fa0080bea', 'refresh_token,client_credentials,authorization_code', 'client_secret_basic',
--        'client', now(), 'abbc70f1-fb59-4b42-b1e4-c52fa0080bea', '$2a$10$lcGI9Fp6GLfk7wjyOK0VqORQqMtsQRoC3J7i/V023SgQv9JZLZ01K', date_add(now(),interval 10 day),
--        '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":true}',
--        'http://insomnia,http://127.0.0.1:8080/login/oauth2/code/client', 'read,openid,profile,address,email,phone,client.read,client.create',
--        '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,
--        "settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],
--        "settings.token.access-token-time-to-live":["java.time.Duration",86400.000000000],
--        "settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
--        "value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],
--        "settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');