package br.com.chronos.domain.user;

public record UserCreatedDTO(
        Long id,
        String email,
        Role role
) {
    public UserCreatedDTO(User user) {
        this(user.getId(), user.getEmail(), user.getRole());
    }
}
