# 📓 Journal Entry Application

A secure backend application that allows users to register using email-based OTP verification and manage personal journal entries using JWT authentication.

---

## 🚀 Tech Stack & Tools Used

- **Java & Spring Boot**
- **Spring Security + JWT** (Authentication)
- **MongoDB Atlas** (Cloud Database)
- **Java Mail Sender** (Email OTP service)
- **Lombok** (Boilerplate code reduction)
- **jBCrypt** (Password & OTP hashing)

---

## 🔐 Security & Authentication Design

This application follows a **multi-step secure authentication flow** designed to balance **user experience and security**.

---

## 🧭 Authentication Flow

1. **Public Routes (No Security)**
   - Signup / New User endpoints are bypassed from Spring Security
   - Allows new users to register without authentication barriers

2. **User Signup with Email OTP**
   - User enters **email address**
   - Server sends an **OTP to the provided email**
   - OTP properties:
     - ⏳ Valid for 10 minutes
     - 🔐 Stored in hashed format in the database
     - 🔁 One-time use only
     - ❌ Reuse or expired OTP results in an error

3. **OTP Verification**
   - User submits OTP for verification
   - Server validates:
     - OTP correctness
     - OTP expiration time
     - OTP usage status
   - ✅ On success:
     - Server issues a **Signup Token** (valid 10 minutes)
     - Signup token is used to complete minimal user profile

4. **Profile Completion (Minimal Details)**
   - Using the signup token, user provides:
     - Full Name
     - Mobile Number
     - Password (hashed using jBCrypt)
     - Email (already captured)
   - These details are stored as **initial user data**

5. **Final Authentication & JWT Token**
   - After profile completion:
     - User is authenticated
     - Server generates a **final JWT token** (valid 15 minutes)
     - JWT token is required for accessing secured APIs

---

## 📝 Journal Entry Features

Once authenticated using the JWT token, the user can:

- ➕ Create new journal entries
- 👀 View existing journal entries
- ✏️ Edit journal entries
- 🔐 Access protected routes securely

---

## 🔑 Data Security Measures

- 🔒 Passwords are hashed using jBCrypt
- 🔒 OTP values are hashed before storing
- ❌ Plain text credentials are never stored
- 🔐 JWT-based stateless authentication
- ⏳ Token expiration enforced for better security

---

## 🛠️ Database

- **MongoDB Atlas** is used as the primary database
- Stores:
  - User data
  - OTP details with expiry timestamps
  - Journal entries

---

## ⚠️ Work in Progress

- 🔐 Role-based authorization
- More security enhancements and features coming soon

---

## 📌 Future Improvements

- Role-based authorization
- Refresh tokens
- Rate limiting for OTP requests
- Better error handling & logging

---

## 📄 Note

Sensitive configuration files like `.env` and `application.properties` are ignored and not committed to the repository.

---

## 👨‍💻 Author

Developed with ❤️ using Spring Boot & MongoDB
