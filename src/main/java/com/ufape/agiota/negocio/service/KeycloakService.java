package com.ufape.agiota.negocio.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm = "agiota"; // Altere conforme seu ambiente

    public KeycloakService() {
        // Inicialize o cliente Keycloak
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080/") // URL do servidor Keycloak
                .realm("master") // Realm do admin
                .clientId("admin-cli")
                .username("admin") // Credenciais do administrador
                .password("admin") // Substitua pela senha do admin
                .build();
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
        return keycloak.realm(realm).users().search(username).get(0).getId();
    }
}