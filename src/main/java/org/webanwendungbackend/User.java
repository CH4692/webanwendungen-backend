package org.webanwendungbackend;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;

    private List<Order> orders;

    Role role;
}
