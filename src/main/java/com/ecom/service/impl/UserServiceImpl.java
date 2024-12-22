package com.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ecom.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDtls saveUser(UserDtls user) {
		user.setRole("ROLE_USER");
		user.setIsEnable(true);
		user.setAccountNonLocked(true);
		user.setFailedAttempt(0);

		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
        return userRepository.save(user);
	}

	@Override
	public UserDtls getUserById(Integer user) {
        return userRepository.findById(user).orElse(null);
	}

	@Override
	public UserDtls getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDtls> getUsers(String role) {
		return userRepository.findByRole(role);
	}

	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {

		Optional<UserDtls> findByuser = userRepository.findById(id);

		if (findByuser.isPresent()) {
			UserDtls userDtls = findByuser.get();
			userDtls.setIsEnable(status);
			userRepository.save(userDtls);
			return true;
		}

		return false;
	}

	@Override
	public void increaseFailedAttempt(UserDtls user) {
		int attempt = user.getFailedAttempt() + 1;
		user.setFailedAttempt(attempt);
		userRepository.save(user);
	}

	@Override
	public void userAccountLock(UserDtls user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
	}

	@Override
	public boolean unlockAccountTimeExpired(UserDtls user) {

		long lockTime = user.getLockTime().getTime();
		long unLockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;

		long currentTime = System.currentTimeMillis();

		if (unLockTime < currentTime) {
			user.setAccountNonLocked(true);
			user.setFailedAttempt(0);
			user.setLockTime(null);
			userRepository.save(user);
			return true;
		}

		return false;
	}

	@Override
	public void resetAttempt(int userId) {

	}

	@Override
	public void updateUserResetToken(String email, String resetToken) {
		UserDtls findByEmail = userRepository.findByEmail(email);
		findByEmail.setResetToken(resetToken);
		userRepository.save(findByEmail);
	}

	@Override
	public UserDtls getUserByToken(String token) {
		return userRepository.findByResetToken(token);
	}

	@Override
	public UserDtls updateUser(UserDtls user) {
		return userRepository.save(user);
	}

	@Override
	public UserDtls updateUserProfile(UserDtls user, MultipartFile img) {
		UserDtls dbUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

		// Verificar si la imagen es vacía o no
		if (img != null && !img.isEmpty()) {
			String imageName = img.getOriginalFilename();

			if (imageName != null && !imageName.isEmpty()) {
				dbUser.setProfileImage(imageName);

				String uploadDir = "uploads/user_img/";
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				// Obtener la ruta del archivo y copiarlo
				Path path = Paths.get(uploadDir + imageName);
				try {
					Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new RuntimeException("Error uploading image: " + e.getMessage(), e);
				}
			}
		} else {
			// Si la imagen está vacía, mantén la imagen actual del usuario
			user.setProfileImage(dbUser.getProfileImage());
		}

		// Actualizar los demás detalles del usuario
		if (dbUser != null) {
			dbUser.setName(user.getName());
			dbUser.setMobileNumber(user.getMobileNumber());
			dbUser.setAddress(user.getAddress());
			dbUser.setCity(user.getCity());
			dbUser.setState(user.getState());
			dbUser.setPincode(user.getPincode());
			// Guardar los cambios en la base de datos
			dbUser = userRepository.save(dbUser);
		}

		return dbUser;
	}

	@Override
	public UserDtls updateAdminProfile(UserDtls user, MultipartFile img) {
		UserDtls dbAdmin = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

		// Verificar si la imagen es vacía o no
		if (img != null && !img.isEmpty()) {
			String imageName = img.getOriginalFilename();

			if (imageName != null && !imageName.isEmpty()) {
				dbAdmin.setProfileImage(imageName);

				String uploadDir = "uploads/admin_img/";
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				// Obtener la ruta del archivo y copiarlo
				Path path = Paths.get(uploadDir + imageName);
				try {
					Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new RuntimeException("Error uploading image: " + e.getMessage(), e);
				}
			}
		} else {
			// Si la imagen está vacía, mantén la imagen actual del usuario
			user.setProfileImage(dbAdmin.getProfileImage());
		}

		// Actualizar los demás detalles del usuario
		if (dbAdmin != null) {
			dbAdmin.setName(user.getName());
			dbAdmin.setMobileNumber(user.getMobileNumber());
			dbAdmin.setAddress(user.getAddress());
			dbAdmin.setCity(user.getCity());
			dbAdmin.setState(user.getState());
			dbAdmin.setPincode(user.getPincode());

			// Guardar los cambios en la base de datos
			dbAdmin = userRepository.save(dbAdmin);
		}

		return dbAdmin;
	}


	@Override
	public UserDtls saveAdmin(UserDtls user) {
		user.setRole("ROLE_ADMIN");
		user.setIsEnable(true);
		user.setAccountNonLocked(true);
		user.setFailedAttempt(0);

		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		UserDtls saveUser = userRepository.save(user);
		return saveUser;
	}

	@Override
	public Boolean existsEmail(String email) {
		return userRepository.existsByEmail(email);
	}

}