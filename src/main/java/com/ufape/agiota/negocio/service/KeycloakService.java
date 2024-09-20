package com.ufape.agiota.negocio.service;

import com.ufape.agiota.comunication.dto.Auth.TokenResponse;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm = "agiota";
    private final String keycloakServerUrl = "http://localhost:8080/";

    public KeycloakService() {
        // Inicialize o cliente Keycloak
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl) // URL do servidor Keycloak
                .realm("master") // Realm do admin
                .clientId("admin-cli")
                .username("admin") // Credenciais do administrador
                .password("admin") // Substitua pela senha do admin
                .build();
    }

    public TokenResponse login(String email, String password) throws Exception {
        String tokenUrl = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        // Fazer a requisição HTTP para o token endpoint do Keycloak
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Parâmetros da requisição
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", "agiota-application");
        formData.add("client_secret", "bBYtcrDGFIzS5J8gHNJKORwPcSccsHsF");
        formData.add("username", email);
        formData.add("password", password);

        // Fazer a requisição
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<TokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, TokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            TokenResponse tokenResponse = response.getBody();
            String userId = getUserId(email);
            List<RoleRepresentation> roles = keycloak.realm(realm).users().get(userId).roles().realmLevel().listEffective();
            assert tokenResponse != null;
            tokenResponse.setRoles(roles.stream().map(RoleRepresentation::getName).toList());
            return response.getBody();
        } else {
            throw new Exception("Erro ao autenticar no Keycloak: " + response.getStatusCode());
        }
    }

    public Response createUser(String username, String email, String password, String role) {
        // Configurar as credenciais do usuário
        UserRepresentation user = getUserRepresentation(username, email, password);

        // Criar o usuário no Keycloak
        Response response = keycloak.realm(realm).users().create(user);

        if (response.getStatus() == 201) {
            String userId = keycloak.realm(realm).users().search(username).get(0).getId();
            RoleRepresentation userRole = keycloak.realm(realm).roles().get(role).toRepresentation();
            keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(userRole));
        }

        return response;
    }

    private static UserRepresentation getUserRepresentation(String username, String email, String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        // Configurar o novo usuário
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setFirstName(email);
        user.setLastName(email);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(credential));
        return user;
    }

    public void updateUser(String userId, String email) {
        UserRepresentation user = keycloak.realm(realm).users().get(userId).toRepresentation();
        user.setFirstName(email);
        user.setLastName(email);
        user.setEmail(email);
        user.setEmailVerified(true);
        keycloak.realm(realm).users().get(userId).update(user);
//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setTemporary(false);
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue(password);
//        keycloak.realm(realm).users().get(userId).resetPassword(credential);
    }

    public void deleteUser(String userId) {
        keycloak.realm(realm).users().get(userId).remove();
    }



    public String getUserId(String username) {
        List<UserRepresentation> user = keycloak.realm(realm).users().search(username, true);
        if (user.isEmpty()) {
            user = keycloak.realm(realm)
                    .users()
                    .search(null, null, null, username, null, null);  // Busca pelo email

        }if (!user.isEmpty()) {
            return user.get(0).getId();
        }
        throw new IndexOutOfBoundsException("User not found");
    }
}