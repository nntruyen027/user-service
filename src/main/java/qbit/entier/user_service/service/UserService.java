package qbit.entier.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qbit.entier.user_service.client.AuthServiceClient;
import qbit.entier.user_service.dto.AccountDto;
import qbit.entier.user_service.dto.UserAccountDto;
import qbit.entier.user_service.entity.User;
import qbit.entier.user_service.repository.UserRepository;
import qbit.entier.user_service.util.FileUtil;
import qbit.entier.user_service.util.JwtUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private FileUtil fileUtil;

    public Page<UserAccountDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> {
            ResponseEntity<AccountDto> responseEntity = authServiceClient.getUserById("Bearer " + jwtUtil.getJwtFromContext(), user.getAccountId());
            AccountDto account = responseEntity.getBody();
            return UserAccountDto.fromEntity(user, account);
        });
    }

    public Optional<UserAccountDto> getUserById(Long id) {
        return userRepository.findById(id).map(user -> {
            ResponseEntity<AccountDto> responseEntity = authServiceClient.getUserById("Bearer " + jwtUtil.getJwtFromContext(), user.getAccountId());
            AccountDto account = responseEntity.getBody();
            return UserAccountDto.fromEntity(user, account);
        });
    }

    public Optional<UserAccountDto> getSelf() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDto auth = (AccountDto) authentication.getPrincipal();
        return userRepository.findUserByAccountId(auth.getId()).map((user -> UserAccountDto.fromEntity(user, auth)));
    }

    public Optional<User> getSelfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDto auth = (AccountDto) authentication.getPrincipal();
        return userRepository.findUserByAccountId(auth.getId());
    }

    public UserAccountDto createOne(User user) {
        ResponseEntity<AccountDto> responseEntity = authServiceClient.getUserByJwt("Bearer " + jwtUtil.getJwtFromContext());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new EntityNotFoundException("Failed to retrieve account information from auth service");
        }

        AccountDto account = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new EntityNotFoundException("Account information is null"));

        if (userRepository.existsByAccountId(account.getId())) {
            throw new IllegalStateException("User with the account ID already exists");
        }

        user.setAccountId(account.getId());
        user.setEmail(account.getEmail());
        User savedUser = userRepository.save(user);

        return UserAccountDto.fromEntity(savedUser, account);
    }


    public UserAccountDto saveAvatar(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDto auth = (AccountDto) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findUserByAccountId(auth.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getAvatar() != null) {
                fileUtil.deleteFile(user.getAvatar());
            }
            String avatarPath = fileUtil.saveFile(file);

            user.setAvatar(avatarPath);

            userRepository.save(user);

            return UserAccountDto.fromEntity(user, auth);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public UserAccountDto updateUser(Long userId, User updatedUser) {
        ResponseEntity<AccountDto> responseEntity = authServiceClient.getUserByJwt("Bearer " + jwtUtil.getJwtFromContext());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new EntityNotFoundException("Failed to retrieve account information from auth service");
        }

        AccountDto account = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new EntityNotFoundException("Account information is null"));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        if (!Objects.equals(existingUser.getAccountId(), account.getId()))
            throw new EntityNotFoundException("User with ID " + userId + " not found");

        if (updatedUser.getFullName() != null && !updatedUser.getFullName().isEmpty())
            existingUser.setFullName(updatedUser.getFullName());
        if (updatedUser.getPhone() != null && !updatedUser.getPhone().isEmpty())
            existingUser.setPhone(updatedUser.getPhone());
        if (updatedUser.getIsMale() != null)
            existingUser.setIsMale(updatedUser.getIsMale());

        User savedUser = userRepository.save(existingUser);

        return UserAccountDto.fromEntity(savedUser, account);
    }

    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        ResponseEntity<?> responseEntity = authServiceClient.deleteUserById("Bearer " + jwtUtil.getJwtFromContext(), existingUser.getAccountId());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new EntityNotFoundException("Failed to retrieve account information from auth service");
        }

        userRepository.delete(existingUser);
    }


}
