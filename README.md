# 📓 Journal Entry Application

A secure backend application that allows users to register using email-based OTP verification and manage personal journal entries using JWT authentication and authorization.

---

## 🚀 Tech Stack & Tools Used

- **Java & Spring Boot**
- **Spring Security + JWT** (Authentication & Authorization)
- **MongoDB Atlas** (Cloud Database)
- **Java Mail Sender** (Email OTP service)
- **Lombok** (Boilerplate code reduction)
- **jBCrypt** (Password & OTP hashing)

---

## 🔐 Security, Authentication & Authorization Design

This application follows a **layered security approach** combining:

- JWT-based authentication
- Role-based authorization
- Ownership-based access control
- Secure OTP verification with rate limiting

---

## 🧭 Authentication Flow

### 1️⃣ Public Routes (No Authentication)
- Signup / OTP request / OTP verification endpoints are excluded from Spring Security
- Allows new users to onboard without authentication barriers

---

### 2️⃣ User Signup with Email OTP
- User provides **email address**
- Server sends a **One-Time Password (OTP)** to the email
- OTP security rules:
    - ⏳ Valid for **10 minutes**
    - 🔐 Stored **only in hashed form**
    - 🔁 **Single-use only**
    - ❌ Expired or reused OTP is rejected

---

### 3️⃣ OTP Request Rate Limiting
To prevent abuse and spamming:

- ⏱️ **Minimum gap of 2 minutes** enforced between OTP requests
- Server checks the **latest unused OTP timestamp**
- ❌ New OTP request is rejected if issued too quickly

This ensures:
- Email abuse prevention
- Brute-force resistance
- Better system reliability

---

### 4️⃣ OTP Verification
During OTP verification, the server validates:
1. OTP existence
2. OTP expiry time
3. OTP purpose (signup / reset, etc.)
4. OTP correctness (hashed comparison)
5. OTP usage status

✅ On successful verification:
- OTP is marked as **used**
- A **Signup Token** (valid for 10 minutes) is issued

---

### 5️⃣ Profile Completion (Minimal Details)
Using the signup token, the user provides:
- Full Name
- Mobile Number
- Password (hashed using jBCrypt)
- Email (already captured)

User account is created after successful submission.

---

### 6️⃣ Final Authentication (JWT)
- After successful signup:
    - Server issues a **JWT access token** (60 minutes validity)
    - JWT is required for all protected APIs
    - Stateless authentication is enforced

---

## 🔑 Authorization Model

The application uses **two-layer authorization**:

### 🔹 Role-Based Authorization
- Implemented using Spring Security `@PreAuthorize`
- JWT contains assigned roles
- Example:
    - `ROLE_USER`

Controllers validate roles before allowing access.

---

### 🔹 Ownership-Based Authorization
- Implemented in the **service layer**
- Ensures users can only:
    - View
    - Update
    - Delete  
      **their own journal entries**

Even users with valid roles cannot access other users’ data.

---

## 📝 Journal Entry Features

Authenticated users can:
- ➕ Create journal entries
- 👀 View all their entries
- ✏️ Update entry content
- 🗑️ Delete entries

All operations are protected by:
- JWT authentication
- Role validation
- Ownership checks

---

## 🔒 Data Security Measures

- 🔐 Passwords hashed using jBCrypt
- 🔐 OTPs stored in hashed form
- ❌ Plain text sensitive data never stored
- 🔐 JWT-based stateless authentication
- ⏳ Token & OTP expiration enforced
- 🚫 OTP reuse prevention
- ⏱️ OTP request rate limiting

---

## 🛠️ Database Design

**MongoDB Atlas** stores:

- Users
- Journal Entries
- OTP records (with TTL-based expiry)
- Role identifiers

Relationships are managed safely to avoid orphan or inconsistent data.

---

## ⚠️ Current Status

✅ Authentication complete  
✅ Authorization complete  
✅ OTP security hardened

---

## 📌 Planned Enhancements

- Refresh token implementation
- Admin role (`ROLE_ADMIN`)
- Advanced rate limiting (hourly caps)
- Centralized exception handling
- API documentation (Swagger)

---

## 📄 Note

Sensitive files like `.env` and `application.properties` are excluded from version control.

---

## 👨‍💻 Author

Developed with ❤️ using Spring Boot, Spring Security & MongoDB
